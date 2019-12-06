package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.PaymentType;
import java.util.List;
import org.apache.log4j.Logger;


/**
 *
 * @author usuario
 */
public class PaymentTypeData  extends AbstractBusinessPortalWs {
     
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    

    public List<PaymentType> getPaymentTpe(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<PaymentType>) listEntities(PaymentType.class, request, logger, getMethodName());
    }


    
}
