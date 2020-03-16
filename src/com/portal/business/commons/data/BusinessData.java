package com.portal.business.commons.data;

import com.portal.business.commons.enumeration.OperationType;
import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Business;
import com.portal.business.commons.models.BusinessBalanceIncoming;
import com.portal.business.commons.models.BusinessBalanceOutgoing;
import com.portal.business.commons.models.BusinessClose;
import com.portal.business.commons.models.BusinessTransaction;
import com.portal.business.commons.models.Operator;
import com.portal.business.commons.utils.EjbConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author hvarona
 */
public class BusinessData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(BusinessData.class);

    public List<Business> getOperatorList() throws EmptyListException, GeneralException {

        List<Business> businessList = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Business> cq = cb.createQuery(Business.class);
            Root<Business> from = cq.from(Business.class);
            businessList = entityManager.createQuery(cq.select(from)).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (businessList == null || businessList.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return businessList;
    }

    public Business loadBusiness(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        return (Business) loadEntity(Operator.class, request, LOG, getMethodName());
    }

    public Business saveBusiness(Business business) throws NullParameterException, GeneralException {
        if (business == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "business"), null);
        }
        return (Business) saveEntity(business);
    }

    public List<BusinessClose> getBusinessClose(Business business) throws EmptyListException, GeneralException {
        List<BusinessClose> businessCloseList = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BusinessClose> cq = cb.createQuery(BusinessClose.class);
            Root<BusinessClose> from = cq.from(BusinessClose.class);
            cq.select(from);
            cq.where(cb.equal(from.get("business"), business));
            businessCloseList = entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (businessCloseList == null || businessCloseList.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return businessCloseList;
    }

    public synchronized BusinessBalanceIncoming saveIncomingBalance(BusinessBalanceIncoming incoming) throws NullParameterException, GeneralException {
        if (incoming == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "incoming"), null);
        }
        return (BusinessBalanceIncoming) saveEntity(incoming);
    }

    public synchronized BusinessBalanceOutgoing saveOutgoinBalance(BusinessBalanceOutgoing outgoing) throws NullParameterException, GeneralException {
        if (outgoing == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "outgoing"), null);
        }
        return (BusinessBalanceOutgoing) saveEntity(outgoing);
    }

    public synchronized BusinessClose closeBusiness(Business business) throws GeneralException, NullParameterException {

        List<BusinessBalanceIncoming> incomings = getPendingIncomingBalance(business);
        List<BusinessBalanceOutgoing> outcomings = getPendingOutcomingBalance(business);
        Date dateClose = new Date();

        double totalAmount = 0;
        for (BusinessBalanceIncoming incoming : incomings) {
            totalAmount += incoming.getAmountWithoutFee();
        }

        for (BusinessBalanceOutgoing outcoming : outcomings) {
            totalAmount -= outcoming.getAmountWithoutFee();
        }

        BusinessClose businessClose = new BusinessClose(business, dateClose, totalAmount, BusinessClose.CloseStatus.PENDING);
        businessClose = (BusinessClose) saveEntity(businessClose);

        for (BusinessBalanceIncoming incoming : incomings) {
            incoming.setClose(businessClose);
            saveEntity(incoming);
        }

        for (BusinessBalanceOutgoing outcoming : outcomings) {
            outcoming.setClose(businessClose);
            saveEntity(outcoming);
        }

        return businessClose;
    }

    private List<BusinessBalanceIncoming> getPendingIncomingBalance(Business business) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BusinessBalanceIncoming> cq = cb.createQuery(BusinessBalanceIncoming.class);
            Root<BusinessBalanceIncoming> from = cq.from(BusinessBalanceIncoming.class);
            cq.select(from);
            cq.where(cb.equal(from.get("business"), business),
                    cb.isNull(from.get("close")));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
        }
        return new ArrayList();
    }

    private List<BusinessBalanceOutgoing> getPendingOutcomingBalance(Business business) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BusinessBalanceOutgoing> cq = cb.createQuery(BusinessBalanceOutgoing.class);
            Root<BusinessBalanceOutgoing> from = cq.from(BusinessBalanceOutgoing.class);
            cq.select(from);
            cq.where(cb.equal(from.get("business"), business),
                    cb.isNull(from.get("close")));

            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
        }
        return new ArrayList();
    }

    private List<BusinessBalanceIncoming> getBusinessIncomingTransactions(Business business, Date startDate, Date endDate, OperationType type) throws GeneralException {
        List<BusinessBalanceIncoming> incomings = new ArrayList();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BusinessBalanceIncoming> cq = cb.createQuery(BusinessBalanceIncoming.class);
            Root<BusinessBalanceIncoming> from = cq.from(BusinessBalanceIncoming.class);
            cq.select(from);

            List<Predicate> predList = new ArrayList();
            Path<Date> dateEntryPath = from.get("dateTransaction");

            predList.add(cb.equal(from.get("business"), business));
            predList.add(cb.between(dateEntryPath, startDate, endDate));
            if (type != null) {
                predList.add(cb.equal(from.get("type"), type));
            }
            Predicate[] predArray = new Predicate[predList.size()];
            predList.toArray(predArray);

            cq.where(predArray);
            Query query = entityManager.createQuery(cq);
            
            incomings = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return incomings;
    }

    private List<BusinessBalanceOutgoing> getBusinessOutgoingTransactions(Business business, Date startDate, Date endDate, OperationType type) throws GeneralException {
        List<BusinessBalanceOutgoing> outgoings = new ArrayList();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BusinessBalanceOutgoing> cq = cb.createQuery(BusinessBalanceOutgoing.class);
            Root<BusinessBalanceOutgoing> from = cq.from(BusinessBalanceOutgoing.class);
            cq.select(from);
            List<Predicate> predList = new ArrayList();
            Path<Date> dateEntryPath = from.get("dateTransaction");

            predList.add(cb.equal(from.get("business"), business));
            predList.add(cb.between(dateEntryPath, startDate, endDate));
            if (type != null) {
                predList.add(cb.equal(from.get("type"), type));
            }
            Predicate[] predArray = new Predicate[predList.size()];
            predList.toArray(predArray);

            cq.where(predArray);
            outgoings = entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return outgoings;
    }

    public List<BusinessTransaction> getBusinessTransactions(Business business, Date startDate, Date endDate) throws EmptyListException, GeneralException {
        return getBusinessTransactions(business, startDate, endDate, null);
    }

    public List<BusinessTransaction> getBusinessTransactions(Business business, Date startDate, Date endDate, OperationType type) throws EmptyListException, GeneralException {
        List<BusinessTransaction> businessTransactions = new ArrayList();
        try {
            System.out.println("Generando reporte desde " + startDate + " hasta " + endDate + " de operacion " + type);
            List<BusinessBalanceIncoming> incomings = getBusinessIncomingTransactions(business, startDate, endDate, type);
            for (BusinessBalanceIncoming incoming : incomings) {
                businessTransactions.add(new BusinessTransaction(incoming));
            }
        } catch (GeneralException e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }

        try {
            List<BusinessBalanceOutgoing> outgoings = getBusinessOutgoingTransactions(business, startDate, endDate, type);
            for (BusinessBalanceOutgoing outgoing : outgoings) {
                businessTransactions.add(new BusinessTransaction(outgoing));
            }
        } catch (GeneralException e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (businessTransactions.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }

        return businessTransactions;
    }

}
