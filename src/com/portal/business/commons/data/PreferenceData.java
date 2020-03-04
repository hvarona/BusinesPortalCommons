package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.models.Business;
import com.portal.business.commons.models.Preference;
import com.portal.business.commons.models.PreferenceField;
import com.portal.business.commons.models.PreferenceValue;
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

    private PreferenceValue getLastPreferenceValueByPreferenceField(PreferenceField field, Business business) throws GeneralException, NullParameterException, EmptyListException {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PreferenceValue> cq = cb.createQuery(PreferenceValue.class);
            Root<PreferenceValue> from = cq.from(PreferenceValue.class);
            cq.select(from);
            cq.where(cb.and(
                    cb.equal(from.get("business"), business),
                    cb.and(
                            cb.equal(from.get("preferenceField"), field),
                            cb.isNull(from.get("endingDate"))))); //The preference needs to be active

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            query.setMaxResults(1);
            return (PreferenceValue) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public Map<Long, String> getLastPreferenceValues(Business business) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        Map<Long, String> currentValues = new HashMap();
        List<PreferenceField> fields = this.getPreferenceFields();
        for (PreferenceField field : fields) {
            PreferenceValue pv = getLastPreferenceValueByPreferenceField(field, business);
            if (pv != null) {
                currentValues.put(field.getId(), pv.getValue());
            }
        }
        return currentValues;
    }

    public List<PreferenceField> getPreferenceFields() throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<PreferenceField> preferenceFields = null;

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PreferenceField> cq = cb.createQuery(PreferenceField.class);
            Root<PreferenceField> from = cq.from(PreferenceField.class);
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

    public List<PreferenceValue> getPreferenceValuesByBusisnessAndField(PreferenceField field, Business business) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<PreferenceValue> preferenceValues = null;

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PreferenceValue> cq = cb.createQuery(PreferenceValue.class);
            Root<PreferenceValue> from = cq.from(PreferenceValue.class);
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

    public PreferenceValue loadActivePreferenceValuesByBusinessAndField(PreferenceField field, Business business) throws GeneralException, RegisterNotFoundException, NullParameterException {

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<PreferenceValue> cq = cb.createQuery(PreferenceValue.class);
            Root<PreferenceValue> from = cq.from(PreferenceValue.class);
            cq.select(from);
            cq.where(cb.and(
                    cb.equal(from.get("business"), business),
                    cb.and(
                            cb.equal(from.get("preferenceField"), field),
                            cb.isNull(from.get("endingDate"))))); //The preference needs to be active

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            query.setMaxResults(1);
            return (PreferenceValue) query.getSingleResult();
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }

    public List<PreferenceValue> savePreferenceValues(List<PreferenceValue> values) throws GeneralException, NullParameterException {
        Timestamp time = new Timestamp(new Date().getTime());
        List<PreferenceValue> returnValues = new ArrayList();
        for (PreferenceValue pv : values) {
            PreferenceValue oldPv = null;
            pv.setBeginningDate(time);
            try {
                oldPv = getLastPreferenceValueByPreferenceField(pv.getPreferenceField(), pv.getBusiness());
                if (oldPv != null) {
                    if (!pv.getValue().equals(oldPv.getValue())) {
                        oldPv.setEndingDate(time);
                        saveEntity(oldPv, LOG, getMethodName());
                        returnValues.add((PreferenceValue) saveEntity(pv, LOG, getMethodName()));
                    }
                }
            } catch (EmptyListException e) {
                e.printStackTrace();
            }
            if (oldPv == null) {
                returnValues.add((PreferenceValue) saveEntity(pv, LOG, getMethodName())); //SALVO EL NUEVO VALOR
            }
        }

        return returnValues;
    }

    public List<Preference> getPreferences() throws GeneralException, RegisterNotFoundException, EmptyListException {
        List<Preference> preferences = null;

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Preference> cq = cb.createQuery(Preference.class);
            Root<Preference> from = cq.from(Preference.class);
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

    public Preference getPreference(Long preferenceId) throws NullParameterException, GeneralException, RegisterNotFoundException {
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Preference> cq = cb.createQuery(Preference.class);
            Root<Preference> from = cq.from(Preference.class);
            cq.select(from);
            cq.where(cb.equal(from.get("id"), preferenceId));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            Preference preference = (Preference) query.getSingleResult();
            return preference;
        } catch (Exception ex) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }
}
