package com.portal.business.commons.utils;

/**
 *
 * @author henry
 */
public class SendSmsThread extends Thread {

    private final String phone;
    private final String message;

    private final static long ENGLISH_LANGUAGE = 0;
    private final static long SPANISH_LANGUAGE = 1;

    private final static String USA_CODE = "1";
    private final static String VZA_CODE = "58";
    private final static String MEX_CODE = "52";

    public SendSmsThread(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }

    public void run() {
        //Solo aplica para dos o tres pasises si se desea hacer dinamicamente se debe agregar un plan de numeraciÃ²n
        String countryCode = phone.substring(0, 2);
        switch (countryCode) {
            case USA_CODE:
                //lo envia por USA
                //TwilioSmsSenderProxy proxy = new TwilioSmsSenderProxy();
                try {
//                    proxy.sendTwilioSMS(phone, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case VZA_CODE:
                //Venezuela  integras con Massiva
//                APIOperations aPIOperations = new APIOperations();
//                SendSmsMassiva sendSmsMassiva = new SendSmsMassiva();
                try {
                    //String response = aPIOperations.sendSmsSimbox(message, movil, userId);
//                String response = sendSmsMassiva.sendSmsMassiva(message, movil);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MEX_CODE:
                //lo envia por TWILIO A MEXICO
//                TwilioSmsSenderProxy proxy = new TwilioSmsSenderProxy();
//                proxy.sendTwilioSMS(movil, message);
                break;

        }
    }

    public static long getLangujeByPhoneNumber(String phone) {
        if (phone.substring(0, 1).equals(USA_CODE)) {
            return ENGLISH_LANGUAGE;
        } else {
            return SPANISH_LANGUAGE;
        }
    }
}
