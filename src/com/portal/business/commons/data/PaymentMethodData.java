package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Currency;
import com.portal.business.commons.models.PaymentMethod;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class PaymentMethodData extends AbstractBusinessPortalWs{
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    
    public PaymentMethodData(){
    
    }
    
//   public List<PaymentMethod> loadPaymentMethod() throws EmptyListException, GeneralException, NullParameterException {
//
//        List<PaymentMethod> PaymentMethod = null;
//        
//        try {
//          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
//            Query query = createQuery("SELECT pa FROM PaymentMethod pa");
//        //    query.setParameter(1, remittentId);
//            PaymentMethod = query.setHint("toplink.refresh", "true").getResultList();
//            }catch (NoResultException e) {
//                    throw new EmptyListException(e.getMessage());
//            }catch (Exception e) {
//                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
//        }
//            return PaymentMethod;
//    }
   
    public List<PaymentMethod> loadPaymentMethod() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<PaymentMethod> PaymentMethods = (List<PaymentMethod>) listEntities(PaymentMethod.class, request, logger, getMethodName());
        return PaymentMethods;
    }
   
   
    public PaymentMethod loadPaymentMethodById(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        PaymentMethod PaymentMethod = (PaymentMethod) loadEntity(PaymentMethod.class, request, logger, getMethodName());
        return PaymentMethod;
    }
       
   public List<PaymentMethod> loadByPaymentMethodTypeId(int PaymentMethodTypeId) throws EmptyListException, GeneralException, NullParameterException {

        List<PaymentMethod> PaymentMethod = null;
        
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT pa FROM PaymentMethod pa WHERE pa.PaymentMethodType.id =?1");
            query.setParameter(1, PaymentMethodTypeId);
            PaymentMethod = query.setHint("toplink.refresh", "true").getResultList();
            }catch (NoResultException e) {
                    throw new EmptyListException(e.getMessage());
            }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
            return PaymentMethod;
    }
    
    public PaymentMethod savePaymentMethod(WsRequest request) throws GeneralException, NullParameterException {
        return (PaymentMethod) saveEntity(request, logger, getMethodName());
    }
    
    public PaymentMethod savePaymentMethod(PaymentMethod PaymentMethod) throws NullParameterException, GeneralException {
   
         if (PaymentMethod == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "PaymentMethod"), null);
        }
         return (PaymentMethod) saveEntity(PaymentMethod);
    
    }
}
