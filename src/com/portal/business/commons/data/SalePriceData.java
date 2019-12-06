/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.CreditLimitExcededException;
import com.portal.business.commons.exceptions.DisabledAccountException;
import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.InvalidAccountException;
import com.portal.business.commons.exceptions.NegativeBalanceException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Account;
import com.portal.business.commons.models.SalePrice;
import com.portal.business.commons.models.SalePriceHistory;
import com.portal.business.commons.utils.AccountReference;
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
public class SalePriceData extends AbstractBusinessPortalWs {

     private static final Logger logger = Logger.getLogger(RemittanceData.class);

//    public  Boolean checkIfNonWorkingDay(int day, int month, int year) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//
//            //StringBuilder sqlBuilder = new StringBuilder("SELECT SUM(t.totalAmount) FROM remittent t WHERE t.creationDate BETWEEN ?1 AND ?2 AND t.storeId = ?3");
//            
//            StringBuilder sqlBuilder = new StringBuilder("SELECT t FROM NoWorkingDays t WHERE t.day = ?1 AND t.month = ?2 AND t.year = ?3");
//            //sqlBuilder.append(" AND t.transactionStatusId=").append("'").append(TransactionStatus.PROCESSED).append("'");
//            Query query = entityManager.createQuery(sqlBuilder.toString());
//            query.setParameter(1, day);
//            query.setParameter(2, month);
//            query.setParameter(3, year);
//            
//            
//            
//            Object result = null;
//            Boolean checkeo=false;
//            try{
//                result =  query.getSingleResult();  
//                NoWorkingDays noWorkingDay = (NoWorkingDays)result;
//                
//                if(noWorkingDay != null){
//                    System.out.println("$$$$$$$$$$$$ ENABLE "+noWorkingDay.getEnabled());
//                checkeo = noWorkingDay.getEnabled();
//                }
//              
//            }
//            
//            catch(NoResultException e){
//                return checkeo;
//            }
//        return checkeo;
//
//            
//    }
//     
     
     /**
     * Éste Método recibe un salePriceId
     * 
     *
     * @return Devuelve un SalePriceHistory conteniendo el precio actual
     * 
     */
      public SalePriceHistory loadLastSalePriceHistoryBySalePriceId(Long salePriceId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        
        System.out.println("* * * Entrando => loadLastSalePriceHistoryBySalePriceId");
        
        if (salePriceId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "salePriceId"), null);
        }
        
        SalePriceHistory salePriceHistory = null;
        
        try {
            Timestamp maxDate = (Timestamp) entityManager.createQuery("SELECT MAX(b.beginningDate) FROM SalePriceHistory b WHERE b.salePrice.id = " + salePriceId).getSingleResult();
            Query query = entityManager.createQuery("SELECT b FROM SalePriceHistory b WHERE b.beginningDate = :maxDate AND b.salePrice.id = " + salePriceId);
            query.setParameter("maxDate", maxDate);

            List result = (List) query.setHint("toplink.refresh", "true").getResultList();

            if (!result.isEmpty()) {
                salePriceHistory = ((SalePriceHistory) result.get(0));
            }
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        }
        return salePriceHistory;
    }
      
     /**
     * Éste Método recibe un salePriceId
     * 
     *
     * @param request
     * @return Devuelve un SalePriceHistory conteniendo el precio actual
     * @throws com.portal.business.commons.exceptions.GeneralException
     * @throws com.portal.business.commons.exceptions.RegisterNotFoundException
     * @throws com.portal.business.commons.exceptions.NullParameterException
     * 
     */
    public SalePrice loadSalePrice(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        
        return (SalePrice) loadEntity(SalePrice.class, request, logger, getMethodName());
    
    }
    
    
    public List<SalePrice> loadSalePriceList(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        
        List<SalePrice> salePriceList = null;
        
        try {            
            salePriceList = (List<SalePrice>) listEntities(SalePrice.class, request, logger, getMethodName());       
        } 
        
        catch (Exception e) {            
            System.out.println("No sale Price List found");        
        }
        
        return salePriceList;
    }


    /*public SalePrice loadSalePrice(String salePriceId) throws GeneralException, RegisterNotFoundException, NullParameterException {
        
        System.out.println("* * * Entrando => loadLastSalePriceHistoryBySalePriceId");

        if (salePriceId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "salePriceId"), null);
        }
        
        SalePrice salePrice = null;
        
                try {
            Timestamp maxDate = (Timestamp) entityManager.createQuery("SELECT MAX(b.beginningDate) FROM SalePriceHistory b WHERE b.salePrice.id = " + salePriceId).getSingleResult();
            Query query = entityManager.createQuery("SELECT b FROM SalePriceHistory b WHERE b.beginningDate = :maxDate AND b.salePrice.id = " + salePriceId);
            query.setParameter("maxDate", maxDate);

            List result = (List) query.setHint("toplink.refresh", "true").getResultList();

            if (!result.isEmpty()) {
                salePrice = ((SalePrice) result.get(0));
            }
        } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        }
        return salePrice;
        
        //return (SalePrice) loadEntity(SalePrice.class, request, logger, getMethodName());
        
        
    }
   */
    

    public SalePriceHistory createSalePriceHistory(Long salePriceId, float newAmount, int transferType, boolean isBalanceTranference) throws GeneralException, EmptyListException, NullParameterException, NegativeBalanceException, CreditLimitExcededException, RegisterNotFoundException {
        
        SalePriceData salePriceData = new SalePriceData();
        WsRequest request = new WsRequest();
        request.setParam(salePriceId);
        SalePrice salePrice = salePriceData.loadSalePrice(request);
        
        SalePriceHistory oldBalanceHistory = loadLastSalePriceHistoryBySalePriceId(salePriceId);
        
        float oldAmount = oldBalanceHistory != null ? oldBalanceHistory.getCurrentAmount().floatValue() : 0f;

        
        if(oldBalanceHistory != null){
            oldBalanceHistory.setEndingdate(new Timestamp(new Date().getTime()));
            saveSalePriceHistory(oldBalanceHistory);
        }
        
        SalePriceHistory newSalePriceHistory = new SalePriceHistory();
        newSalePriceHistory.setSalePrice(salePrice);
        newSalePriceHistory.setBeginningDate(new Timestamp(new Date().getTime()));
        newSalePriceHistory.setOldAmount(oldAmount);
        newSalePriceHistory.setCurrentAmount(newAmount);
        newSalePriceHistory = saveSalePriceHistory(newSalePriceHistory);
        
        return newSalePriceHistory;
    }

    public SalePrice saveSalePrice(SalePrice salePrice) throws NullParameterException, GeneralException {
        if (salePrice == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "store"), null);
        }
        return (SalePrice) saveEntity(salePrice);
    }
    
 
    public SalePrice saveSalePrice(WsRequest request) throws GeneralException, NullParameterException{
        
        return (SalePrice) saveEntity(request, logger, getMethodName());

    }
    
  
    
    public SalePriceHistory saveSalePriceHistory(SalePriceHistory salePriceHistory) throws NullParameterException, GeneralException {
        if (salePriceHistory == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "store"), null);
        }
        return (SalePriceHistory) saveEntity(salePriceHistory);
    }

    public SalePrice loadSalesPriceByCountry(Long countryId, Long salesTypeId, Long corresponderId) throws NullParameterException,RegisterNotFoundException, GeneralException {
        if (countryId == null) {
           throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "countryId"), null);
        }  else if (salesTypeId == null) {
           throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "salesTypeId"), null);
        } else if (salesTypeId == null) {
           throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "corresponderId"), null);
        }
        SalePrice salePrice = null;
        try {
            Query query = null;
            query = createQuery("SELECT s FROM SalePrice s WHERE s.country.id=?1 AND s.saleType.id=?2 AND s.correspondent.id=?3");
            query.setParameter("1", countryId);
            query.setParameter("2", salesTypeId);
            query.setParameter("3", corresponderId);
            salePrice = (SalePrice) query.setHint("toplink.refresh", "true").getSingleResult();
          } catch (NoResultException ex) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), "SalePriceHistory"), null);
        }
        return salePrice;
    }

    
}
