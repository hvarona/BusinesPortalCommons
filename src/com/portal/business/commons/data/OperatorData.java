package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Commerce;
import com.portal.business.commons.models.Operator;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author hvarona
 */
public class OperatorData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(OperatorData.class);

    public List<Operator> getOperatorList(Commerce commerce) throws EmptyListException, GeneralException {

        List<Operator> operatorList = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Operator> cq = cb.createQuery(Operator.class);
            Root<Operator> from = cq.from(Operator.class);
            operatorList = entityManager.createQuery(cq.select(from).where(cb.equal(from.get("commerce"), commerce))).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (operatorList == null || operatorList.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return operatorList;
    }

    public Operator loadOperator(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        return (Operator) loadEntity(Operator.class, request, LOG, getMethodName());
    }

    public Operator saveOperator(Operator operator) throws NullParameterException, GeneralException {
        if (operator == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "operator"), null);
        }
        return (Operator) saveEntity(operator);
    }

}
