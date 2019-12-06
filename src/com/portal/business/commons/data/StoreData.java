package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.PreferenceField;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.models.StoreBalance;
import com.portal.business.commons.models.StoreSetting;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

/**
 *
 * @author usuario
 */
public class StoreData extends AbstractBusinessPortalWs {


  private static final Logger logger = Logger.getLogger(StoreData.class);



  public List<Store> getStore(WsRequest request) throws GeneralException, NullParameterException, EmptyListException {
        return (List<Store>) listEntities(Store.class, request, logger, getMethodName());
  }

  
    public List<Store> getStores() throws EmptyListException, GeneralException, NullParameterException {
        List<Store> stores = null;
        Query query = null;
        try {
            query = createQuery("SELECT c FROM Store c ORDER BY c.login");
            stores = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (stores.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return stores;
    }
  

    public List<Store> getStoresByConditions(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        Boolean isFilter = (Boolean) request.getParam();
        if (isFilter == null || isFilter.equals("null")) {
            isFilter = false;
        }
        Map orderField = new HashMap();
        orderField.put("id", QueryConstants.ORDER_DESC);
        return (List<Store>) createSearchQuery(Store.class, request, orderField, logger, getMethodName(), "stors", isFilter);
    }

    
     public List<Store> loadsStore(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Store>) listEntities(Store.class, request, logger, getMethodName());
    }
    public Store loadStore(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (Store) loadEntity(Store.class, request, logger, getMethodName());
    }


    public Store loadStoreByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

        List<Store> stores = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_EMAIL)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_EMAIL), null);
        }
        try {
            stores = (List<Store>) getNamedQueryResult(Store.class, QueryConstants.LOAD_STORE_BY_EMAIL, request, getMethodName(), logger, "Store");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "Store"), null);
        }

        return stores.get(0);
    }


    public Store loadStoreByLogin(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Store> stores = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_LOGIN)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_LOGIN), null);
        }
        try {
            stores = (List<Store>) getNamedQueryResult(Store.class, QueryConstants.LOAD_STORE_BY_LOGIN, request, getMethodName(), logger, "Store");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "Store"), null);
        }
        return stores.get(0);
    }
    
    
    public Store saveStore(WsRequest request) throws GeneralException, NullParameterException {
        return (Store) saveEntity(request, logger, getMethodName());
    }

    public Store saveStore(Store store) throws NullParameterException, GeneralException {
        if (store == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "store"), null);
        }
        return (Store) saveEntity(store);
    }
    
    public StoreSetting saveStoreSetting(WsRequest request) throws GeneralException, NullParameterException {
        return (StoreSetting) saveEntity(request, logger, getMethodName());
    }

    
    public List<StoreSetting> saveStoreSettings(WsRequest request) throws GeneralException, NullParameterException {
        Timestamp time = new Timestamp(new Date().getTime());
        List<StoreSetting> returnStoreSettings = new ArrayList<StoreSetting>();
        List<StoreSetting> storeSettings = (List<StoreSetting>) request.getParam();
        for (StoreSetting ss : storeSettings) {
            StoreSetting oldPv = null;
            ss.setBeginningDate(time);
            try {
                oldPv = getLastStoreSettingByPreferenceField(ss.getPreferenceField().getId(), ss.getStore().getId());
                if (oldPv != null) {
                    if (ss.getValue().equals(oldPv.getValue()) == false) //SETEO ENDING DAY  Y SALVO EL NUEVO VALOR
                    {
                        oldPv.setEndingDate(time);
                        saveEntity(oldPv, logger, getMethodName());
                        returnStoreSettings.add((StoreSetting) saveEntity(ss, logger, getMethodName()));
                    }
                }
            } catch (EmptyListException e) {
                e.printStackTrace();
            }
            if (oldPv == null) {
                returnStoreSettings.add((StoreSetting) saveEntity(ss, logger, getMethodName())); //SALVO EL NUEVO VALOR
            }        }

        return returnStoreSettings;
    }
    
   

    
    public StoreSetting loadActiveStoreSettingsByStoreIdAndFieldId(Long fieldId, Long storeId) throws GeneralException, RegisterNotFoundException, NullParameterException {

        StoreSetting storeSetting = null;
        try {
            Query query = null;

            query = createQuery("SELECT s FROM StoreSetting s WHERE s.preferenceField.id=?1 AND s.store.id=?2 AND s.endingDate IS NULL");
            query.setParameter("1", fieldId);
            query.setParameter("2", storeId);
            storeSetting = (StoreSetting) query.setHint("toplink.refresh", "true").getSingleResult();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (storeSetting == null) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return storeSetting;
    }
    
    public Map<Long, String> getLastStoreSettings(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        Map<Long, String> currentValues = new HashMap<Long, String>();
        Map<String, Object> params = request.getParams();
        List<PreferenceField> fields = this.getPreferenceFields(request);
        for (PreferenceField field : fields) {
            StoreSetting ss = getLastStoreSettingByPreferenceField(field.getId(), Long.valueOf("" + params.get("storeId")));
            if (ss != null) {
                currentValues.put(field.getId(), ss.getValue());
            }
        }
        return currentValues;
    }
    
    private StoreSetting getLastStoreSettingByPreferenceField(Long preferenceFieldId, Long storeId) throws GeneralException, NullParameterException, EmptyListException {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("preferenceFieldId", preferenceFieldId);
        params.put("storeId", storeId);
        WsRequest request = new WsRequest();
        request.setParams(params);
        request.setLimit(1);
        List<StoreSetting> preferences = new ArrayList<StoreSetting>();
        try {
            preferences = (List<StoreSetting>) getNamedQueryResult(StoreData.class, QueryConstants.STORE_SETTING_BY_PREFERENCE_FIELD, request, getMethodName(), logger, "storeSetting");
            return preferences.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<PreferenceField> getPreferenceFields(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        return (List<PreferenceField>) listEntities(PreferenceField.class, request, logger, getMethodName());
    }
    
    public StoreBalance setCurrentStoreCreditLimit(Long storeId) throws NullParameterException, GeneralException {
        
        StoreBalance storeBalance = null;
        
        int numDays = 1;
        
        RemittanceData remittanceData = new RemittanceData();
        
        Float amount = remittanceData.getCurrentRemittanceAmountByStore(storeId, numDays);
        
        

        return storeBalance;
        
    }
    


}
