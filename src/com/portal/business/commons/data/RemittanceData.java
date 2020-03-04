package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.CreditLimitExcededException;
import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.MaxAmountAnnualPerReceiverException;
import com.portal.business.commons.exceptions.MaxAmountAnnualPerRemittentException;
import com.portal.business.commons.exceptions.MaxAmountAnnualPerStoreException;
import com.portal.business.commons.exceptions.MaxAmountDailyPerReceiverException;
import com.portal.business.commons.exceptions.MaxAmountDailyPerRemittentException;
import com.portal.business.commons.exceptions.MaxAmountDailyPerStoreException;
import com.portal.business.commons.exceptions.MaxAmountMonthlyPerReceiverException;
import com.portal.business.commons.exceptions.MaxAmountMonthlyPerRemittentException;
import com.portal.business.commons.exceptions.MaxAmountMonthlyPerStoreException;
import com.portal.business.commons.exceptions.MaxAmountPerRemettenceException;
import com.portal.business.commons.exceptions.NegativeBalanceException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.exceptions.RemittenceNotAvailableException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.managers.ContentManager;
import com.portal.business.commons.managers.PreferenceManager;
import com.portal.business.commons.models.PaymentMethod;
import com.portal.business.commons.models.PaymentNetwork;
import com.portal.business.commons.models.PaymentNetworkType;
import com.portal.business.commons.models.PreferenceFieldEnum;
import com.portal.business.commons.models.Receiver;
import com.portal.business.commons.models.Remittance;
import com.portal.business.commons.models.RemittanceStatus;
import com.portal.business.commons.models.RemittanceHasRemittenceStatus;
import com.portal.business.commons.models.Remittent;
import com.portal.business.commons.models.SaleType;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.utils.EjbUtils;

import java.util.ArrayList;

import com.portal.business.commons.models.StoreBalanceHistory;
import com.portal.business.commons.models.StoreSetting;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class RemittanceData extends AbstractBusinessPortalWs {

    private static final Logger logger = Logger.getLogger(RemittanceData.class);
    private StoreData storeData = new StoreData();
    public static final Long PROVIDER_SYSTEM_USER_ID = 1l;

    public Remittance saveRemittance(Remittance remittance) throws NullParameterException, GeneralException {
        if (remittance == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittance"), null);
        }
        return (Remittance) saveEntity(remittance);
    }

    public Remittance updateRemittance(Remittance remittance) throws NullParameterException, GeneralException, CreditLimitExcededException, NegativeBalanceException {
        System.out.println("---------------------SAVE REMITTANCE------------------");
        Map<String, Object> response = new HashMap<String, Object>();
        StoreBalanceHistory balanceHistory = new StoreBalanceHistory();
        List<StoreBalanceHistory> histories = new ArrayList<StoreBalanceHistory>();
        List<Remittance> wrongRemittances = new ArrayList<Remittance>();
        WsRequest request = new WsRequest();
        request.setParam(remittance.getStore().getId());
        Store store = null;
        try {
            store = storeData.loadStore(request);
        } catch (RegisterNotFoundException ex) {
        }
        float amount = 0f;
        try {
            if (!remittance.getStatus().getId().equals(RemittanceStatus.NULLED)) {
                amount = remittance.getTotalAmount();
                balanceHistory = (StoreBalanceHistory) createStoreBalanceHistory(remittance.getStore(), amount, 1, false);
                balanceHistory.setRemittance(remittance);
                histories.add(balanceHistory);
                remittance.setBalanceHistories(histories);
            }
            try {
                entityManager.getTransaction().begin();
                store = entityManager.merge(store);
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                if (entityManager.getTransaction().isActive()) {
                    entityManager.getTransaction().rollback();
                }
                throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ""), null);
            }

        } catch (CreditLimitExcededException m) {
            throw new CreditLimitExcededException(logger, sysError.format(EjbConstants.ERR_CREDIT_LIMIT_EXCEDED, this.getClass(), getMethodName(), "param"), null);
        } catch (NegativeBalanceException m) {
            throw new NegativeBalanceException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_BALANCE, this.getClass(), getMethodName(), "param"), null);
        } catch (Exception e) {
            remittance.setBalanceHistories(null);
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ""), null);
        }
        return remittance;

    }

    public boolean persistListObject(List data) {
        boolean success = false;
        EntityManager em = getEntityManagerWrapper().getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            for (Object entity : data) {
                if (entity instanceof RemittenceEntity) {
                    if (((RemittenceEntity) entity).getPk() == null) {
                        em.persist(entity);
                    } else {
                        em.merge(entity);
                    }
                }
            }
            et.commit();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
            makeRoolback(et);
        }
        return success;
    }

    private void makeRoolback(EntityTransaction transaction) {
        try {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (Exception ex1) {
            ex1.printStackTrace();

        }
    }

    public boolean validateRemittance(Remittance remittance) throws GeneralException, NullParameterException, RemittenceNotAvailableException, MaxAmountPerRemettenceException, MaxAmountDailyPerStoreException, MaxAmountMonthlyPerStoreException, MaxAmountAnnualPerStoreException {
        boolean remittenceAproved = true;
        if (remittance == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_REMITTANCE), null);
        }
        Long storeId = remittance.getStore().getId();
        Long enterpriseId = remittance.getStore().getId();
        PreferenceManager pManager = null;
        try {
            pManager = PreferenceManager.getInstance();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittenceAproved;
    }

    public boolean validateRemittent(Remittent remittent, Float amount) throws GeneralException, NullParameterException, RemittenceNotAvailableException, MaxAmountDailyPerRemittentException, MaxAmountMonthlyPerRemittentException, MaxAmountAnnualPerRemittentException {
        boolean remittentAproved = true;
        if (remittent == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_REMITTENT), null);
        } else if (amount == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_AMOUNT), null);
        }
        Long enterpriseId = 1L;
        PreferenceManager pManager = null;
        try {
            pManager = PreferenceManager.getInstance();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }

        Float maxAmountDailyRemittent = Float.parseFloat(pManager.getPreferencesValueByBusinessAndPreferenceId(enterpriseId, PreferenceFieldEnum.MAX_AMOUNT_DAILY_PER_REMITTENT.getId()));
        Float maxAmountMonthlyRemittent = Float.parseFloat(pManager.getPreferencesValueByBusinessAndPreferenceId(enterpriseId, PreferenceFieldEnum.MAX_AMOUNT_MONTHLY_PER_REMITTENT.getId()));
        Float maxAmountAnnualRemittent = Float.parseFloat(pManager.getPreferencesValueByBusinessAndPreferenceId(enterpriseId, PreferenceFieldEnum.MAX_AMOUNT_YEARLY_PER_REMITTENT.getId()));

        Float remittenceAmount = amount;
        float amountDaily = 0f;
        float amountMounthy = 0f;
        float amountAnnual = 0f;
        amountDaily = getCurrentRemittanceAmountByRemittent(remittent.getId(), 0);
        amountMounthy = getMounthlyRemittanceAmountByRemittent(remittent.getId());
        amountAnnual = getAnnualAmountRemittanceByRemittent(remittent.getId());
        if ((amountDaily + remittenceAmount) > maxAmountDailyRemittent) {
            throw new MaxAmountDailyPerRemittentException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_DAILY, this.getClass(), getMethodName(), maxAmountDailyRemittent + ""), null);
        }
        if ((amountMounthy + remittenceAmount) > maxAmountMonthlyRemittent) {
            throw new MaxAmountMonthlyPerRemittentException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_MOUNTHLY, this.getClass(), getMethodName(), maxAmountMonthlyRemittent + ""), null);
        }
        if ((amountAnnual + remittenceAmount) > maxAmountAnnualRemittent) {
            throw new MaxAmountAnnualPerRemittentException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_YEARLY, this.getClass(), getMethodName(), maxAmountAnnualRemittent + ""), null);
        }
        return remittentAproved;
    }

    public boolean validateReceiver(Receiver receiver, Float amount) throws GeneralException, NullParameterException, RemittenceNotAvailableException, MaxAmountDailyPerReceiverException, MaxAmountMonthlyPerReceiverException, MaxAmountAnnualPerReceiverException {
        boolean receiverAproved = true;
        if (receiver == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_RECEIVER), null);
        } else if (amount == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_AMOUNT), null);
        }
        Long enterpriseId = 1L;
        PreferenceManager pManager = null;
        try {
            pManager = PreferenceManager.getInstance();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        Float maxAmountDailyReceiver = Float.parseFloat(pManager.getPreferencesValueByBusinessAndPreferenceId(enterpriseId, PreferenceFieldEnum.MAX_AMOUNT_DAILY_PER_RECEIVER.getId()));
        Float maxAmountMonthlyReceiver = Float.parseFloat(pManager.getPreferencesValueByBusinessAndPreferenceId(enterpriseId, PreferenceFieldEnum.MAX_AMOUNT_MONTHLY_PER_RECEIVER.getId()));
        Float maxAmountAnnualReceiver = Float.parseFloat(pManager.getPreferencesValueByBusinessAndPreferenceId(enterpriseId, PreferenceFieldEnum.MAX_AMOUNT_YEARLY_PER_RECEIVER.getId()));

        Float remittenceAmount = amount;
        float amountDaily = 0f;
        float amountMounthy = 0f;
        float amountAnnual = 0f;
        amountDaily = getCurrentRemittanceAmountByReceiver(receiver.getId(), 0);
        amountMounthy = getMounthlyRemittanceAmountByReceiver(receiver.getId());
        amountAnnual = getAnnualAmountRemittanceByReceiver(receiver.getId());
        if ((amountDaily + remittenceAmount) > maxAmountDailyReceiver) {
            throw new MaxAmountDailyPerReceiverException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_DAILY, this.getClass(), getMethodName(), maxAmountDailyReceiver + ""), null);
        }
        if ((amountMounthy + remittenceAmount) > maxAmountMonthlyReceiver) {
            throw new MaxAmountMonthlyPerReceiverException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_MOUNTHLY, this.getClass(), getMethodName(), maxAmountMonthlyReceiver + ""), null);
        }
        if ((amountAnnual + remittenceAmount) > maxAmountAnnualReceiver) {
            throw new MaxAmountAnnualPerReceiverException(logger, sysError.format(EjbConstants.ERR_MAX_AMOUNT_YEARLY, this.getClass(), getMethodName(), maxAmountAnnualReceiver + ""), null);
        }
        return receiverAproved;
    }

    public List<Float> getEntireSalesAmountByStore(Long storeId) throws NullParameterException, GeneralException {
        List<Float> sales = new ArrayList<Float>();
        sales.add(getCurrentRemittanceAmountByStore(storeId, 0));
        sales.add(getCurrentRemittanceAmountByStore(storeId, 1));
        sales.add(getCurrentRemittanceAmountByStore(storeId, 7));
        sales.add(getCurrentRemittanceAmountByStore(storeId, 15));
        return sales;
    }

    public Float getCurrentRemittanceAmountByStore(Long storeId, int previousDays) throws NullParameterException, GeneralException {

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%% init method getCurrentRemittanceAmountByStore");

        if (storeId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);
            Calendar tomorrowsMidnite = (Calendar) todaysMidnite.clone();

            if (previousDays > 0) {
                todaysMidnite.add(Calendar.DAY_OF_YEAR, -1 * previousDays);
            }

            tomorrowsMidnite.add(Calendar.DAY_OF_YEAR, 1);
            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.storeId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", new Date(todaysMidnite.getTimeInMillis()));
            query.setParameter("2", new Date(tomorrowsMidnite.getTimeInMillis()));
            query.setParameter("3", storeId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        System.out.println("+++++ Amount: " + remittanceAmount);
        return remittanceAmount;
    }

    public Float getMounthlyRemittanceAmountByStore(Long storeId) throws NullParameterException, GeneralException {
        if (storeId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);

            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.storeId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDateMonth(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("2", EjbUtils.getEndingDateMonth(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("3", storeId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getAnnualAmountRemittanceByStore(Long storeId) throws NullParameterException, GeneralException {
        if (storeId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);
            Calendar tomorrowsMidnite = (Calendar) todaysMidnite.clone();
            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.storeId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDateAnnual(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("2", EjbUtils.getEndingDateAnnual(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("3", storeId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getCurrentRemittanceAmountByRemittent(Long remittentId, int previousDays) throws NullParameterException, GeneralException {
        if (remittentId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittentId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);
            Calendar tomorrowsMidnite = (Calendar) todaysMidnite.clone();

            if (previousDays > 0) {
                todaysMidnite.add(Calendar.DAY_OF_YEAR, -1 * previousDays);
            }

            tomorrowsMidnite.add(Calendar.DAY_OF_YEAR, 1);
            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.remittentId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", new Date(todaysMidnite.getTimeInMillis()));
            query.setParameter("2", new Date(tomorrowsMidnite.getTimeInMillis()));
            query.setParameter("3", remittentId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getMounthlyRemittanceAmountByRemittent(Long remittentId) throws NullParameterException, GeneralException {
        if (remittentId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);

            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.remittentId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDateMonth(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("2", EjbUtils.getEndingDateMonth(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("3", remittentId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getAnnualAmountRemittanceByRemittent(Long remittentId) throws NullParameterException, GeneralException {
        if (remittentId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {

            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);
            Calendar tomorrowsMidnite = (Calendar) todaysMidnite.clone();
            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.remittentId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDateAnnual(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("2", EjbUtils.getEndingDateAnnual(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("3", remittentId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getCurrentRemittanceAmountByReceiver(Long remittentId, int previousDays) throws NullParameterException, GeneralException {
        if (remittentId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittentId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);
            Calendar tomorrowsMidnite = (Calendar) todaysMidnite.clone();

            if (previousDays > 0) {
                todaysMidnite.add(Calendar.DAY_OF_YEAR, -1 * previousDays);
            }

            tomorrowsMidnite.add(Calendar.DAY_OF_YEAR, 1);
            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.remittentId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", new Date(todaysMidnite.getTimeInMillis()));
            query.setParameter("2", new Date(tomorrowsMidnite.getTimeInMillis()));
            query.setParameter("3", remittentId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getMounthlyRemittanceAmountByReceiver(Long receiverId) throws NullParameterException, GeneralException {
        if (receiverId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);

            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.receiverId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDateMonth(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("2", EjbUtils.getEndingDateMonth(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("3", receiverId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    public Float getAnnualAmountRemittanceByReceiver(Long receiverId) throws NullParameterException, GeneralException {
        if (receiverId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        Float remittanceAmount = null;
        try {
            Calendar todaysMidnite = Calendar.getInstance();
            todaysMidnite.set(Calendar.HOUR, 0);
            todaysMidnite.set(Calendar.MINUTE, 0);
            todaysMidnite.set(Calendar.SECOND, 0);
            todaysMidnite.set(Calendar.MILLISECOND, 0);
            todaysMidnite.set(Calendar.AM_PM, Calendar.AM);
            Calendar tomorrowsMidnite = (Calendar) todaysMidnite.clone();
            StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittance t,remittance_status_has_remittence rhs,remittance_status s WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.receiverId = ?3");
            sqlBuilder.append(" AND t.id=rhs.remittenceId AND rhs.endingDate is null AND rhs.remittenceStatusId=s.id and s.id NOT IN (5,6,7)");
            Query query = entityManager.createNativeQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDateAnnual(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("2", EjbUtils.getEndingDateAnnual(new Date(todaysMidnite.getTimeInMillis())));
            query.setParameter("3", receiverId);

            Double result = (Double) query.getSingleResult();
            remittanceAmount = result != null ? result.floatValue() : 0f;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
        return remittanceAmount;
    }

    private StoreBalanceHistory createStoreBalanceHistory(Store store, float transferAmount, int transferType, boolean isBalanceTranference) throws GeneralException, EmptyListException, NullParameterException, NegativeBalanceException, CreditLimitExcededException, RegisterNotFoundException {
        StoreBalanceHistory currentBalanceHistory = loadLastBalanceHistoryByStore(store.getId());
        float currentAmount = currentBalanceHistory != null ? currentBalanceHistory.getCurrentAmount().floatValue() : 0f;
        StoreBalanceHistory balanceHistory = new StoreBalanceHistory();
        balanceHistory.setStore(store);
        balanceHistory.setCreation(new Timestamp(new Date().getTime()));
        balanceHistory.setOldAmount(currentAmount);
        float newCurrentAmount = 0.0f;
        switch (transferType) {
            case 1:
                newCurrentAmount = currentAmount - transferAmount;
                break;
            case 2:
                newCurrentAmount = currentAmount + transferAmount;//SUMO AL MONTO ACTUAL (EL DESTINO)
                break;
        }
        balanceHistory.setCurrentAmount(newCurrentAmount);
        return balanceHistory;
    }

    private boolean ValidateBalance(Store store, float transferAmount) throws GeneralException, EmptyListException, NullParameterException, NegativeBalanceException, CreditLimitExcededException, RegisterNotFoundException {
        StoreBalanceHistory currentBalanceHistory = loadLastBalanceHistoryByStore(store.getId());
        float currentAmount = currentBalanceHistory != null ? currentBalanceHistory.getCurrentAmount().floatValue() : 0f;
        float newCurrentAmount = currentAmount - transferAmount;

        return true;
    }

    public StoreBalanceHistory loadLastBalanceHistoryByStore(Long storeId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        if (storeId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeId"), null);
        }
        StoreBalanceHistory balanceHistory = null;
        try {
            Timestamp maxDate = (Timestamp) entityManager.createQuery("SELECT MAX(b.creation) FROM StoreBalanceHistory b WHERE b.store.id = " + storeId).getSingleResult();
            Query query = entityManager.createQuery("SELECT b FROM StoreBalanceHistory b WHERE b.creation = :maxDate AND b.store.id = " + storeId);
            query.setParameter("maxDate", maxDate);

            //balanceHistory = (BalanceHistory) query.setHint("toplink.refresh", "true").getSingleResult();
            List result = (List) query.setHint("toplink.refresh", "true").getResultList();

            if (!result.isEmpty()) {
                balanceHistory = ((StoreBalanceHistory) result.get(0));
            }
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "BalanceHistory"), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "BalanceHistory"), null);
        }
        return balanceHistory;
    }

    public SaleType loadSaleType(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SaleType saleType = (SaleType) loadEntity(SaleType.class, request, logger, getMethodName());
        return saleType;
    }

    public List<SaleType> getSaleTypes(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<SaleType>) listEntities(SaleType.class, request, logger, getMethodName());
    }

    public PaymentMethod loadPaymentMethod(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PaymentMethod paymentMethod = (PaymentMethod) loadEntity(PaymentMethod.class, request, logger, getMethodName());
        return paymentMethod;
    }

    public List<PaymentMethod> getPaymentMethods(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<PaymentMethod>) listEntities(PaymentMethod.class, request, logger, getMethodName());
    }

    public PaymentNetworkType loadPaymentNetworkType(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PaymentNetworkType paymentNetworkType = (PaymentNetworkType) loadEntity(PaymentNetworkType.class, request, logger, getMethodName());
        return paymentNetworkType;
    }

    public List<PaymentNetworkType> getPaymentNetworkTypes(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<PaymentNetworkType>) listEntities(PaymentNetworkType.class, request, logger, getMethodName());
    }

    public List<PaymentNetwork> getPaymentNetwork(WsRequest request) throws GeneralException, NullParameterException, EmptyListException {
        return (List<PaymentNetwork>) listEntities(PaymentNetwork.class, request, logger, getMethodName());
    }

    public List<PaymentNetwork> getPaymentNetworkByConditions(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        Boolean isFilter = (Boolean) request.getParam();
        if (isFilter == null || isFilter.equals("null")) {
            isFilter = false;
        }
        Map orderField = new HashMap();
        orderField.put("id", QueryConstants.ORDER_DESC);
        return (List<PaymentNetwork>) createSearchQuery(PaymentNetwork.class, request, orderField, logger, getMethodName(), "paymentNetworks", isFilter);
    }

    public PaymentNetwork loadPaymentNetwork(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (PaymentNetwork) loadEntity(PaymentNetwork.class, request, logger, getMethodName());
    }

    public Remittance loadSingleRemittance(WsRequest request) throws NullParameterException, GeneralException, RegisterNotFoundException {
        return (Remittance) loadEntity(Remittance.class, request, logger, getMethodName());
    }

    public List<Remittance> loadRemittance(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Remittance>) listEntities(Remittance.class, request, logger, getMethodName());
    }

    public Remittance loadRemittanceById(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (Remittance) loadEntity(Remittance.class, request, logger, getMethodName());
    }

    public List<Remittance> loadRemittances(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Remittance>) listEntities(Remittance.class, request, logger, getMethodName());
    }

    public PaymentNetwork loadPaymentNetworkByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

        List<PaymentNetwork> paymentNetworks = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_EMAIL)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_EMAIL), null);
        }
        try {
            paymentNetworks = (List<PaymentNetwork>) getNamedQueryResult(Store.class, QueryConstants.LOAD_STORE_BY_EMAIL, request, getMethodName(), logger, "PaymentNetwork");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "PaymentNetwork"), null);
        }

        return paymentNetworks.get(0);
    }

    public PaymentNetwork loadPaymentNetworkByLogin(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<PaymentNetwork> paymentNetworks = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_LOGIN)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_LOGIN), null);
        }
        try {
            paymentNetworks = (List<PaymentNetwork>) getNamedQueryResult(Store.class, QueryConstants.LOAD_STORE_BY_LOGIN, request, getMethodName(), logger, "PaymentNetwork");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "PaymentNetwork"), null);
        }
        return paymentNetworks.get(0);
    }

    public PaymentNetwork savePaymentNetwork(WsRequest request) throws GeneralException, NullParameterException {
        return (PaymentNetwork) saveEntity(request, logger, getMethodName());
    }

    public PaymentNetwork savePaymentNetwork(PaymentNetwork paymentNetwork) throws NullParameterException, GeneralException {
        if (paymentNetwork == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetwork"), null);
        }
        return (PaymentNetwork) saveEntity(paymentNetwork);
    }

    public RemittanceStatus loadRemittenceStatus(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        RemittanceStatus remittenceStatus = (RemittanceStatus) loadEntity(RemittanceStatus.class, request, logger, getMethodName());
        return remittenceStatus;
    }

    public List<RemittanceStatus> getRemittenceStatus(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<RemittanceStatus>) listEntities(RemittanceStatus.class, request, logger, getMethodName());
    }

    public Remittance loadTransaction(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (Remittance) loadEntity(Remittance.class, request, logger, getMethodName());
    }

    public List<Remittance> getRemittances(Long storeId) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<Remittance> remittances = new ArrayList<Remittance>();

        Query query = null;
        try {
            //$$$ CHECK
            query = createQuery("SELECT r FROM Remittance r WHERE r.store.id=?1");
            query.setParameter("1", storeId);
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances;
    }

    public List<Remittance> getRemittancesByRemittentId(Long remittentId) throws GeneralException, NullParameterException, EmptyListException {
        if (remittentId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittentId"), null);
        }
        List<Remittance> remittances = new ArrayList<Remittance>();

        Query query = null;
        try {
            //$$$ CHECK
            query = createQuery("SELECT r FROM Remittance r WHERE r.remittent.id=?1");
            query.setParameter("1", remittentId);
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances;
    }

    public List<Remittance> getRemittancesByReceiverId(Long receiverId) throws GeneralException, NullParameterException, EmptyListException {
        if (receiverId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "receiverId"), null);
        }
        List<Remittance> remittances = new ArrayList<Remittance>();
        Query query = null;
        try {
            //$$$ CHECK
            query = createQuery("SELECT r FROM Remittance r WHERE r.receiver.id=?1");
            query.setParameter("1", receiverId);
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances;
    }

    public List<Remittance> getRemittances() throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<Remittance> remittances = new ArrayList<Remittance>();

        Query query = null;
        try {
            query = createQuery("SELECT r FROM Remittance r");
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances;
    }

    public List<Remittance> searchTransaction(WsRequest request) throws GeneralException, NullParameterException, EmptyListException {
        List<Remittance> remittances = new ArrayList<Remittance>();
        List<RemittanceHasRemittenceStatus> status = new ArrayList<RemittanceHasRemittenceStatus>();
        Map<String, Object> params = request.getParams();

        StringBuilder sqlBuilder = new StringBuilder("SELECT r.remittance FROM RemittanceHasRemittenceStatus r WHERE r.remittance.creationDate BETWEEN ?1 AND ?2");
//        StringBuilder sqlBuilder = new StringBuilder(SELECT * FROM remettences.remittance r,remettences.remittance_status_has_remittence s WHERE r.creationDate BETWEEN  '2016-11-01' AND  '2016-11-10' and r.storeId = 1 and r.id=s.remittenceId and s.remittenceStatusId=2);
        if (!params.containsKey(QueryConstants.PARAM_BEGINNING_DATE) || !params.containsKey(QueryConstants.PARAM_ENDING_DATE)) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "beginningDate & endingDate"), null);
        }
        if (params.containsKey(QueryConstants.PARAM_STORE_ID)) {
            sqlBuilder.append(" AND r.remittance.store.id=").append(params.get(QueryConstants.PARAM_STORE_ID));
        }
        if (params.containsKey(QueryConstants.PARAM_REMITTENCE_STATUS)) {
//            .append(" and r.remittenceStatus.endingDate is null")
            sqlBuilder.append(" AND r.remittenceStatus.id=").append(params.get(QueryConstants.PARAM_REMITTENCE_STATUS));
        }

        Query query = null;
        try {
            System.out.println("query:********" + sqlBuilder.toString());
            query = createQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDate((Date) params.get(QueryConstants.PARAM_BEGINNING_DATE)));
            query.setParameter("2", EjbUtils.getEndingDate((Date) params.get(QueryConstants.PARAM_ENDING_DATE)));
            if (request.getLimit() != null && request.getLimit() > 0) {
                query.setMaxResults(request.getLimit());
            }
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances;
    }

    public List<RemittanceHasRemittenceStatus> getRemittancesStatusByRemittanceId(Long remittanceId) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<RemittanceHasRemittenceStatus> remittenceStatuses = new ArrayList<RemittanceHasRemittenceStatus>();

        Query query = null;
        try {
            query = createQuery("SELECT r FROM RemittanceHasRemittenceStatus r WHERE r.remittance.id=?1");
            query.setParameter("1", remittanceId);
            remittenceStatuses = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittenceStatuses.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittenceStatuses;
    }

    public Remittance addRemittanceStatus(Remittance remittance, Long remittanceStatusId, Long userId, String comment) {
        try {
            ContentManager cManager = ContentManager.getInstance();
            RemittanceHasRemittenceStatusData hasRemittenceStatusData = new RemittanceHasRemittenceStatusData();
            RemittanceHasRemittenceStatus remittanceHasRemittenceStatus = hasRemittenceStatusData.updateRemittenceStatus(remittance, remittanceStatusId, userId, comment);
            RemittanceStatus status = cManager.getRemittanceStatusById(remittanceStatusId);
            remittance.setStatus(status);
            remittance = saveRemittance(remittance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return remittance;
    }

    public Remittance getRemittancesByRemittanceNumber(String remittanceNumber) throws GeneralException, NullParameterException, RegisterNotFoundException {
        if (remittanceNumber == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittanceNumber"), null);
        }
        List<Remittance> remittances = new ArrayList<Remittance>();
        Query query = null;
        try {
            //$$$ CHECK
            query = createQuery("SELECT r FROM Remittance r WHERE r.remittanceNumber=?1");
            query.setParameter("1", remittanceNumber);
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances.get(0);
    }

//	//FIXME Mover a RemittanceData
//	public RemittanceProviderResponse changeRemittanceStatus(RemittanceResponse remittanceResponse) {
//		RemittanceProviderResponse response = new RemittanceProviderResponse();
//		logger.debug("Executing changeRemittanceStatus wiht RemittanceResponse code:" + remittanceResponse.getCode() + " | message:" + remittanceResponse.getMessage());
//		Map<String, Object> params = new HashMap<String, Object>();
//		//params.put("", value);
//		WsRequest wsRequest = new WsRequest();
//		wsRequest.setParams(params);
//		logger.debug("Looking for remittance with provider id:" + remittanceResponse.getRemittanceProviderId());
//		Remittance remittance = null;
//		try {
//			remittance = this.getRemittancesByRemittanceNumber(remittanceResponse.getRemittanceProviderId());
//		} catch (com.remettence.commons.exceptions.GeneralException e) {
//			e.printStackTrace();
//		} catch (NullParameterException e) {
//			e.printStackTrace();
//		} catch (com.remettence.commons.exceptions.RegisterNotFoundException e) {
//			e.printStackTrace();
//		}
//		String providerCode = remittanceResponse.getProviderCode();
//		logger.debug("Remittance provider response code is:" +  remittanceResponse.getProviderCode());
//		switch (providerCode) {
//		
//		//Datos
//		case "0000":
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.SENT, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//				response.setProviderCode(providerCode);
//				response.setProviderMessage(remittanceResponse.getMessage());
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "1000":
//		case "1001":
//		case "1002":
//		case "1003":
//		case "1004":
//		case "1005":
//		case "1006":
//		case "1007":
//		case "1008":
//		case "1009":
//		case "1010":
//		case "1011":
//		case "1012":
//		case "1013":
//		case "1014":
//		case "1015":
//		case "1016":
//		case "1017":
//		case "1018":
//		case "1019":
//		case "1020":
//		case "1021":
//		case "1022":
//		case "1023":
//		case "1024":
//		case "1025":
//		case "1026":
//		case "1027":
//		case "1028":
//		case "1029":
//		case "1030":
//		case "1031":
//		case "1032":
//		case "1033":
//		case "1034":
//		case "1035":
//		case "1036":
//		case "1037":
//		case "1038":
//		case "1039":
//		case "1040":
//		case "1041":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.RETAINED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "1100":	
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.RETAINED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "1101":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.TRANSIT, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//			
//		//Cumplimiento
//		case "1200":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.RETAINED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//                        //Code: 1201 - Message : Monto de Cumplimiento excede en Nombre de Beneficiario
//		case "1201":
//                    	response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.RETAINED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "1202":
//		case "1203":
//		case "1204":
//		case "1205":
//		case "1206":
//                    //Code: 1207 - Message : En Lista negra Nombres Remitente
//		case "1207":
//                    response.setProviderCode(providerCode);
//                    response.setProviderMessage(remittanceResponse.getMessage());
//                    try {
//                        this.addRemittanceStatus(remittance, RemittanceStatus.RETAINED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//                        response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//                        response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//                    } catch (Exception e) {
//                        response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//                        response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//                    }
//                    break;
//		case "1208":
//		case "1209":
//		case "1210":
//		case "1211":
//		case "1212":
//		case "1213":
//		case "1214":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.NULLED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		//Anulacion
//		case "1300":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.NULLED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "9995":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		case "9996":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR));
//			break;
//		//Modificación
//		case "1400":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.SENT, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "1401":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.SENT, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "1402":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.SENT, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "9997":
//		case "9998":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR));
//			break;
//		//Reserva
//		case "1600":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.CONFIRMED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "9993":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		case "9994":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR));
//			break;
//		//Liberación
//		case "1700":
//			this.addRemittanceStatus(remittance, RemittanceStatus.TRANSIT, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//			break;
//		case "9990":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR));
//			break;
//		case "9991":
//			//El id de reserva no coincide con la remesa
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		case "9992":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		//PAGO
//		case "1800":
//			this.addRemittanceStatus(remittance, RemittanceStatus.DELIVERED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//			break;
//		case "9986":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR));
//			break;
//		case "9987":
//			//El id de reserva no coincide con la remesa
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		case "9988":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		case "9989":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.REMITTANCE_CURRENT_STATUS_ERROR));
//			break;
//		//Reversión
//		case "1900":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			try{
//				this.addRemittanceStatus(remittance, RemittanceStatus.CONFIRMED, PROVIDER_SYSTEM_USER_ID, providerCode + ": " + remittanceResponse.getMessage());
//				response.setCode(RemittanceProviderMessage.SUCCESSFUL_OPERATION);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.SUCCESSFUL_OPERATION));
//			}catch (Exception e) {
//				response.setCode(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS);
//				response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.EXCEPTION_CHANGING_REMITTANCE_STATUS));
//			}
//			break;
//		case "9982":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			//throw new PaymentReversalWasMadeException("Remittance id Not found in liberation. Provider Message " + providerCode + ": " + remittanceResponse.getMessage());
//			//throw new RemittanceExpireDateException("Remittance expire date for this operation. Provider Message " + providerCode + ": " + remittanceResponse.getMessage());
//			break;
//		case "9983":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			//throw new PaymentReversalWasMadeException("Remittance id Not found in liberation. Provider Message " + providerCode + ": " + remittanceResponse.getMessage());
//			break;
//		case "9984":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		case "9985":
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			response.setCode(RemittanceProviderMessage.RECORD_NOT_FOUND);
//			response.setMessage(RemittanceProviderMessage.codes.get(RemittanceProviderMessage.RECORD_NOT_FOUND));
//			break;
//		default:
//			response.setProviderCode(providerCode);
//			response.setProviderMessage(remittanceResponse.getMessage());
//			break;
//		}	
//			return response;
//	}
//	public boolean cancelRemittance(Remittance remittance, String reason, String comments) throws RemoteException{
//		boolean success = false;
//		if(remittance.getCorrespondent().getId() == Correspondent.REPCHAPINA){
//			RemittanceResponse providerResponse = null;
//			try {
//				providerResponse = redChapinaProvider.cancelRemittance(remittance.getId().toString(), reason, comments);
//				providerResponse.setRemittanceProviderId("Y" + remittance.getId().toString());
//				this.changeRemittanceStatus(providerResponse);
//				if(providerResponse.getProviderCode().equals("1300")){
//					success = true;
//				}else{
//					success = false;
//				}
//			} catch (RemoteException e) {
//				e.printStackTrace();
//				throw e;
//			} 
//		}
//		return success;
//	}
//    
    public void updateRemittanceNumber(Long remittentId) throws Exception {
        Query query = null;
        int row = 0;
        try {
            //$$$ CHECK
            query = createQuery("UPDATE Remittance r SET r.remittanceNumber =?1 WHERE r.id=?2");
            query.setParameter("1", "Y" + remittentId);
            query.setParameter("2", remittentId);
            executeQuery(query);
        } catch (Exception e) {
            throw e;
        }
        //return row;
    }

    public Remittent load(WsRequest request) throws NullParameterException, GeneralException, RegisterNotFoundException {
        return (Remittent) this.loadEntity(Remittent.class, request.getParam(), logger, "Load remitten");
    }

    public List<Remittance> getRemittancesByStatusId(Long statusId) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<Remittance> remittances = new ArrayList<Remittance>();

        Query query = null;
        try {
            query = createQuery("SELECT r FROM Remittance r where r.status.id=?1");
            query.setParameter("1", statusId);
            remittances = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (remittances.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return remittances;
    }
}
