/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.CreditLimitExcededException;
import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NegativeBalanceException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Remittance;
import com.portal.business.commons.models.RemittanceHasRemittenceStatus;
import com.portal.business.commons.models.RemittanceStatus;
import com.portal.business.commons.models.User;
import com.portal.business.commons.utils.EjbConstants;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class RemittanceHasRemittenceStatusData extends AbstractBusinessPortalWs {

     private static final Logger logger = Logger.getLogger(RemittanceHasRemittenceStatusData.class);
     private RemittanceData remittanceData  = new RemittanceData();
     private UserData userData =  new UserData();
     /**
     * Éste Método recibe un salePriceId
     * 
     *
     * @return Devuelve un SalePriceHistory conteniendo el precio actual
     * 
     */     
     public RemittanceHasRemittenceStatus loadLastRemittenceStatusByRemittenceStatusId(Long remittanceId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        
        System.out.println("* * * Entrando => loadLastRemittenceStatusByRemittenceStatusId");
        
        if (remittanceId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittenceId"), null);
        }
        
//        RemittanceStatus remittanceStatus = new RemittanceStatus(remittenceStatusId);

        
        RemittanceHasRemittenceStatus remittanceHasRemittenceStatus = null;
        
        try {
            Timestamp maxDate = null;
            //(Timestamp) entityManager.createQuery("SELECT MAX(b.beginningDate) FROM RemittanceHasRemittenceStatus b WHERE b.remittenceStatus.id = " + remittenceStatusId).getSingleResult();
            Query query = entityManager.createQuery("SELECT b FROM RemittanceHasRemittenceStatus b WHERE b.beginningDate = (SELECT MAX(c.beginningDate) FROM RemittanceHasRemittenceStatus c WHERE c.remittance.id = "+ remittanceId +  ") AND b.remittance.id = " + remittanceId);
            //query.setParameter("maxDate", maxDate);

            List result = (List) query.setHint("toplink.refresh", "true").getResultList();

            if (!result.isEmpty()) {
                remittanceHasRemittenceStatus = ((RemittanceHasRemittenceStatus) result.get(0));
            }
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        }
        return remittanceHasRemittenceStatus;
    }
      
     
    public RemittanceHasRemittenceStatus saveRemittanceHasRemittenceStatus(RemittanceHasRemittenceStatus remittanceHasRemittenceStatus) throws NullParameterException, GeneralException {
        if (remittanceHasRemittenceStatus == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "store"), null);
        }
        return (RemittanceHasRemittenceStatus) saveEntity(remittanceHasRemittenceStatus);
    }
    
    public RemittanceHasRemittenceStatus saveRemittanceHasRemittenceStatus(WsRequest request) throws GeneralException, NullParameterException {

        return (RemittanceHasRemittenceStatus) saveEntity(request, logger, getMethodName());

    }
    
    // $$$ =================
        public RemittanceHasRemittenceStatus updateRemittenceStatus(Remittance remittance, Long remittenceStatusId, Long userId, String comment) throws GeneralException, EmptyListException, NullParameterException, RegisterNotFoundException {
        
        RemittanceHasRemittenceStatus remittanceHasRemittenceStatus = new RemittanceHasRemittenceStatus();
        WsRequest request = new WsRequest();
        request.setParam(remittenceStatusId);
        RemittanceStatus statusInit = remittanceData.loadRemittenceStatus(request);
        request.setParam(userId);
        User user = userData.loadUser(request);
        RemittanceHasRemittenceStatus oldRemittanceHasRemittenceStatus = null;
        try{
            oldRemittanceHasRemittenceStatus = loadLastRemittenceStatusByRemittenceStatusId(remittance.getId());
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(oldRemittanceHasRemittenceStatus != null){
            oldRemittanceHasRemittenceStatus.setEndingDate(new Timestamp(new Date().getTime()));
            saveRemittanceHasRemittenceStatus(oldRemittanceHasRemittenceStatus);
        }
        
        RemittanceHasRemittenceStatus newRemittanceHasRemittenceStatus = new RemittanceHasRemittenceStatus();
        newRemittanceHasRemittenceStatus.setRemittenceStatus(statusInit);
        newRemittanceHasRemittenceStatus.setRemittance(remittance);
        newRemittanceHasRemittenceStatus.setBeginningDate(new Timestamp(new Date().getTime()));
        newRemittanceHasRemittenceStatus.setEndingDate(null);
        newRemittanceHasRemittenceStatus.setUser(user);
        newRemittanceHasRemittenceStatus.setComments(comment);
        newRemittanceHasRemittenceStatus = saveRemittanceHasRemittenceStatus(newRemittanceHasRemittenceStatus);
        
        return newRemittanceHasRemittenceStatus;
    }
        
        
        
        public RemittanceHasRemittenceStatus lastPrueba(Long remittenceStatusId) throws EmptyListException, GeneralException, NullParameterException {

        List<RemittanceHasRemittenceStatus> ratePaymentNetwork = null;
        RemittanceHasRemittenceStatus objValidar = null;
        
        try{
            
            Query query = createQuery("SELECT b FROM RemittanceHasRemittenceStatus b WHERE b.beginningDate = (SELECT MAX(a.beginningDate) FROM RemittanceHasRemittenceStatus a WHERE a.id = "+remittenceStatusId+") AND b.id =" +remittenceStatusId);
            ratePaymentNetwork = query.setHint("toplink.refresh", "true").getResultList();
            objValidar = ratePaymentNetwork.get(0);
            
            
       }catch (NoResultException e){
                    throw new EmptyListException(e.getMessage());
       }catch (Exception e) {
                    throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
       }
      
        return objValidar;
   }

        
        public List<RemittanceHasRemittenceStatus> loadRemittanceHasRemittenceStatus(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<RemittanceHasRemittenceStatus> remittanceHasRemittenceStatusList = null;
        try {
            remittanceHasRemittenceStatusList = (List<RemittanceHasRemittenceStatus>) listEntities(RemittanceHasRemittenceStatus.class, request, logger, getMethodName());
        } catch (Exception e) {
            System.out.println("No Working Days found");
        }

        // System.out.println("% % % % RESULT % % % " + remittanceHasRemittenceStatusList.toString() );
        return remittanceHasRemittenceStatusList;
    }

    public List<RemittanceHasRemittenceStatus> loadRemittanceHasRemittenceStatusList(WsRequest request) {
        
        System.out.println("-------%%%% Entrando en LOAD");
        List<RemittanceHasRemittenceStatus> remittanceHasRemittenceStatusList = null;
        
        try {            
            remittanceHasRemittenceStatusList = (List<RemittanceHasRemittenceStatus>) listEntities(RemittanceHasRemittenceStatus.class, request, logger, getMethodName());       
        } 
        
        catch (Exception e) {            
            System.out.println("No RemittanceHasRemittenceStatus List found");        
        }
        
        return remittanceHasRemittenceStatusList;    }

  
}
