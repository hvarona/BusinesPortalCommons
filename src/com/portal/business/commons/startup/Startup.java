package com.portal.business.commons.startup;

import com.portal.business.commons.data.UtilsData;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.models.Language;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author hvarona
 */
public class Startup extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("BusinessPortal Checking Languages ...");
        try {
            /*UtilsData utilsdata = new UtilsData();
            for (Language lang : Languages.LANG) {
                try {

                    if (!lang.getId().equals(utilsdata.getLanguage(lang.getIso()).getId())) {

                    }
                } catch (RegisterNotFoundException | NullParameterException | GeneralException ex) {
                    System.out.println("Didn't found Language " + lang.getIso());

                }
            }*/
            System.out.println("---------- BussinessPortal Initialized successfully ----------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
