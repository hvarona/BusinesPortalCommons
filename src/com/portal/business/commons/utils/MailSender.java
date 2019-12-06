package com.portal.business.commons.utils;

import com.portal.business.commons.data.UtilsData;
import com.portal.business.commons.exceptions.GeneralException;


public class MailSender extends Thread {

    private UtilsData utilsData;
    private Mail mail;

    public MailSender(Mail mail) {
        utilsData = new UtilsData();
        this.mail = mail;
    }

    @Override
    public void run() {
        try {
            this.sendMail(mail);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMail(Mail mail) throws GeneralException {
        try {
            utilsData.sendMail(mail);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

}
