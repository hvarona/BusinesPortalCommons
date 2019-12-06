package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.RemittentHasReceiver;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class RemittentHasReceiverData extends AbstractBusinessPortalWs {
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    
    public RemittentHasReceiverData(){
    
    }
    
   public List<RemittentHasReceiver> loadRemittentHasReceiver() throws EmptyListException, GeneralException, NullParameterException {

           List<RemittentHasReceiver> remittentHasReceiver = null;
        try {
            //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT pa FROM RemittentHasReceiver pa");
            //    query.setParameter(1, remittentId);
            remittentHasReceiver = query.setHint("toplink.refresh", "true").getResultList();
            return remittentHasReceiver;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(RemittentHasReceiverData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return remittentHasReceiver;
    }
   
//    public RemittentHasReceiver loadRemittentHasReceiver(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
//        RemittentHasReceiver remittentHasReceiver = (RemittentHasReceiver) loadEntity(RemittentHasReceiverData.class, request, logger, getMethodName());
//        return remittentHasReceiver;
//    }
    
    public List<RemittentHasReceiver> loadRemittentHasReceiver(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<RemittentHasReceiver>) listEntities(RemittentHasReceiver.class, request, logger, getMethodName());
    }
    
  public RemittentHasReceiver saveRemittentHasReceiver(WsRequest request) throws GeneralException, NullParameterException {
        return (RemittentHasReceiver) saveEntity(request, logger, getMethodName());
    }
    
    public RemittentHasReceiver saveRemittentHasReceiver(RemittentHasReceiver remittentHasReceiver) throws NullParameterException, GeneralException {
   
         if (remittentHasReceiver == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittentHasReceiver"), null);
        }
         return (RemittentHasReceiver) saveEntity(remittentHasReceiver);
    
    }

    
    
}
