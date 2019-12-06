package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Account;
import com.portal.business.commons.models.Address;
import com.portal.business.commons.models.Permission;
import com.portal.business.commons.models.PermissionGroup;
import com.portal.business.commons.models.Profile;
import com.portal.business.commons.models.User;
import com.portal.business.commons.models.UserHasProfile;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;


/**
 *
 * @author usuario
 */
public class UserData  extends AbstractBusinessPortalWs {
     
    private static final Logger logger = Logger.getLogger(UserData.class);
    

    public List<User> getUsers(WsRequest request) throws EmptyListException, GeneralException {

        List<User> users = null;
        try {
            users = (List<User>) createQuery("SELECT u FROM User u").setHint("toplink.refresh", "true").getResultList();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        if (users.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return users;
    }

    public User loadUser(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = (User) loadEntity(User.class, request, logger, getMethodName());

        return user;
    }

    public User loadUserByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

        List<User> users = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_EMAIL)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_EMAIL), null);
        }

        try {
            users = (List<User>) getNamedQueryResult(User.class, QueryConstants.LOAD_USER_BY_EMAIL, request, getMethodName(), logger, "User");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "user"), null);
        }

        return users.get(0);
    }

    public User loadUserByLogin(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<User> users = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_LOGIN)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_LOGIN), null);
        }
        try {
            users = (List<User>) getNamedQueryResult(User.class, QueryConstants.LOAD_USER_BY_LOGIN, request, getMethodName(), logger, "User");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "user"), null);
        }
        return users.get(0);
    }

    public Account loadAccountByLogin(String login) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Account account = null;
        if (login == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            Query query = createQuery("SELECT a FROM Account a WHERE a.login =?1");
            query.setParameter("1", login);
            account = (Account) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), ex);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return account;

    }

    public User saveUser(WsRequest request) throws NullParameterException, GeneralException {
        return (User) saveEntity(request, logger, getMethodName());
    }

    public boolean validateExistingUser(WsRequest request) throws NullParameterException, GeneralException {
        boolean valid = true;
        Map<String, Object> params = request.getParams();
        if (params.containsKey(QueryConstants.PARAM_LOGIN)) {
            try {
                loadUserByLogin(request);
            } catch (RegisterNotFoundException ex) {
                valid = false;
            } catch (NullParameterException ex) {
                throw new NullParameterException(ex.getMessage());
            } catch (GeneralException ex) {
                throw new GeneralException(ex.getMessage());
            }
        } else if (params.containsKey(QueryConstants.PARAM_EMAIL)) {
            try {
                loadUserByEmail(request);
            } catch (RegisterNotFoundException ex) {
                valid = false;
            } catch (NullParameterException ex) {
                throw new NullParameterException(ex.getMessage());
            } catch (GeneralException ex) {
                throw new GeneralException(ex.getMessage());
            }
        } else {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_LOGIN), null);
        }
        return valid;
    }

 


    public List<PermissionGroup> getPermissionGroups() throws EmptyListException, NullParameterException, GeneralException {
        List<PermissionGroup> permissionGroups = new ArrayList<PermissionGroup>();
        Query query = null;
        try {
            query = createQuery("SELECT pg FROM PermissionGroup pg");
            permissionGroups = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissionGroups.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissionGroups;

    }

    public List<Permission> getPermissions() throws EmptyListException, NullParameterException, GeneralException {
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT p FROM Permission p WHERE p.enabled =1");
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    public List<Permission> getPermissionByGroupId(Long groupId) throws EmptyListException, NullParameterException, GeneralException {
        if (groupId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "groupId"), null);
        }
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT p FROM Permission p WHERE p.enabled =1 AND p.permissionGroup.id=?1");
            query.setParameter("1", groupId);
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    public List<Permission> getPermissionByProfileId(Long profileId) throws EmptyListException, NullParameterException, GeneralException {
        if (profileId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "groupId"), null);
        }
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT php.permission FROM PermissionHasProfile php WHERE php.profile.id = ?1 and php.permission.enabled=1");
            query.setParameter("1", profileId);
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    public Permission loadPermissionById(Long permissionId) throws GeneralException, NullParameterException, RegisterNotFoundException {
        WsRequest bRequest = new WsRequest(permissionId);
        Permission permission = (Permission) loadEntity(Permission.class, bRequest, logger, getMethodName());
        return permission;

    }

    public List<Profile> getProfiles() throws EmptyListException, GeneralException {

        List<Profile> profiles = new ArrayList<Profile>();
        Query query = null;
        try {
            query = createQuery("SELECT p FROM Profile p WHERE p.enabled = 1");
            profiles = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (profiles.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return profiles;
    }
   
    public Account saveAccount(WsRequest request) throws GeneralException, NullParameterException {
        return (Account) saveEntity(request, logger, getMethodName());
    }

//    public Remettence saveRemettence(WsRequest request) throws GeneralException, NullParameterException {
//        return (Remettence) saveEntity(request, logger, getMethodName());
//    }

    public List<Account> getAccounts(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Account> accounts = (List<Account>) listEntities(Account.class, request, logger, getMethodName());

        return accounts;
    }

    public List<Account> searchAccounts(Long enterpriseId, String login, String fullName, String email,String status) throws EmptyListException, NullParameterException, GeneralException {
        List<Account> accounts = new ArrayList<Account>();
        if (enterpriseId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "enterpriseId"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT a FROM Account a WHERE a.enterprise.id = ?1");
        try {
            if (login != null) {
                sqlBuilder.append(" AND a.login like '%").append(login).append("%'");
            }
            if (fullName != null) {
                sqlBuilder.append(" AND a.fullName like '%").append(fullName).append("%'");
            }
            if (email != null) {
                sqlBuilder.append(" AND a.email like '%").append(email).append("%'");
            }
            if (status != null && !status.equals(QueryConstants.STATUS_ACCOUNT_ALL)) {
                sqlBuilder.append(" AND a.enabled='").append(status).append("'");
            }
            Query query = entityManager.createQuery(sqlBuilder.toString());
            query.setParameter("1", enterpriseId);
            accounts = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException ex) {
//            throw new EmptyListException("No distributions found");
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return accounts;
    }

    public Account loadAccount(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Account account = (Account) loadEntity(Account.class, request, logger, getMethodName());
        return account;
    }

    public User loadUserByLogin(String login) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = null;
        if (login == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {

            Query query = createQuery("SELECT u FROM User u WHERE u.login =?1 AND u.enabled=TRUE");
            query.setParameter("1", login);
            user = (User) query.getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), ex);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return user;

    }

   


    public Account searchAccountsByLogin(String login) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Account accounts = new Account();


        try {

            Query query = createQuery("SELECT a FROM Account a  WHERE a.login =?1");
            query.setParameter("1", login);
            accounts = (Account) query.getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), ex);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return accounts;
    }

    

    public Account loadAccountbyFullName(String fullName) {
        Account account = null;
        account = entityManager.find(Account.class, fullName);
        if (account != null) {
            return account;
        } else {
            account = null;
        }
        return account;
    }

    public Account loadAccountById(String id) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Account account = null;
        Long accountId = Long.valueOf(id);
        if (id == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            Query query = createQuery("SELECT a FROM Account a WHERE a.id =?1");
            query.setParameter("1", accountId);
            account = (Account) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), ex);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return account;

    }

    public Address saveAddress(Address address) throws NullParameterException, GeneralException {
        if (address == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "address"), null);
        }
        return (Address) saveEntity(address);
    }
    
    public Address loadAddress(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Address address = (Address) loadEntity(Address.class, request, logger, getMethodName());
        return address;
    }
    
     public void deleteUserHasProfile(Long userId) throws NullParameterException, GeneralException {
        if (userId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "userId"), null);
        }
        
        Map<String, Long> params = new HashMap<String, Long>();
        params.put(QueryConstants.PARAM_USER_ID, userId);
        
        try {
            executeNameQuery(UserHasProfile.class, QueryConstants.DELETE_USER_HAS_PROFILE, params, getMethodName(), logger, "User", null, null);
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }
     
     public User loadUserByLogin(String login, String password) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = null;
        if (login == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            Query query = createQuery("SELECT u FROM User u WHERE u.login=?1 AND u.password=?2");
            query.setParameter("1", login);
            query.setParameter("2", password);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException("");
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException("");
        }
        return user;
    }
     
     public List<Permission> getPermissionByGroupIdAndProfile(Long groupId,Long profileId) throws EmptyListException, NullParameterException, GeneralException {
        if (groupId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "groupId"), null);
        }
        List<Permission> permissions = new ArrayList<Permission>();
        Query query = null;
        try {
            query = createQuery("SELECT php.permission FROM PermissionHasProfile php WHERE php.permission.permissionGroup.id=?1 AND php.profile.id = ?2 and php.permission.enabled=1");
            query.setParameter("1", groupId);
            query.setParameter("2", profileId);
            permissions = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions.isEmpty()) {
            throw new EmptyListException("No");
        }
        return permissions;
    }
     
    public User loadUserByEmail(String email) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = null;
        if (email == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            Query query = createQuery("SELECT u FROM User u WHERE u.email =?1 AND u.enabled=TRUE");
            query.setParameter("1", email);
            user = (User) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException("No se encontro el usuario");
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException("No se encontro el usuario");
        }
        return user;
    }
    
    public Profile loadProfile(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Profile profile = (Profile) loadEntity(Profile.class, request, logger, getMethodName());

        return profile;
    }
}
