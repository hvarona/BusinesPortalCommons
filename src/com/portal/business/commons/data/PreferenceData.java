package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.models.Business;
import com.portal.business.commons.models.BPPreference;
import com.portal.business.commons.models.BPPreferenceField;
import com.portal.business.commons.models.BPPreferenceValue;
import com.portal.business.commons.utils.EjbConstants;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author usuario
 */
public class PreferenceData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(PreferenceData.class);

    private BPPreferenceValue getLastPreferenceValueByPreferenceField(BPPreferenceField field, Business business) throws GeneralException, NullParameterException, EmptyListException {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPPreferenceValue> cq = cb.createQuery(BPPreferenceValue.class);
            Root<BPPreferenceValue> from = cq.from(BPPreferenceValue.class);
            cq.select(from);
            cq.where(cb.and(
                    cb.equal(from.get("business"), business),
                    cb.and(
                            cb.equal(from.get("preferenceField"), field),
                            cb.isNull(from.get("endingDate"))))); //The preference needs to be active

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            query.setMaxResults(1);
            return (BPPreferenceValue) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public Map<Long, String> getLastPreferenceValues(Business business) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        Map<Long, String> currentValues = new HashMap();
        List<BPPreferenceField> fields = this.getPreferenceFields();
        for (BPPreferenceField field : fields) {
            BPPreferenceValue pv = getLastPreferenceValueByPreferenceField(field, business);
            if (pv != null) {
                currentValues.put(field.getId(), pv.getValue());
            }
        }
        return currentValues;
    }

    public List<BPPreferenceField> getPreferenceFields() throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<BPPreferenceField> preferenceFields = null;

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPPreferenceField> cq = cb.createQuery(BPPreferenceField.class);
            Root<BPPreferenceField> from = cq.from(BPPreferenceField.class);
            cq.select(from);

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            preferenceFields = query.getResultList();
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        if (preferenceFields == null || preferenceFields.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return preferenceFields;
    }

    public List<BPPreferenceValue> getPreferenceValuesByBusisnessAndField(BPPreferenceField field, Business business) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<BPPreferenceValue> preferenceValues = null;

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPPreferenceValue> cq = cb.createQuery(BPPreferenceValue.class);
            Root<BPPreferenceValue> from = cq.from(BPPreferenceValue.class);
            cq.select(from);
            cq.where(cb.and(
                    cb.equal(from.get("business"), business),
                    cb.equal(from.get("preferenceField"), field))); //The preference needs to be active

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            preferenceValues = query.getResultList();
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        if (preferenceValues == null || preferenceValues.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return preferenceValues;
    }

    public BPPreferenceValue loadActivePreferenceValuesByBusinessAndField(BPPreferenceField field, Business business) throws GeneralException, RegisterNotFoundException, NullParameterException {

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPPreferenceValue> cq = cb.createQuery(BPPreferenceValue.class);
            Root<BPPreferenceValue> from = cq.from(BPPreferenceValue.class);
            cq.select(from);
            cq.where(cb.and(
                    cb.equal(from.get("business"), business),
                    cb.and(
                            cb.equal(from.get("preferenceField"), field),
                            cb.isNull(from.get("endingDate"))))); //The preference needs to be active

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            query.setMaxResults(1);
            return (BPPreferenceValue) query.getSingleResult();
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }

    public List<BPPreferenceValue> savePreferenceValues(List<BPPreferenceValue> values) throws GeneralException, NullParameterException {
        Timestamp time = new Timestamp(new Date().getTime());
        List<BPPreferenceValue> returnValues = new ArrayList();
        for (BPPreferenceValue pv : values) {
            BPPreferenceValue oldPv = null;
            pv.setBeginningDate(time);
            try {
                oldPv = getLastPreferenceValueByPreferenceField(pv.getPreferenceField(), pv.getBusiness());
                if (oldPv != null) {
                    if (!pv.getValue().equals(oldPv.getValue())) {
                        oldPv.setEndingDate(time);
                        saveEntity(oldPv, LOG, getMethodName());
                        returnValues.add((BPPreferenceValue) saveEntity(pv, LOG, getMethodName()));
                    }
                }
            } catch (EmptyListException e) {
                e.printStackTrace();
            }
            if (oldPv == null) {
                returnValues.add((BPPreferenceValue) saveEntity(pv, LOG, getMethodName())); //SALVO EL NUEVO VALOR
            }
        }

        return returnValues;
    }

    public List<BPPreference> getPreferences() throws GeneralException, RegisterNotFoundException, EmptyListException {
        List<BPPreference> preferences = null;

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPPreference> cq = cb.createQuery(BPPreference.class);
            Root<BPPreference> from = cq.from(BPPreference.class);
            cq.select(from);

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            preferences = query.getResultList();
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        if (preferences == null || preferences.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return preferences;
    }

    public BPPreference getPreference(Long preferenceId) throws NullParameterException, GeneralException, RegisterNotFoundException {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<BPPreference> cq = cb.createQuery(BPPreference.class);
            Root<BPPreference> from = cq.from(BPPreference.class);
            cq.select(from);
            cq.where(cb.equal(from.get("id"), preferenceId));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            BPPreference preference = (BPPreference) query.getSingleResult();
            return preference;
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }
}
