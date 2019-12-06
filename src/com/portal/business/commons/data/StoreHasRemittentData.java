package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.StoreHasRemittent;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class StoreHasRemittentData extends AbstractBusinessPortalWs {
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    
    public StoreHasRemittentData(){
    
    }
    
    public List<StoreHasRemittent> loadStoreHasRemittentData() throws EmptyListException, GeneralException, NullParameterException {

         List<StoreHasRemittent> storeHasRemittent = null;
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT re FROM StoreHasRemittent re");
        //    query.setParameter(1, remittentId);
            storeHasRemittent = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException e) {
            throw new EmptyListException(e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return storeHasRemittent;
    }
   
    public List<StoreHasRemittent> loadStoreHasRemittentData(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<StoreHasRemittent>) listEntities(StoreHasRemittent.class, request, logger, getMethodName());
    }
    
  public StoreHasRemittent saveStoreHasRemittentData(WsRequest request) throws GeneralException, NullParameterException {
        return (StoreHasRemittent) saveEntity(request, logger, getMethodName());
    }
    
    public StoreHasRemittent saveStoreHasRemittentData(StoreHasRemittent storeHasRemittent) throws NullParameterException, GeneralException {
   
         if (storeHasRemittent == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "storeHasRemittent"), null);
        }
         return (StoreHasRemittent) saveEntity(storeHasRemittent);
    
    }

    
    
}
