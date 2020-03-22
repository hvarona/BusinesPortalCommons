package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.DisabledUserException;
import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;

import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Permission;
import com.portal.business.commons.models.PermissionHasProfile;
import com.portal.business.commons.models.Profile;
import com.portal.business.commons.models.ProfileData;
import com.portal.business.commons.models.User;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.Encoder;
import com.portal.business.commons.utils.Mail;
import com.portal.business.commons.utils.QueryConstants;
import com.portal.business.commons.utils.RemettenceMails;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author usuario
 */
public class AccessControlData extends AbstractBusinessPortalWs {

    private static final Logger logger = Logger.getLogger(AccessControlData.class);

    public void deletePermissionHasProfile(Long profileId) throws NullParameterException, GeneralException {
        if (profileId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "profileId"), null);
        }
        Map<String, Long> params = new HashMap<String, Long>();
        params.put(QueryConstants.PARAM_PROFILE_ID, profileId);
        try {
            executeNameQuery(PermissionHasProfile.class, QueryConstants.DELETE_PERMISSION_HAS_PROFILE, params, getMethodName(), logger, "Profile", null, null);
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public List<Profile> getParentsByProfile(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Profile> profiles = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(QueryConstants.PARAM_PROFILE_ID)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_PROFILE_ID), null);
        }
        Query query = null;
        try {
            query = createQuery("SELECT php.parent FROM ProfileHasProfile php WHERE php.child.id = ?1 AND php.endingDate IS NULL");
            query.setParameter("1", params.get(QueryConstants.PARAM_PROFILE_ID));
            if (request.getLimit() != null && request.getLimit() > 0) {
                query.setMaxResults(request.getLimit());
            }
            profiles = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (profiles.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return profiles;
    }

    public List<Permission> getPermissions(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Permission> permissions = (List<Permission>) listEntities(Permission.class, request, logger, getMethodName());
        return permissions;
    }

    public List<Profile> getProfiles(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Profile> profiles = (List<Profile>) listEntities(Profile.class, request, logger, getMethodName());
        return profiles;
    }

    public Permission loadPermission(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Permission permission = (Permission) loadEntity(Permission.class, request, logger, getMethodName());

        return permission;
    }

    public Profile loadProfile(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Profile profile = (Profile) loadEntity(Profile.class, request, logger, getMethodName());

        return profile;
    }

    public void logginFailed(WsRequest request) throws NullParameterException, GeneralException, RegisterNotFoundException {
        Object o = request.getParam();
        if (o == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_CUSTOMER_ID + " - " + QueryConstants.PARAM_USER_ID), null);
        }

    }

    public void logginSuccessful(WsRequest request) throws NullParameterException, GeneralException, RegisterNotFoundException {
    }

    public Permission savePermission(WsRequest request) throws NullParameterException, GeneralException {
        return (Permission) saveEntity(request, logger, getMethodName());
    }

    public Profile saveProfile(WsRequest request) throws NullParameterException, GeneralException {
        return (Profile) saveEntity(request, logger, getMethodName());
    }

    public ProfileData saveProfileData(WsRequest request) throws NullParameterException, GeneralException {
        return (ProfileData) saveEntity(request, logger, getMethodName());

    }

    public boolean validateLoginPreferences(WsRequest request) throws NullParameterException, GeneralException, RegisterNotFoundException {
        Object o = request.getParam();
        if (o == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_CUSTOMER_ID + " - " + QueryConstants.PARAM_USER_ID), null);
        }
        return false;
    }

    public User validateUser(String login, String password) throws RegisterNotFoundException, NullParameterException, GeneralException, DisabledUserException {
        User user = null;

        if (login == null || login.equals("")) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_LOGIN), null);
        }
        if (password == null || password.equals("")) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_PASSWORD), null);
        }

        try {
            UserData userData = new UserData();
            user = userData.loadUserByLogin(login);
            if (user != null && !user.getPassword().equals(password)) {
                user = null;
                throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "user"), null);
            }

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "user"), ex);
        } catch (RegisterNotFoundException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "user"), ex);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "user"), ex);
        }
        if (!user.getEnabled()) {
            throw new DisabledUserException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "distributor enabled"), null);
        }

        return user;
    }

    public static void generateNewPassword(User user) throws GeneralException {
        try {
            UserData userData = new UserData();
            SecureRandom random = new SecureRandom();
            String str = new BigInteger(130, random).toString(32);
            if (user != null) {
                String shortStr = str.toUpperCase().substring(0, 8);
                String oldPassword = user.getPassword();
                WsRequest request = new WsRequest();
                request.setParam(user);
                user.setPassword(Encoder.MD5(shortStr));
                userData.saveUser(request);
                try {
                    sendUserRecoveryPasswordMail(user, shortStr);
                } catch (Exception ex) {
                    /*Si ocurre un error al enviar el correo se guarda el
                     usuario con el password que tenia previamente.*/
                    user.setPassword(oldPassword);
                    request.setParam(user);
                    userData.saveUser(request);
                    throw new GeneralException(ex.getMessage());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(ex.getMessage());
        }
    }

    private static void sendUserRecoveryPasswordMail(User user, String newPassword) throws GeneralException {
        try {
            Mail mail = RemettenceMails.getRecoveryPasswordMail(user, newPassword);
            //Inicia el envio del correo electronico
            (new com.portal.business.commons.utils.MailSender(mail)).start();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(ex.getMessage());
        }
    }
}
