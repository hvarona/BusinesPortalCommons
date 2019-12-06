package com.portal.business.commons.utils;

import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.models.Remittance;
import com.portal.business.commons.models.Store;


public class ServiceMailDispatcher {

    public static void sendAlertMail(Store store, Float numberTransaction) throws GeneralException {
        System.out.println("sendAlertMail");
        try {
            Mail mail = ServiceMails.getAlertMail(store, numberTransaction);
            (new com.portal.business.commons.utils.MailSender(mail)).start();
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }
    
     public static void sendPurchaseBalanceErrorMail(Store store, Remittance remittance,String error, String step, Exception ex) {
        System.out.println("-------------- sendPinPurchaseErrorMail -------------- " + step);
        try {
              Mail mail = ServiceMails.getPurchaseBalanceProccessError(store, remittance, step, ex);
            (new MailSender(mail)).start();

        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    }
}
