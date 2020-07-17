package com.portal.business.commons.startup;

import com.portal.business.commons.data.BusinessData;
import com.portal.business.commons.data.BusinessSellData;
import com.portal.business.commons.data.PosData;
import com.portal.business.commons.data.StoreData;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.models.Business;
import com.portal.business.commons.models.BusinessSell;
import com.portal.business.commons.models.Pos;
import com.portal.business.commons.models.Store;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    }

    private float getRandomAmount() {
        int amount = (int) (Math.random() * 500);

        return (float) amount / 100;
    }

    private void createSellData() {
        try {
            int weeks = 8;
            BusinessSellData businessSellData = new BusinessSellData();

            BusinessData businessData = new BusinessData();
            Business business = businessData.getBusinessByCode("codigo1");
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < weeks; i++) {
                cal.roll(Calendar.WEEK_OF_YEAR, false);
            }
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, (int) (Math.random() * 29) + 30);
            if (businessSellData.getBusinessSalesNumber(business, cal.getTime(), new Date()) <= 0) {
                List<Pos> pos = new PosData().getPosList(business);
                List<Store> stores = new StoreData().getStores(business);

                while (cal.getTimeInMillis() < System.currentTimeMillis()) {
                    Date sellDate = cal.getTime();
                    float amount = getRandomAmount();
                    switch ((int) (Math.random() * 3)) {
                        case 0:
                            Pos ps = pos.get((int) (Math.random() * pos.size()));
                            addBusinessSellTransaction(business, ps.getStore(), ps, (long) (Math.random() * 2) + 1, sellDate, "AlodigaWallet", amount);
                            break;
                        case 1:
                            addBusinessSellTransaction(business, stores.get((int) (Math.random() * stores.size())), null, (long) (Math.random() * 2) + 1, sellDate, "AlodigaWallet", amount);
                            break;
                        case 2:
                        case 3:
                            addBusinessSellTransaction(business, null, null, (long) (Math.random() * 2) + 1, sellDate, "AlodigaWallet", amount);
                            break;

                    }
                    cal.setTimeInMillis(cal.getTimeInMillis() + ((int) (Math.random() * 30 * 60000)));
                    if (cal.get(Calendar.HOUR_OF_DAY) > 17) {
                        cal.roll(Calendar.DAY_OF_WEEK, true);
                        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                            cal.roll(Calendar.WEEK_OF_YEAR, true);
                            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        }
                        cal.set(Calendar.HOUR_OF_DAY, 9);
                        cal.set(Calendar.MINUTE, (int) (Math.random() * 29) + 30);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long addBusinessSellTransaction(Business business, Store store, Pos pos, long idWalletTransaction,
            Date date, String origin, float amount) {

        BusinessSell sell = new BusinessSell();
        sell.setBusiness(business);
        sell.setStore(store);
        sell.setPos(pos);
        sell.setAmount(amount);
        sell.setDateSell(date);
        sell.setIdWalletTransaction(idWalletTransaction);
        sell.setOrigin(origin);
        BusinessSellData businessSellData = new BusinessSellData();

        try {
            businessSellData.saveBusinessSell(sell);
        } catch (NullParameterException ex) {
        } catch (GeneralException ex) {
        }
        return sell.getId();
    }

}
