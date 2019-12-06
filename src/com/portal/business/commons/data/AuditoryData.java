package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;

import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.models.AuditAction;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.GeneralUtils;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author usuario
 */
public class AuditoryData extends AbstractBusinessPortalWs {


  private static final Logger logger = Logger.getLogger(AuditoryData.class);

   public AuditAction deleteAuditAction(Long actionId) throws GeneralException, NullParameterException {
        return null;
    }

    public List<AuditAction> getAuditActions(Date beginningDate, Date endingDate) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<AuditAction> audits = new ArrayList<AuditAction>();
        if (beginningDate == null || beginningDate == null) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "beginningDate & endingDate"), null);
        }
        Query query = null;
        try {
            query = createQuery("SELECT a FROM AuditAction a WHERE a.date BETWEEN :date1 AND :date2");
            query.setParameter("date1", GeneralUtils.getBeginningDate(beginningDate));
            query.setParameter("date2", GeneralUtils.getEndingDate(endingDate));
            audits = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (audits.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return audits;
    }


     public List<AuditAction> searchAuditAction(String login, String userName, Long permissionId, Date beginningDate, Date endingDate) throws GeneralException, NullParameterException, EmptyListException {
        List<AuditAction> audits = new ArrayList<AuditAction>();

        if (beginningDate == null || beginningDate == null) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "beginningDate & endingDate"), null);
        }
        StringBuilder sqlBuilder = new StringBuilder("SELECT a FROM AuditAction a WHERE a.date BETWEEN :date1 AND :date2");

        if (login != null) {
            sqlBuilder.append(" AND a.user.login=").append(login);
        }
        if (userName != null && !userName.equals("")) {
            sqlBuilder.append(" AND a.user.firstName=").append(userName);
        }
        if (permissionId != null) {
            sqlBuilder.append(" AND a.permission.id=").append(permissionId);
        }
        Query query = null;
        try {
            query = createQuery(sqlBuilder.toString());
            query.setParameter("date1", GeneralUtils.getBeginningDate(beginningDate));
            query.setParameter("date2", GeneralUtils.getEndingDate(endingDate));
            audits = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (audits.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return audits;
    }

    public AuditAction saveAuditAction(AuditAction action) throws GeneralException, NullParameterException {
        return (AuditAction) saveEntity(action);
    }

    public List<AuditAction> getAuditActionsByUserId(Long userId, Date beginningDate, Date endingDate) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<AuditAction> audits = new ArrayList<AuditAction>();

        if (userId == null) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "userId"), null);
        } else if (beginningDate == null || beginningDate == null) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "beginningDate & endingDate"), null);
        }
        Query query = null;
        try {
            query = createQuery("SELECT a FROM AuditAction a WHERE a.date BETWEEN :date1 AND :date2 AND a.user.id=?1");
            query.setParameter("1", userId);
            query.setParameter("date1", GeneralUtils.getBeginningDate(beginningDate));
            query.setParameter("date2", GeneralUtils.getEndingDate(endingDate));
            audits = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (audits.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return audits;
    }
}