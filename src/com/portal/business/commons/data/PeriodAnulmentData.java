package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.PeriodAnulment;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author frank
 */
public class PeriodAnulmentData extends AbstractBusinessPortalWs {
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);
    public PeriodAnulmentData(){}
    
     public List<PeriodAnulment> loadPeriodAnulment(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<PeriodAnulment>) listEntities(PeriodAnulment.class, request, logger, getMethodName());
    }
    
    public PeriodAnulment savePeriodAnulment(PeriodAnulment periodAnulment) throws NullParameterException, GeneralException {
        if (periodAnulment == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ratePaymentNetwork"), null);
         }
         return (PeriodAnulment) saveEntity(periodAnulment);
     }
    
    public PeriodAnulment lastPeriodAnulment(Long periodAnulmentId) throws EmptyListException, GeneralException, NullParameterException {

        List<PeriodAnulment> periodAnulment = null;
        PeriodAnulment objValidar = null;
        
        try{
            
            Query query = createQuery("SELECT b FROM PeriodAnulment b WHERE b.beginningDate = (SELECT MAX(a.beginningDate) FROM PeriodAnulment a WHERE a.id = "+periodAnulmentId+") AND b.id =" +periodAnulmentId);
            periodAnulment = query.setHint("toplink.refresh", "true").getResultList();
            objValidar = periodAnulment.get(0);
            
       }catch (NoResultException e){
                    throw new EmptyListException(e.getMessage());
       }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
       }
      
        return objValidar;
   }
    
}
     

