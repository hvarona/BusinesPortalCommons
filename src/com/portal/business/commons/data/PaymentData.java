/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Payment;
import com.portal.business.commons.models.PaymentPatner;
import com.portal.business.commons.models.PaymentType;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author usuario
 */
public class PaymentData extends AbstractBusinessPortalWs {
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    
    
    public  Payment  savePayment(Payment payment) throws GeneralException, NullParameterException{
        try {
            payment = (Payment) saveEntity(payment);
        } catch (GeneralException ex) {
            throw new GeneralException(sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        } catch (NullParameterException ex) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "payment"), null);
        }
        return payment;
    }

    public PaymentType loadPaymentType(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        PaymentType paymentType = (PaymentType) loadEntity(PaymentType.class, request, logger, getMethodName());
        return paymentType;
    }

    public List<PaymentType> getPaymentTypes(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<PaymentType>) listEntities(PaymentType.class, request, logger, getMethodName());
    }


    public PaymentPatner loadPaymentPatner(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PaymentPatner paymentPatner = (PaymentPatner) loadEntity(PaymentPatner.class, request, logger, getMethodName());
        return paymentPatner;
    }
}
