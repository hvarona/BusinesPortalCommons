package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.RatePaymentNetwork;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class RatePaymentNetworkData extends AbstractBusinessPortalWs {
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    public RatePaymentNetworkData(){
    
    }
    
    public RatePaymentNetwork saveRatePaymentNetwork(RatePaymentNetwork ratePaymentNetwork) throws NullParameterException, GeneralException {
   
         if (ratePaymentNetwork == null) {
             
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ratePaymentNetwork"), null);
        
         }
        
         return (RatePaymentNetwork) saveEntity(ratePaymentNetwork);
     }
    
    public RatePaymentNetwork lastRatePaymentNetwork(Long paymentNetworkId) throws RegisterNotFoundException, GeneralException, NullParameterException {
        if (paymentNetworkId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetworkId"), null);
        }
        List<RatePaymentNetwork> ratePaymentNetwork = null;
        RatePaymentNetwork objValidar = null;

        try {

            Query query = createQuery("SELECT b FROM RatePaymentNetwork b WHERE b.beginingDate = (SELECT MAX(a.beginingDate) FROM RatePaymentNetwork a WHERE a.id = " + paymentNetworkId + ") AND b.id =" + paymentNetworkId);
            ratePaymentNetwork = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (ratePaymentNetwork.isEmpty()) {
            throw new RegisterNotFoundException("ExchangeRate Empty");
        }

        return ratePaymentNetwork.get(0);

   }
    
      public List<RatePaymentNetwork> loadPaymentNetwork(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<RatePaymentNetwork>) listEntities(RatePaymentNetwork.class, request, logger, getMethodName());
    }
      
    public List<RatePaymentNetwork> loadPaymentNetworks() throws EmptyListException, GeneralException, NullParameterException {

        List<RatePaymentNetwork> ratePaymentNetwork = null;
        RatePaymentNetwork objValidar = null;
        
        try{
            
            Query query = createQuery("SELECT b FROM RatePaymentNetwork b WHERE b.endingDate IS NULL ");
            ratePaymentNetwork = query.setHint("toplink.refresh", "true").getResultList();
           
       }catch (NoResultException e){
                    throw new EmptyListException(e.getMessage());
       }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
       }
      
        return ratePaymentNetwork;
   }
}
