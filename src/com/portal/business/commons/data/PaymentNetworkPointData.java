package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.PaymentNetwork;
import com.portal.business.commons.models.PaymentNetworkPoint;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class PaymentNetworkPointData extends AbstractBusinessPortalWs{
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    
    public PaymentNetworkPointData(){
    
    }
    
   public List<PaymentNetworkPoint> loadPaymentNetworkPoint() throws EmptyListException, GeneralException, NullParameterException {

        List<PaymentNetworkPoint> paymentNetworkPoint = null;
        
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT pa FROM PaymentNetworkPoint pa");
        //    query.setParameter(1, remittentId);
            paymentNetworkPoint = query.setHint("toplink.refresh", "true").getResultList();
            }catch (NoResultException e) {
                    throw new EmptyListException(e.getMessage());
            }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
            return paymentNetworkPoint;
    }
   
    public PaymentNetworkPoint loadPaymentNetworkPoint(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        PaymentNetworkPoint paymentNetworkPoint = (PaymentNetworkPoint) loadEntity(PaymentNetwork.class, request, logger, getMethodName());
        return paymentNetworkPoint;
    }
    
    public PaymentNetworkPoint savePaymentNetworkPoint(WsRequest request) throws GeneralException, NullParameterException {
        return (PaymentNetworkPoint) saveEntity(request, logger, getMethodName());
    }
    
    public PaymentNetworkPoint savePaymentNetworkPoint(PaymentNetworkPoint paymentNetworkPoint) throws NullParameterException, GeneralException {
   
         if (paymentNetworkPoint == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetworkPoint"), null);
        }
         return (PaymentNetworkPoint) saveEntity(paymentNetworkPoint);
    
    }
}