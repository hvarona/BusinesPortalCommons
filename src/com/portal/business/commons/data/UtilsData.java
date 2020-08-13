package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.IpAddress;
import com.portal.business.commons.models.IpBlackList;
import com.portal.business.commons.models.Language;
import com.portal.business.commons.models.Period;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.Mail;
import com.portal.business.commons.utils.SendMail;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author usuario
 */
public class UtilsData extends AbstractBusinessPortalWs {

    private static final String dCase = "abcdefghijklmnopqrstuvwxyz";
    private static final String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String intChar = "0123456789";
    private static Random r = new Random();
    private static String pass = "";
    private static final Logger logger = Logger.getLogger(UtilsData.class);

    public List<Language> getLanguages() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<Language> languages = (List<Language>) listEntities(Language.class, request, logger, getMethodName());
        return languages;
    }

    public List<Language> getLanguages(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Language>) listEntities(Language.class, request, logger, getMethodName());
    }

    public Language loadLanguage(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Language language = (Language) loadEntity(Language.class, request, logger, getMethodName());
        return language;
    }

    public Language getLanguage(String isoName) throws RegisterNotFoundException, NullParameterException, GeneralException {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Language> cq = cb.createQuery(Language.class);
            Root<Language> from = cq.from(Language.class);
            cq.select(from);
            cq.where(
                    cb.equal(from.get("iso"), isoName));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            return (Language) query.getSingleResult();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }

    public boolean isIpAddresInBlackList(String ipAddress) throws NullParameterException, GeneralException {
        if (ipAddress == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ipAddress"), null);
        }
        List<IpBlackList> list = null;
        try {
            Query query = createQuery("SELECT b FROM IpBlackList b WHERE b.ipAddress=?1");
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
        if (ipAddress == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "loadIpddress"), null);
        }
        List<IpAddress> list = null;

        try {
            Query query = createQuery("SELECT i FROM IpAddress i WHERE i.ipAddress=?1");
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
        if (ipBlackList == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "loadIpddress"), null);
        }
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
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = createQuery("DELETE FROM IpBlackList i WHERE i.ipAddress=?1");
            query.setParameter("1", ipBlackList);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public String generatedKey(int length) {
        while (pass.length() != 16) {
            int rPick = r.nextInt(4);
            if (rPick == 0) {
                int spot = r.nextInt(25);
                pass += dCase.charAt(spot);
            } else if (rPick == 1) {
                int spot = r.nextInt(25);
                pass += uCase.charAt(spot);
            } else if (rPick == 3) {
                int spot = r.nextInt(9);
                pass += intChar.charAt(spot);
            }
        }
        System.out.println("Generated Pass: " + pass);
        return pass;
    }

    public List<Period> getPeriods(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Period>) listEntities(Period.class, request, logger, getMethodName());
    }

    public Period loadPeriod(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Period period = (Period) loadEntity(Period.class, request, logger, getMethodName());
        return period;
    }

    public Store saveStore(Store store) throws NullParameterException, GeneralException {
        if (store == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "store"), null);
        }
        return (Store) saveEntity(store);
    }
    
    public void sendMail(Mail mail) throws GeneralException, NullParameterException {
        SendMail SendMail = new SendMail();
        try {
            SendMail.sendMail(mail);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }
}
