/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.NoWorkingDays;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
public class NoWorkingDaysData extends AbstractBusinessPortalWs {

     private static final Logger logger = Logger.getLogger(NoWorkingDaysData.class);

    public  Boolean checkIfNonWorkingDay(int day, int month, int year) {

            StringBuilder sqlBuilder = new StringBuilder("SELECT t FROM NoWorkingDays t WHERE t.day = ?1 AND t.month = ?2 AND t.year = ?3");
            //sqlBuilder.append(" AND t.transactionStatusId=").append("'").append(TransactionStatus.PROCESSED).append("'");
            Query query = entityManager.createQuery(sqlBuilder.toString());
            query.setParameter(1, day);
            query.setParameter(2, month);
            query.setParameter(3, year);
            
            
            
            Object result = null;
            Boolean checkeo=false;
            try{
                result =  query.getSingleResult();  
                NoWorkingDays noWorkingDay = (NoWorkingDays)result;
                
                if(noWorkingDay != null){
                checkeo = noWorkingDay.getEnabled();
                }
              
            }
            
            catch(NoResultException e){
                return checkeo;
            }
        return checkeo;

            
    }
    
    
        public NoWorkingDays saveNoWorkingDay(WsRequest request) throws GeneralException, NullParameterException{
        
                return (NoWorkingDays) saveEntity(request, logger, getMethodName());

    }
        
    public List<NoWorkingDays> loadNoWorkingDaysMio (WsRequest request){
        
        StringBuilder sqlBuilder = new StringBuilder("SELECT t FROM NoWorkingDays t WHERE t.year = ?1");
        //sqlBuilder.append(" AND t.transactionStatusId=").append("'").append(TransactionStatus.PROCESSED).append("'");
        Query query = entityManager.createQuery(sqlBuilder.toString());
        int year = 2017;
        query.setParameter(1, year);

        
        List<NoWorkingDays> lista;

        Object result = null;

            result = query.getResultList();
            lista = (List<NoWorkingDays>) result;

        return lista;
        
        
    }     
        
    
    public NoWorkingDays setNoWorkingDay(int day, int month, int year, Boolean status,String description) throws GeneralException, NullParameterException{
        
        NoWorkingDays noWorkingDays; 
        
        StringBuilder sqlBuilder = new StringBuilder("SELECT t FROM NoWorkingDays t WHERE t.day = ?1 AND t.month = ?2 AND t.year = ?3");

        Query query = entityManager.createQuery(sqlBuilder.toString());
        query.setParameter(1, day);
        query.setParameter(2, month);
        query.setParameter(3, year);
        
        Object result = null;

        try {
            // Check if the date already exists in the database
            result = query.getSingleResult();
            noWorkingDays = (NoWorkingDays) result;

            // It exists, so we update it
            noWorkingDays.setEnabled(status);
            noWorkingDays.setDescription(description);        

        } catch (NoResultException e) {
        // The day doesnt exist so we create it
        noWorkingDays = new NoWorkingDays();
        noWorkingDays.setDay(day);
        noWorkingDays.setMonth(month);
        noWorkingDays.setYear(year);
        noWorkingDays.setEnabled(status);
        noWorkingDays.setDescription(description);
        
        
        }
        
        return (NoWorkingDays) saveEntity(noWorkingDays);

    }
     
      
    public List<NoWorkingDays> loadNoWorkingDays(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<NoWorkingDays> noWorkingDaysList = null;
        try {
            noWorkingDaysList = (List<NoWorkingDays>) listEntities(NoWorkingDays.class, request, logger, getMethodName());
        } catch (Exception e) {
            System.out.println("No Working Days found");
        }
       
       // System.out.println("% % % % RESULT % % % " + noWorkingDaysList.toString() );
    return noWorkingDaysList;
    }
   
    
}
