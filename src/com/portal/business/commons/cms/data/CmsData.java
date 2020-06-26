package com.portal.business.commons.cms.data;

import com.portal.business.commons.cms.CmsCity;
import com.portal.business.commons.cms.CmsCountry;
import com.portal.business.commons.cms.CmsDocumentPersonType;
import com.portal.business.commons.cms.CmsPersonType;
import com.portal.business.commons.cms.CmsState;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.EntityManagerWrapper;
import com.portal.business.commons.generic.RemittenceGenericEntity;
import com.portal.business.commons.models.CardPreRequest;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.MessageFormatHelper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author henry
 */
public class CmsData {

    private static final Logger LOG = Logger.getLogger(CmsData.class);

    protected MessageFormatHelper sysError;

    protected EntityManagerFactory emf;

    private final EntityManager entityManager;

    protected EntityManagerWrapper entityManagerWrapper;

    public CmsData() {
        emf = Persistence.createEntityManagerFactory("cmsPU");
        entityManager = emf.createEntityManager();
        sysError = new MessageFormatHelper(EjbConstants.ERROR_FILE_NAME);
        entityManagerWrapper = new EntityManagerWrapper(entityManager);
    }

    protected String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    protected Object saveEntity(Object entity) throws GeneralException, NullParameterException {
        if (entity == null) {
            throw new NullParameterException("NullParameterException ");
        }
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (!transaction.isActive()) {
                transaction.begin();
            }
            if (((RemittenceGenericEntity) entity).getPk() != null) {
                //processAuditData(EventType.UPDATE, entity, auditData, entityManagerWrapper);
                entityManagerWrapper.update(entity);
            } else {
                entityManagerWrapper.save(entity);
                //processAuditData(EventType.CREATE, entity, auditData, entityManagerWrapper);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception e1) {
                throw new GeneralException("GeneralException saveEntity");
            }
            throw new GeneralException("GeneralException saveEntity");
        }

        return entity;
    }

    public List<CmsCountry> getCountries() throws GeneralException {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<CmsCountry> cq = cb.createQuery(CmsCountry.class);
            Root<CmsCountry> from = cq.from(CmsCountry.class);
            cq.select(from);
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public List<CmsState> getStates(CmsCountry country) throws NullParameterException, GeneralException {
        if (country == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "country"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<CmsState> cq = cb.createQuery(CmsState.class);
            Root<CmsState> from = cq.from(CmsState.class);
            cq.select(from).where(cb.equal(from.get("country"), country));
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public List<CmsCity> getCities(CmsState state) throws NullParameterException, GeneralException {
        if (state == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "state"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<CmsCity> cq = cb.createQuery(CmsCity.class);
            Root<CmsCity> from = cq.from(CmsCity.class);
            cq.select(from).where(cb.equal(from.get("state"), state));
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    private List<CmsPersonType> getPersonType(CmsCountry country, boolean natural) throws NullParameterException, GeneralException {
        if (country == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "country"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<CmsPersonType> cq = cb.createQuery(CmsPersonType.class);
            Root<CmsPersonType> from = cq.from(CmsPersonType.class);
            cq.select(from).where(cb.equal(from.get("country"), country), cb.equal(from.get("indNaturalPerson"), natural));
            return entityManager.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    public List<CmsDocumentPersonType> getDocumentTypes(CmsCountry country) throws NullParameterException, GeneralException {
        if (country == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "country"), null);
        }
        try {
            List<CmsPersonType> personTypes = getPersonType(country, true);
            List<CmsDocumentPersonType> documentTypes = new ArrayList();

            for (CmsPersonType personType : personTypes) {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<CmsDocumentPersonType> cq = cb.createQuery(CmsDocumentPersonType.class);
                Root<CmsDocumentPersonType> from = cq.from(CmsDocumentPersonType.class);
                cq.select(from).where(cb.equal(from.get("personType"), personType));
                documentTypes.addAll(entityManager.createQuery(cq).getResultList());
            }
            return documentTypes;
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

    
}
