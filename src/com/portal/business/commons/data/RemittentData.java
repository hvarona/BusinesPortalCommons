package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Remittent;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class RemittentData extends AbstractBusinessPortalWs{
   private static final Logger logger = Logger.getLogger(PersonData.class);
 
   public RemittentData(){}
    
  
    public List<Remittent> getRemitent(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Remittent>) listEntities(Remittent.class, request, logger, getMethodName());
    }
     
/*-----------------------------------------------------------------------------------------------*/
  
    public Remittent saveRemittent(Remittent remittent) throws NullParameterException, GeneralException {
        
        if (remittent == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittent"), null);
        }
        return (Remittent) saveEntity(remittent);
    }
    
  
}
