package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.DisabledAccountException;
import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.InvalidAccountException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;

import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Account;
import com.portal.business.commons.models.AccountHasIpAddress;
import com.portal.business.commons.models.IpAddress;
import com.portal.business.commons.models.IpBlackList;
import com.portal.business.commons.utils.AccountReference;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author usuario
 */
public class AccountData extends AbstractBusinessPortalWs {


  private static final Logger logger = Logger.getLogger(AccountData.class);

  public Account validateAccount(AccountReference accountReference) throws NullParameterException, InvalidAccountException, DisabledAccountException, GeneralException {
        if (accountReference == null) {
            throw new NullParameterException("");
        } else if (accountReference.getLogin() == null) {
            throw new NullParameterException("");
        } else if (accountReference.getPassword() == null) {
            throw new NullParameterException("");
        }
        Account account = null;
        try {
            Query query = null;
            query = createQuery("SELECT a FROM Account a WHERE a.login=?1 AND a.password=?2");
            query.setParameter("1", accountReference.getLogin());
            query.setParameter("2", accountReference.getPassword());
            account = (Account) query.setHint("toplink.refresh", "true").getSingleResult();
        } catch (NoResultException ex) {
            ex.printStackTrace();
            throw new InvalidAccountException("");
        } catch (Exception ex) {
            throw new GeneralException("");
        }
        if (!account.getEnabled()) {
            throw new DisabledAccountException("");
        }
        return account;
    }




    public Long getCountIpAddress(String ip,Long accountId) throws GeneralException {
        Long number = 0l;
        String sql = "SELECT count(ipa.id) FROM account_has_ip_address ahip, ip_address ipa, account a  WHERE a.id=ahip.accountId AND ahip.ipAddressId=ipa.id  AND ipa.ip='" + ip + "' AND a.id="+accountId;
        try {
            List result = new ArrayList();
            result =  (List) entityManager.createNativeQuery(sql).getSingleResult();
            number= result != null && result.size() > 0 ? ((Long) result.get(0)) : 0L;
//            number = (Long) result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(ex.getMessage());
        }
        return number;
    }

     public Account accountEnabled(String login) throws NullParameterException, GeneralException,InvalidAccountException {
         if (login == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ipAddress"), null);
        }
        Account account = null;
        try {
            Query query =  createQuery("SELECT a FROM Account a WHERE a.login=?1");
            query.setParameter("1", login);
            account =  (Account) query.setHint("toplink.refresh", "true").getSingleResult();
        } catch (NoResultException e) {
            throw new InvalidAccountException("Exception in method accountEnabled: " + e.getMessage(), e.getStackTrace());
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return account;
    }

    public boolean isIpAddresInBlackList(String ipAddress) throws NullParameterException, GeneralException {
         if (ipAddress == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ipAddress"), null);
        }
        List<IpBlackList> list = null;
        try {
            Query query =  createQuery("SELECT b FROM IpBlackList b WHERE b.ipAddress=?1");
            query.setParameter("1", ipAddress);
            list = (List<IpBlackList>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (list.isEmpty()) {
           return false;
        }
        return true;
    }

    public IpBlackList saveIpBlackList(IpBlackList ipBlackList) throws NullParameterException, GeneralException {
        if (ipBlackList == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "saveIpBlackList"), null);
        }
        return (IpBlackList) saveEntity(ipBlackList);
    }

    public IpAddress loadIpddress(String ipAddress) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (ipAddress == null)
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "loadIpddress"), null);
        List<IpAddress> list = null;

        try {
            Query query = createQuery("SELECT i FROM IpAddress i WHERE i.ip=?1");
            query.setParameter("1", ipAddress);
            list = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            logger.error("Exception in method loadIpddress: Exception text: ", e);
            throw new GeneralException("Exception in method loadIpddress: Exception text: " + e.getMessage(), e.getStackTrace());
        }
        if (list.isEmpty()) {
            throw new RegisterNotFoundException("Not IpAddress found in method loadIpddress");
        }
        return list.get(0);
    }

     public List<IpBlackList> getBlackList() throws GeneralException, EmptyListException {
        List<IpBlackList> ipBlackList = new ArrayList<IpBlackList>();
        try {
            Query query = createQuery("SELECT i FROM IpBlackList i");
            ipBlackList = (List<IpBlackList>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (ipBlackList.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return ipBlackList;
    }

     public IpBlackList loadBlackList(String ipBlackList) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (ipBlackList == null)
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "loadIpddress"), null);
        List<IpBlackList> list = null;

        try {
            Query query = createQuery("SELECT i FROM IpBlackList i WHERE i.ipAddress=?1");
            query.setParameter("1", ipBlackList);
            list = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            logger.error("Exception in method loadBlackList: Exception text: ", e);
            throw new GeneralException("Exception in method loadBlackList: Exception text: " + e.getMessage(), e.getStackTrace());
        }
        if (list.isEmpty()) {
            throw new RegisterNotFoundException("Not ipBlackList found in method loadBlackList");
        }
        return list.get(0);
    }


     public void deleteIpBlackList(String ipBlackList) throws NullParameterException, GeneralException {
        if (ipBlackList == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "enterpriseId"), null);
        }
        try {
            Query query = createQuery("DELETE FROM IpBlackList i WHERE i.ipAddress=?1");
            query.setParameter("1", ipBlackList);
            executeQuery(query);
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }
     
    public List<Account> getAccount(WsRequest request) throws GeneralException, NullParameterException, EmptyListException {
        return (List<Account>) listEntities(Account.class, request, logger, getMethodName());
  }

    public List<Account> getAccountByConditions(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        Boolean isFilter = (Boolean) request.getParam();
        if (isFilter == null || isFilter.equals("null")) {
            isFilter = false;
        }
        Map orderField = new HashMap();
        orderField.put("id", QueryConstants.ORDER_DESC);
        return (List<Account>) createSearchQuery(Account.class, request, orderField, logger, getMethodName(), "accounts", isFilter);
    }

    public Account loadAccount(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (Account) loadEntity(Account.class, request, logger, getMethodName());
    }


    public Account loadAccountByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

        List<Account> accounts = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_EMAIL)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_EMAIL), null);
        }
        try {
            accounts = (List<Account>) getNamedQueryResult(Account.class, QueryConstants.LOAD_ACCOUNT_BY_EMAIL, request, getMethodName(), logger, "Account");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Account"), null);
        }

        return accounts.get(0);
    }


    public Account loadAccountByLogin(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Account> accounts = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_LOGIN)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_LOGIN), null);
        }
        try {
            accounts = (List<Account>) getNamedQueryResult(Account.class, QueryConstants.LOAD_ACCOUNT_BY_LOGIN, request, getMethodName(), logger, "Account");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Account"), null);
        }
        return accounts.get(0);
    }
    
    
    public Account saveAccount(WsRequest request) throws GeneralException, NullParameterException {
        return (Account) saveEntity(request, logger, getMethodName());
    }

    public Account saveAccount(Account account) throws NullParameterException, GeneralException {
        if (account == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "Account"), null);
        }
        return (Account) saveEntity(account);
    }
    
    public AccountHasIpAddress updateAccountHasIpAddress(AccountHasIpAddress hasIpAddress) throws NullParameterException, GeneralException {
         if (hasIpAddress == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "hasIpAddress"), null);
        }
        return (AccountHasIpAddress) saveEntity(hasIpAddress);
    }
}