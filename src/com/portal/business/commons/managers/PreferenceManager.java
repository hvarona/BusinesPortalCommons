package com.portal.business.commons.managers;

import com.portal.business.commons.data.PreferenceData;
import com.portal.business.commons.data.UserData;
import com.portal.business.commons.models.Business;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class PreferenceManager {

    private static PreferenceManager instance;
    private Map<Long, Map<Long, String>> preferencesByBusiness = new HashMap();
    private Map<Long, String> preferences = new HashMap();
    private PreferenceData preferenceData;
    private UserData userData;

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
        userData = new  UserData();
        List<Business> businesses = userData.getBusinesses();
        for (Business business : businesses) {
            
            try {
                preferences = preferenceData.getLastPreferenceValues(business);
                preferencesByBusiness.put(business.getId(), preferences);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }

    public String getPreferenceValueByPreferenceId(Long preferenceFielId) {
        return preferences.get(preferenceFielId);
    }
    public String getPreferencesValueByBusinessAndPreferenceId(Long businessId, Long preferenceFielId) {
        return preferencesByBusiness.get(businessId).get(preferenceFielId);
    }

    public Map<Long, String> getPreferences() {
        return preferences;
    }
}
