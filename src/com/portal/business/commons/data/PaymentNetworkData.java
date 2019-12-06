package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Currency;
import com.portal.business.commons.models.DeliveryForm;
import com.portal.business.commons.models.PaymentNetwork;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class PaymentNetworkData extends AbstractBusinessPortalWs{
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    
    public PaymentNetworkData(){
    
    }
    
//   public List<PaymentNetwork> loadPaymentNetwork() throws EmptyListException, GeneralException, NullParameterException {
//
//        List<PaymentNetwork> paymentNetwork = null;
//        
//        try {
//          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
//            Query query = createQuery("SELECT pa FROM PaymentNetwork pa");
//        //    query.setParameter(1, remittentId);
//            paymentNetwork = query.setHint("toplink.refresh", "true").getResultList();
//            }catch (NoResultException e) {
//                    throw new EmptyListException(e.getMessage());
//            }catch (Exception e) {
//                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
//        }
//            return paymentNetwork;
//    }
   
    public List<PaymentNetwork> loadPaymentNetwork() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<PaymentNetwork> paymentNetworks = (List<PaymentNetwork>) listEntities(PaymentNetwork.class, request, logger, getMethodName());
        return paymentNetworks;
    }
   
   
    public PaymentNetwork loadPaymentNetworkById(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        PaymentNetwork paymentNetwork = (PaymentNetwork) loadEntity(PaymentNetwork.class, request, logger, getMethodName());
        return paymentNetwork;
    }
       
   public List<PaymentNetwork> loadBypaymentNetworkTypeId(int paymentNetworkTypeId) throws EmptyListException, GeneralException, NullParameterException {

        List<PaymentNetwork> paymentNetwork = null;
        
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT pa FROM PaymentNetwork pa WHERE pa.paymentNetworkType.id =?1");
            query.setParameter(1, paymentNetworkTypeId);
            paymentNetwork = query.setHint("toplink.refresh", "true").getResultList();
            }catch (NoResultException e) {
                    throw new EmptyListException(e.getMessage());
            }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
            return paymentNetwork;
    }
    
    public PaymentNetwork savePaymentNetwork(WsRequest request) throws GeneralException, NullParameterException {
        return (PaymentNetwork) saveEntity(request, logger, getMethodName());
    }
    
    public PaymentNetwork savePaymentNetwork(PaymentNetwork paymentNetwork) throws NullParameterException, GeneralException {
   
         if (paymentNetwork == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetwork"), null);
        }
         return (PaymentNetwork) saveEntity(paymentNetwork);
    
    }
    
    public List<PaymentNetwork> loadPaymentNetworkByCountryId(Long countryId) throws EmptyListException, GeneralException, NullParameterException {

        List<PaymentNetwork> paymentNetwork = null;
        
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT pa FROM PaymentNetwork pa WHERE pa.country.id =?1");
            query.setParameter(1, countryId);
            paymentNetwork = query.setHint("toplink.refresh", "true").getResultList();
            }catch (NoResultException e) {
                    throw new EmptyListException(e.getMessage());
            }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
            return paymentNetwork;
    }
    
     public List<DeliveryForm> loadDeliveryFormByPaymentNetworkId(Long paymentNetworkId) throws EmptyListException, GeneralException, NullParameterException {

        List<DeliveryForm> deliveryForms = null;
        
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT d.deliveryForm FROM PaymentNetworkHasDeliveryForm d WHERE d.paymentNetwork.id =?1");
            query.setParameter(1, paymentNetworkId);
            deliveryForms = query.setHint("toplink.refresh", "true").getResultList();
            }catch (NoResultException e) {
                    throw new EmptyListException(e.getMessage());
            }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
            return deliveryForms;
    }
}
