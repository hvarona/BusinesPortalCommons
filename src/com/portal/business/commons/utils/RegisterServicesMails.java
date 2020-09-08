package com.portal.business.commons.utils;

import java.util.ArrayList;

public class RegisterServicesMails {

    public static String REGISTER_FROM_MAIL = "info@alodiga.com";
    
    public static final String SUBMIT_SUBJECT = "Register Submitted";
    public static final String ACCEPT_SUBJECT = "Register Accepted";
    public static final String REJECT_SUBJECT = "Register Rejected";
    
    public static void sendRegisterSubmitMail(String to){
        StringBuilder body = new StringBuilder();
        Mail mail = new Mail();
        mail.setSubject(SUBMIT_SUBJECT);
        mail.setFrom(REGISTER_FROM_MAIL);
        mail.setBody(body.toString());
        
        ArrayList<String> recipients = new ArrayList();
        recipients.add(to);
        mail.setTo(recipients);
        
        RegisterMailSender sender = new RegisterMailSender(mail);
        sender.start();
    }
    
    public static void sendRegisterRejectedMail(String to){
        StringBuilder body = new StringBuilder();
        Mail mail = new Mail();
        mail.setSubject(REJECT_SUBJECT);
        mail.setFrom(REGISTER_FROM_MAIL);
        mail.setBody(body.toString());
        
        ArrayList<String> recipients = new ArrayList();
        recipients.add(to);
        mail.setTo(recipients);
        
        RegisterMailSender sender = new RegisterMailSender(mail);
        sender.start();
        
    }
    
    public static void sendRegisterAcceptMail(String to){
        StringBuilder body = new StringBuilder();
        Mail mail = new Mail();
        mail.setSubject(ACCEPT_SUBJECT);
        mail.setFrom(REGISTER_FROM_MAIL);
        mail.setBody(body.toString());
        
        ArrayList<String> recipients = new ArrayList();
        recipients.add(to);
        mail.setTo(recipients);
        
        RegisterMailSender sender = new RegisterMailSender(mail);
        sender.start();
        
    }

}
