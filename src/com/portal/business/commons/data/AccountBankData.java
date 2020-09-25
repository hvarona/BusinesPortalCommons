package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.AccountBank;
import com.portal.business.commons.models.AccountTypeBank;
import com.portal.business.commons.models.BPBank;
import com.portal.business.commons.models.Business;
import com.portal.business.commons.models.StatusAccountBank;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author hvarona
 */
public class AccountBankData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(AccountBankData.class);

    public List<AccountBank> getAccountBankList(Business commerce) throws EmptyListException, GeneralException {
       List<AccountBank> accountBanks = null;
       try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AccountBank> cq = cb.createQuery(AccountBank.class);
            Root<AccountBank> from = cq.from(AccountBank.class);
            accountBanks = entityManager.createQuery(cq.select(from).where(cb.equal(from.get("commerce"), commerce))).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (accountBanks == null || accountBanks.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return accountBanks;
    }

   
    public AccountBank loadAccountBank(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        return (AccountBank) loadEntity(AccountBank.class, request, LOG, getMethodName());
    }

    public List<AccountBank> getAccountBankByCommerce(Business commerce) throws EmptyListException, GeneralException {
        List<AccountBank> accountBanks = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AccountBank> cq = cb.createQuery(AccountBank.class);
            Root<AccountBank> from = cq.from(AccountBank.class);
            cq.select(from);
            cq.where(cb.equal(from.get("commerce"), commerce));

            accountBanks = entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (accountBanks == null || accountBanks.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return accountBanks;
    }

   
    public AccountBank saveAccountBank(AccountBank accountBank) throws NullParameterException, GeneralException {
        if (accountBank == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "pos"), null);
        }
        return (AccountBank) saveEntity(accountBank);
    }
    
    public AccountBank geAccountBankByAccountNumber(String accountNumber) throws NullParameterException, GeneralException {
        if (accountNumber == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "code"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AccountBank> cq = cb.createQuery(AccountBank.class);
            Root<AccountBank> from = cq.from(AccountBank.class);
            cq.select(from).where(cb.equal(from.get("accountNumber"), accountNumber));
            return entityManager.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }
    
    public BPBank getBank(Long bankId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        if (bankId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPBank> cq = cb.createQuery(BPBank.class);
            Root<BPBank> from = cq.from(BPBank.class);
            cq.select(from);
            cq.where(cb.equal(from.get("id"), bankId));

            Query query = entityManager.createQuery(cq);
            return (BPBank) query.getSingleResult();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }
    
    public AccountTypeBank getAccountTypeBank(Long accountTypeBankId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        if (accountTypeBankId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<AccountTypeBank> cq = cb.createQuery(AccountTypeBank.class);
            Root<AccountTypeBank> from = cq.from(AccountTypeBank.class);
            cq.select(from);
            cq.where(cb.equal(from.get("id"), accountTypeBankId));

            Query query = entityManager.createQuery(cq);
            return (AccountTypeBank) query.getSingleResult();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public StatusAccountBank getStatusAccountBank(Long statusAccountBankId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        if (statusAccountBankId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<StatusAccountBank> cq = cb.createQuery(StatusAccountBank.class);
            Root<StatusAccountBank> from = cq.from(StatusAccountBank.class);
            cq.select(from);
            cq.where(cb.equal(from.get("id"), statusAccountBankId));

            Query query = entityManager.createQuery(cq);
            return (StatusAccountBank) query.getSingleResult();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public List<BPBank> getBanks(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<BPBank>) listEntities(BPBank.class, request, LOG, getMethodName());
    }
    
    public List<AccountTypeBank> getAccountTypeBanks(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<AccountTypeBank>) listEntities(AccountTypeBank.class, request, LOG, getMethodName());
    }
     
    public List<StatusAccountBank> getStatusAccountBanks(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<StatusAccountBank>) listEntities(StatusAccountBank.class, request, LOG, getMethodName());
    }
}
