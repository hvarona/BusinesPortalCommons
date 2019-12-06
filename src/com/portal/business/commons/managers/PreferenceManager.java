package com.portal.business.commons.managers;

//import com.alodiga.multilevelchannel.commons.ejbs.PreferencesEJB;
//import com.alodiga.multilevelchannel.commons.ejbs.UtilsEJB;
//import com.alodiga.multilevelchannel.commons.genericEJB.EJBRequest;
//import com.alodiga.multilevelchannel.commons.models.Enterprise;
//import com.alodiga.multilevelchannel.commons.models.PreferenceFieldEnum;
//import com.alodiga.multilevelchannel.commons.utils.EJBServiceLocator;
//import com.alodiga.multilevelchannel.commons.utils.EjbConstants;
//import com.alodiga.multilevelchannel.commons.utils.QueryConstants;
import com.portal.business.commons.data.PreferenceData;
import com.portal.business.commons.data.UtilsData;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Enterprise;
import com.portal.business.commons.utils.QueryConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class PreferenceManager {

    private static PreferenceManager instance;
    private Map<Long, Map<Long, String>> preferencesByEnterprise = new HashMap<Long, Map<Long, String>>();
    private Map<Long, String> preferences = new HashMap<Long, String>();
    private PreferenceData preferenceData;
    private UtilsData utilsData;

    public static synchronized PreferenceManager getInstance() throws Exception {
        if (instance == null) {
            instance = new PreferenceManager();
        }
        return instance;
    }

    public static void refresh() throws Exception {
        instance = new PreferenceManager();
    }

    private PreferenceManager() throws Exception {

        preferenceData = new PreferenceData();
        utilsData = new UtilsData();
        List<Enterprise> enterprises = utilsData.getEnterprises();
        for (Enterprise enterprise : enterprises) {

            WsRequest request = new WsRequest();
            Map params = new HashMap<String, Object>();
            params.put(QueryConstants.PARAM_ENTERPRISE_ID, enterprise.getId());
            request.setParams(params);
            try {
                preferences = preferenceData.getLastPreferenceValues(request);
                preferencesByEnterprise.put(enterprise.getId(), preferences);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }

    }

    public String getPreferenceValueByPreferenceId(Long preferenceFielId) {
        return preferences.get(preferenceFielId);
    }
    public String getPreferencesValueByEnterpriseAndPreferenceId(Long enterpriseId, Long preferenceFielId) {
        return preferencesByEnterprise.get(enterpriseId).get(preferenceFielId);
    }

    public Map<Long, String> getPreferences() {
        return preferences;
    }
}
