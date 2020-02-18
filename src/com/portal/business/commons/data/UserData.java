package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Permission;
import com.portal.business.commons.models.PermissionGroup;
import com.portal.business.commons.models.PermissionHasProfile;
import com.portal.business.commons.models.Profile;
import com.portal.business.commons.models.User;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author usuario
 */
public class UserData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(UserData.class);

    public List<User> getUsers(WsRequest request) throws EmptyListException, GeneralException {

        List<User> users = null;
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> from = cq.from(User.class);
            cq.select(from);

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            users = query.getResultList();
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        if (users.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return users;
    }

    public User loadUser(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        User user = (User) loadEntity(User.class, request, LOG, getMethodName());

        return user;
    }

    public User saveUser(WsRequest request) throws NullParameterException, GeneralException {
        return (User) saveEntity(request, LOG, getMethodName());
    }

    public List<PermissionGroup> getPermissionGroups() throws EmptyListException, NullParameterException, GeneralException {
        List<PermissionGroup> permissionGroups = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PermissionGroup> cq = cb.createQuery(PermissionGroup.class);
            Root<PermissionGroup> from = cq.from(PermissionGroup.class);
            cq.select(from);

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            permissionGroups = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissionGroups == null || permissionGroups.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissionGroups;

    }

    public List<Permission> getPermissions() throws EmptyListException, NullParameterException, GeneralException {
        List<Permission> permissions = null;
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Permission> cq = cb.createQuery(Permission.class);
            Root<Permission> from = cq.from(Permission.class);
            cq.select(from).where(cb.equal(from.get("enabled"), true));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            permissions = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions == null || permissions.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    public List<Permission> getPermissionByGroupId(PermissionGroup permissionGroup) throws EmptyListException, NullParameterException, GeneralException {
        if (permissionGroup == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "permissionGroup"), null);
        }
        List<Permission> permissions = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Permission> cq = cb.createQuery(Permission.class);
            Root<Permission> from = cq.from(Permission.class);
            cq.select(from);
            cq.where(cb.and(cb.equal(from.get("enabled"), true),
                    cb.equal(from.get("permissiongroup"), permissionGroup)));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            permissions = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions == null || permissions.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    public List<Permission> getPermissionByProfileId(Profile profile) throws EmptyListException, NullParameterException, GeneralException {
        if (profile == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "groupId"), null);
        }
        List<Permission> permissions = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Permission> cq = cb.createQuery(Permission.class);
            Root<PermissionHasProfile> from = cq.from(PermissionHasProfile.class);
            Join<PermissionHasProfile, Permission> permission = from.join("permission");
            cq.select(permission);
            cq.where(cb.and(cb.equal(permission.get("enabled"), true),
                    cb.equal(from.get("profile"), profile)));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            permissions = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions == null || permissions.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return permissions;
    }

    public Permission loadPermissionById(Long permissionId) throws GeneralException, NullParameterException, RegisterNotFoundException {
        WsRequest bRequest = new WsRequest(permissionId);
        Permission permission = (Permission) loadEntity(Permission.class, bRequest, LOG, getMethodName());
        return permission;

    }

    public List<Profile> getProfiles() throws EmptyListException, GeneralException {

        List<Profile> profiles = null;
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Profile> cq = cb.createQuery(Profile.class);
            Root<Profile> from = cq.from(Profile.class);
            cq.select(from);
            cq.where(cb.equal(from.get("enabled"), true));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            profiles = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (profiles == null || profiles.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return profiles;
    }

    public List<Profile> getOperatorsProfiles() throws EmptyListException, GeneralException {

        List<Profile> profiles = null;
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Profile> cq = cb.createQuery(Profile.class);
            Root<Profile> from = cq.from(Profile.class);
            cq.select(from);
            cq.where(cb.and(cb.equal(from.get("enabled"), true),
                    cb.equal(from.get("isOperator"), true)));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            profiles = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (profiles == null || profiles.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return profiles;
    }

    public User loadUserByLogin(String login) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (login == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> from = cq.from(User.class);
            cq.select(from);
            cq.where(cb.equal(from.get("login"), login));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            return (User) query.getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), ex);
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }

    }

    public User loadUserByLogin(String login, String password) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (login == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> from = cq.from(User.class);

            cq.select(from).where(cb.and(cb.equal(from.get("login"), login), cb.equal(from.get("password"), password)));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            return (User) query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException("");
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException("");
        }
    }

    public List<Permission> getPermissionByGroupIdAndProfile(PermissionGroup permissionGroup, Profile profile) throws EmptyListException, NullParameterException, GeneralException {
        if (permissionGroup == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "permissionGroup"), null);
        }
        if (profile == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "profile"), null);
        }
        List<Permission> permissions = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Permission> cq = cb.createQuery(Permission.class);
            Root<PermissionHasProfile> from = cq.from(PermissionHasProfile.class);
            Join<PermissionHasProfile, Permission> permission = from.join("permission");

            cq.select(permission);
            cq.where(
                    cb.and(cb.and(
                            cb.equal(permission.get("permissionGroup"), permissionGroup),
                            cb.equal(permission.get("enabled"), true)),
                            cb.equal(from.get("profile"), profile)));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            permissions = query.getResultList();

        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (permissions == null || permissions.isEmpty()) {
            throw new EmptyListException("No");
        }
        return permissions;
    }

    public User loadUserByEmail(String email) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (email == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "login"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> from = cq.from(User.class);

            cq.select(from).where(cb.and(cb.equal(from.get("email"), email), cb.equal(from.get("enabled"), true)));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            return (User) query.getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException("No se encontro el usuario");
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException("No se encontro el usuario");
        }
    }

    public Profile loadProfile(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Profile profile = (Profile) loadEntity(Profile.class, request, LOG, getMethodName());

        return profile;
    }
}
