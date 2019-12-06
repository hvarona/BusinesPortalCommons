package com.portal.business.commons.utils;

import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.models.Enterprise;
import com.portal.business.commons.models.Language;
import com.portal.business.commons.models.Remittance;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.models.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ServiceMails {

    //public static String SSI_GROUP_MAIL = "monitoreo_esp@alodiga.com";
    //public static String SAC_COORDINADORES_MAIL = "sac-coordinadores@alodiga.com";
    public static String SSI_GROUP_MAIL = "dramirez@alodiga.com";
    public static String SUPPORT_MAIL = "kbermudez@alodiga.com";
    public static String SAC_COORDINADORES_MAIL = "yalmea@alodiga.com";

    public static Mail getSuccessfulRemittanceMail(Store store, Remittance remittance) throws Exception {

        String currencySymbol = store.getEnterprise().getCurrency().getSymbol();
        String hello = "Hola";
        String subject = "Alodiga SP: Confirmaci&oacute;n de Compra de Saldo.";
        String text1 = "Confirmaci&oacute;n de Compra de Saldo. Alodiga Services Provider System";
        String text2 = "Datos de su compra:";
        String distributorName = "Cuenta: ";
        String transactionNumber = "Transaccion: ";
        String date = "Fecha: ";
        String amount = "Total Monto s/descuentos: ";
        String totalAmount = "Total Monto pagado: ";

        String moreInfo = "Para mayor informaci&oacute;n visiste";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        if (remittance.getLanguage().getId().equals(Language.ENGLISH)) {
            hello = "Hello";
            subject = "Balance Purchase Confirmation. Services Provider";
            text1 = "Balance Purchase Confirmation";
            text2 = "Purchase Data: ";
            distributorName = "Account: ";
            transactionNumber = "Transaction: ";
            date = "Date: ";
            amount = "Total Amount wo/discounts: ";
            totalAmount = "Total Paid Amount: ";
            moreInfo = "Form more info visit";
            mailInvite = "We invite you to continue enjoying the benefits and attractive products and services offered by Alodiga.";
            thanks = "Thank you for choosing Alodiga, Alodiga Mejora tu vida";
            messageFooter1 = "This message was sent from an email distributor solely for notification messages that are not supported. Do not respond to this communication";
            allRights = "All rights reserved";
        }
        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";
        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text1 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + " " + store.getFirstName()+" "+store.getLastName() + "&nbsp;"
                + "!<br/><br/>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + distributorName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + store.getFirstName()+" "+store.getLastName() + "(" + store.getLogin() + ")</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + transactionNumber + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + remittance.getId() + "</div></td></tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + date + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + GeneralUtils.date2String(remittance.getCreationDate(), GeneralUtils.FORMAT_DATE_USA) + "</div></td></tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + amount + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + remittance.getTotalAmount() + " " + currencySymbol + "</div></td></tr>"
                + "</table></div>"
                + "</th>"
                + "</tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights + "<br> </div></th></tr></table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(store.getEnterprise());
        mail.setSubject(subject);
        mail.setFrom(store.getEnterprise().getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(store.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

    public static Mail getPurchaseBalanceProccessError(Store store, Remittance remittance, String step, Exception ex) throws Exception {

        String hello = "Hola";
        String subject = "Alodiga SP: Error durante el proceso de compra.";
        String text1 = "Ha ocurrido un error durante el proceso de compra.";
        String text2 = "Datos del Error:";
        String distributorName = "Cuenta: ";
        String transactionNumber = "Transaccion: ";
        String date = "Fecha: ";
        String _step = "Momento donde ocurre el error: ";

        String moreInfo = "Para mayor informaci&oacute;n visiste";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";

        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text1 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + "&nbsp;!<br/><br/>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='60px'><td " + style2 + " width='105'><div align='left'>"
                + _step + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + step + "</div>"
                + "</td>"
                + "</tr>";

        if (ex != null && ex.getMessage() != null) {
            body += "<tr height='80px'><td " + style2 + " width='105'><div align='left' >Error:</div></td>"
                    + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                    + ex.getMessage() + "</div></td></tr>";
        }

        body += "<tr height='30px'><td " + style2 + " width='105'><div align='left'>"
                + distributorName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + store.getFirstName()+" "+store.getLastName() + "(" + store.getLogin() + ")</div>"
                + "</td>"
                + "</tr>";

        if (remittance != null && remittance.getId() != null) {
            body += "<tr height='30px'><td " + style2 + " width='105'><div align='left' >"
                    + transactionNumber + "</div></td>"
                    + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                    + remittance.getId() + "</div></td></tr>";
        }

        if (remittance != null && remittance.getId() != null) {
            body += "<tr height='30px'><td " + style2 + " width='105'><div align='left'>"
                    + date + "</div></td>"
                    + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                    + GeneralUtils.date2String(remittance.getCreationDate(), GeneralUtils.FORMAT_DATE_USA) + "</div></td></tr>";
        }

        body += "</table></div></th></tr>";
        body += "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights + "<br> </div></th></tr></table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(store.getEnterprise());
        mail.setSubject(subject);
        mail.setFrom(store.getEnterprise().getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(store.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

   

    public static Mail getUpdateNotificationMail(Enterprise enterprise, ArrayList<String> recipients, String providerName, String processName) throws GeneralException {

        String hello = "Hola";
        String subject = "Alodiga SP: Proceso de actualización.";
        String text1 = "Proceso de Actualizaci&oacute;n " + processName;
        String info = "Puede verificar la actualizaci&oacute;n de las comisiones para cada tipo de cuenta accediendo al area de administraci&oacute;n de la Aplicaci&oacute;n Alodiga Services Provider System.";
        String process = "Proceso de Actualizaci&oacute;n";
        String executed = "Ejecutado al:";
        String moreInfo = "Para mayor informaci&oacute;n visiste";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";

        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='570' border='0'>"
                + "<tr><th width='750' height='565'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text1 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + "&nbsp;!<br/><br/>"
                + text1 + "<br><br>"
                + "</p>"
                + "</th>"
                + "</tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='30px'><td " + style2 + " width='105'><div align='left'>Proveedor: </div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + providerName + "</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='105'><div align='left'>"
                + process + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + info + "</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='105'><div align='left'>"
                + executed + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + new Timestamp(new java.util.Date().getTime()) + "</div>"
                + "</td>"
                + "</tr>";

        body += "</table></div></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights + "<br> </div></th></tr></table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

    public static Mail getAccountRegistrationMail(User user, String password,Enterprise enterprise) throws Exception {

        String hello = "Hola";
        String subject = "Alodiga SP: Registro de Cuenta.";
        String text1 = "Bienvenid@ al Alodiga Services Provider(SP)!";
        String text2 = "Datos de su cuenta:";
        String text3 = "Registro de Cuenta.";
        String distributorName = "Cuenta: ";
        String login = "Usuario(Login): ";
        String pass = "Clave: ";
        String date = "Fecha de Creaci&oacute;n: ";

        String moreInfo = "Para acceder al sistema visite:";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";
        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text3 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + " " + user.getFirstName()+ " "+ user.getLastName()+ "&nbsp;" + "!</p>"
                + "<p align='left' class='Estilo11'>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + distributorName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + user.getFirstName()+ " "+ user.getLastName() + "</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + login + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + user.getLogin() + "</div></td></tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + pass + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + password + "</div></td></tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + date + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + GeneralUtils.date2String(user.getCreationDate(), GeneralUtils.FORMAT_DATE_USA)
                + "</div></td></tr></div></table>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://192.168.201.21'>  192.168.201.21</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights
                + "<br> </div></th></tr>"
                + "</table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(enterprise.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

    public static Mail getRecoveryPasswordMail(User user, String newPassword, Enterprise enterprise) throws GeneralException {
        String hello = "Hola";
        String subject = "Alodiga SP: Recuperación de clave.";
        String text1 = "Nos complace notificarle que su clave de acceso ha sido generada automaticamente.";
        String text2 = "Datos de su cuenta:";
        String text3 = "Recuperacion de clave.";
        String distributorName = "Cuenta: ";
        String login = "Usuario(Login): ";
        String pass = "Nueva Clave: ";
        String moreInfo = "Para acceder al sistema visite:";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";
        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text3 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + " " + user.getFirstName()+ " "+ user.getLastName() + "&nbsp;" + "!</p>"
                + "<p align='left' class='Estilo11'>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + distributorName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + user.getFirstName()+ " "+ user.getLastName() + "</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + login + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + user.getLogin() + "</div></td></tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + pass + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + newPassword + "</div></td></tr></div></table>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights
                + "<br> </div></th></tr>"
                + "</table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(enterprise.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

    public static Mail getUserRecoveryPasswordMail(User user, String newPassword, Enterprise enterprise) throws GeneralException {

        String hello = "Hola";
        String subject = "Alodiga SP: Recuperación de clave.";
        String text1 = "Nos complace notificarle que su clave de acceso ha sido generada automaticamente.";
        String text2 = "Datos de su cuenta:";
        String text3 = "Recuperacion de clave.";
        String distributorName = "Cuenta: ";
        String login = "Usuario(Login): ";
        String pass = "Nueva Clave: ";
        String moreInfo = "Para acceder al sistema visite:";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";

        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";
        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text3 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + " " + user.getFirstName() + " " + user.getLastName() + "&nbsp;" + "!</p>"
                + "<p align='left' class='Estilo11'>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left'>"
                + distributorName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + user.getFirstName() + " " + user.getLastName() + "</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + login + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + user.getLogin() + "</div></td></tr>"
                + "<tr height='30px'><td " + style2 + " width='305'><div align='left' >"
                + pass + "</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + newPassword + "</div></td></tr></div></table>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights
                + "<br> </div></th></tr>"
                + "</table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(enterprise.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

    public static Mail getTestMail(Enterprise enterprise, ArrayList<String> _recipients, String subject, String content) {

        String hello = "Hola";
        String text1 = "Este es un correo de prueba.";
        String text2 = "Contenido: ";
        String moreInfo = "Para acceder al sistema visite:";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";

        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";
        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text1 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + " &nbsp;!</p>"
                + "<p align='left' class='Estilo11'>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + content
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights
                + "<br> </div></th></tr>"
                + "</table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(enterprise.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.addAll(_recipients);
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }

   
   public static Mail getUpdateProcessErrorMail(Enterprise enterprise, String process, String error, Exception ex) throws Exception {

        String hello = "Hola";
        String subject = "Alodiga SP: Error durante el actualización Automática.";
        String text1 = "Ha ocurrido un error durante el actualizaci&oacute;n Autom&aacute;tica.";
        String text2 = "Datos del Error:";
        String processName = "Proceso: ";
        String _step = "Error: ";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";

        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text1 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + "&nbsp;!<br/><br/>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='60px'><td " + style2 + " width='105'><div align='left'>"
                + processName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + process + "</div>"
                + "</td>"
                + "</tr>"
                + "<tr height='30px'><td " + style2 + " width='105'><div align='left'>"
                + "Fecha:</div></td>"
                + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + GeneralUtils.date2String(new Timestamp(new java.util.Date().getTime()), GeneralUtils.FORMAT_DATE_USA) + "</div></td></tr>"
                + "<tr height='60px'><td " + style2 + " width='105'><div align='left'>"
                + _step + "</div></td>"
                + "<td>"
                + "<div align='left' style='line-height:1.2em;font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + error + "</div>"
                + "</td>"
                + "</tr>";

        if (ex != null && ex.getMessage() != null) {
            body += "<tr height='80px'><td " + style2 + " width='105'><div align='left' >Error:</div></td>"
                    + "<td><div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                    + ex.getMessage() + "</div></td></tr>";
        }

        body += "</table></div></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"

                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights + "<br> </div></th></tr></table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setTo(recipients);
        return mail;
    }

   

    public static Mail getNoticeOfSuspensionOfServiceMail(Enterprise enterprise, ArrayList<String> _recipients, String subject, String content) {

        String hello = "Hola";
        String text1 = "Alodiga SP: Notificacion del Servicio.";
        String text2 = "Contenido: ";
        String moreInfo = "Para mayor informaci&oacute;n Contáctenos  al personal administrativo";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";

        String style1 = "style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";
        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + text1 + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + " &nbsp;!</p>"
                + "<p align='left' class='Estilo11'>"
                + text1 + "<br><br></p>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + content
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + moreInfo
                + "<span style='font-size: 13px'> "
                + "<a href='http://sales.alodiga.com/'>  sales.alodiga.com</a></span></div>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights
                + "<br> </div></th></tr>"
                + "</table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(enterprise);
        mail.setSubject(subject);
        mail.setFrom(enterprise.getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(enterprise.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.addAll(_recipients);
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }



   
     public static Mail getAlertMail(Store store, Float numberTransaction) throws Exception {

        String hello = "Hola";
        String subject = "Alodiga SP - Alerta: Compras de TopUp.";
        String text1 = "Se han detectado "+ numberTransaction;
        String text2 = "Datos del Alerta:";
        String distributorName = "Cuenta: ";
        String _step = "Mensaje: ";

        String moreInfo = "Para mayor informaci&oacute;n visiste";
        String mailInvite = "Le invitamos a seguir disfrutando los beneficios y de los atractivos productos y servicios que le ofrece Alodiga.";
        String thanks = "Gracias por preferirnos, Alodiga Mejora tu vida";
        String messageFooter1 = "Este mensaje ha sido enviado desde una cuenta de correo electr&oacute;nico exclusivamente de notificaciones que no admite mensajes. No responda esta comunicaci&oacute;n.";
        String allRights = "Todos los derechos reservados";
        String style2 = "style='background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px'";

        String body = "";
        body = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>";
        body += "<html xmlns='http://www.w3.org/1999/xhtml'>";
        body += "<head>";
        body += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><style type='text/css'>.Estilo11 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #333333; font-size:13px; font-weight:bold;}</style><style type='text/css'>.Estilo12 {font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;}</style><style type='text/css'>.EstiloColumn {background-color: #555555;color:#7CBF4F;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;font-weight:bold;padding-left:10px}</style>" + "<div align='center'>"
                + "<table width='756' height='600' border='0'>"
                + "<tr><th width='750' height='595'><p>"
                + "<img src='http://sales.alodiga.com/images/img-alodiga-logo.png' align='left' width='114' height='90' longdesc='Logo alodiga' />"
                + "</p><p>&nbsp;</p>" + "<p>&nbsp;</p>"
                + "<table  width='730' border='0' >"
                + "<tr><th width='728' height='20' align='right' bgcolor='#80C454' style='color:#242424;font:12px/1.8em Arial,Helvetica,sans-serif,lighter;'>" + subject + "</th></tr>"
                + "<tr><th width='728' height='5' bgcolor='#232323'></th></tr>"
                + "</table>"
                + "<table width='728' border='0'>"
                + "<tr><th width='728'>"
                + "<p align='left' class='Estilo11'><br/><br/>&iexcl;"
                + hello + "&nbsp;!<br/><br/>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th><p align='left' style='font: 16px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:bold; display: table;  margin: 0; padding:0;' >"
                + text2
                + "</p></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr>"
                + "<th>"
                + "<div><table width='705' border='0' cellpadding='2' cellspancing='0' style='border:inherit'>"
                + "<tr height='60px'><td " + style2 + " width='105'><div align='left'>"
                + _step + "</div></td>"
                + "<td>"
                + "<p align='justify' style='font: 13px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; font-weight:normal; display: table;  margin: 0; padding:0;'>"
                + text1 + "</div></td>"
                + "</p>"
                + "<td>"
                + "</td>"
                + "</tr>";

        body += "<tr height='30px'><td " + style2 + " width='105'><div align='left'>"
                + distributorName + "</div></td>"
                + "<td>"
                + "<div align='left' style='font:13px/0.6em Arial,Helvetica,sans-serif,lighter; color: #666; font-size:13px;'>"
                + store.getFirstName() + " " + store.getLastName() + ")</div>"
                + "<td>"
                + "</td>"
                + "</tr>"
                + "</table></div></th></tr>"
                + "<tr height='3px'><th width='728' bgcolor='#232323'></th></tr>"
                + "<tr height='40px'>"
                + "<th height='40px'><div class='Estilo11' align='left'>"
                + "</th>"
                + "</tr>"
                + "<tr>"
                + "<th height='31' bordercolor='#999999'><div align='center'><p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;' >"
                + mailInvite
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + thanks
                + "</p>"
                + " <p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>"
                + messageFooter1
                + "</p>"
                + "</div>"
                + "</th>"
                + "</tr>"
                + " </table>"
                + "<div align='center'>"
                + "<p align='center' style='font: 10px/1.8em Arial,Helvetica,sans-serif,lighter ; color: #666; display: table;  margin: 0; padding:0;'>&copy; Copyright 2013 - Alodiga, C.A. " + allRights + "<br> </div></th></tr></table></div></body></html>";

        Mail mail = new Mail();
        mail.setEnterprise(store.getEnterprise());
        mail.setSubject(subject);
        mail.setFrom(store.getEnterprise().getEmail());
        mail.setBody(body);
        ArrayList<String> recipients = new ArrayList<String>();
        recipients.add(store.getEmail());
        mail.setTo(recipients);
        //Copia oculta
        recipients = new ArrayList<String>();
        recipients.add(SSI_GROUP_MAIL);
        recipients.add(SAC_COORDINADORES_MAIL);
        recipients.add(SUPPORT_MAIL);
        mail.setBcc(recipients);
        return mail;
    }


}
