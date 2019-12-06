package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;

import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.models.StoreBalance;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author usuario
 */
public class StoreBalanceData extends AbstractBusinessPortalWs {

    private static final Logger logger = Logger.getLogger(StoreBalanceData.class);

//public StoreBalance setStoreLimit(Long storeId, Float amount) throws GeneralException, NullParameterException{
//    
//    StoreBalance storeBalance = null;
//    
//    String queryStr = "SELECT s FROM remettences.StoreBalance \n"
//            + "WHERE storeid = " + storeId ;
//    
//    StringBuilder sqlBuilder = new StringBuilder(queryStr);
//    Query query = entityManager.createNativeQuery(sqlBuilder.toString());
//    
//    try {
//        // Check if the storeclose already exists in the database
//        Object result = query.getSingleResult();
//
//        storeBalance = (StoreBalance) result ;
//        
//        storeBalance.setLimit(storeBalance.getLimit() - amount);
//        
//      
//
//        } catch (NoResultException e) {
//            // The storeclose doesnt exist so we create it
//            storeBalance = null;
//        }
//    
//    return (StoreBalance) saveEntity(storeBalance);
//}
    public StoreBalance setStoreLimit(Long storeId) throws GeneralException, RegisterNotFoundException, NullParameterException {

        RemittanceData remittanceData = new RemittanceData();
        StoreData storeData = new StoreData();

        Float amount = remittanceData.getCurrentRemittanceAmountByStore(storeId, 0);
        Store store = null;

        WsRequest request = new WsRequest();
        request.setParam(storeId);

        try {

            store = storeData.loadStore(request);

        } catch (RegisterNotFoundException e) {

            System.out.println("ERROR: No Store with ID " + storeId);

        }

        StoreBalance storeBalance = null;

        String queryStr = "SELECT t FROM StoreBalance t WHERE t.store.id = " + storeId;

        StringBuilder sqlBuilder = new StringBuilder(queryStr);
        Query query = entityManager.createQuery(sqlBuilder.toString());
        System.out.println("%%%%%%%%%%%% HOLAAAA ");
        try {
            // Check if the storebalance already exists in the database
            Object result = query.getSingleResult();
            storeBalance = (StoreBalance) result;

        } catch (NoResultException e) {
            // The storeclose doesnt exist so we create it
            storeBalance = new StoreBalance();
            Timestamp closeDate = new Timestamp(new Date().getTime());
            storeBalance.setBeginningDate(closeDate);
            storeBalance.setStore(store);
            storeBalance.setCreditLimit(0F);

        }

        storeBalance.setCreditLimit(storeBalance.getCreditLimit() - amount);

        return (StoreBalance) saveEntity(storeBalance);

    }

    public StoreBalance saveStoreBalance(WsRequest request) throws GeneralException, NullParameterException {

        return (StoreBalance) saveEntity(request, logger, getMethodName());

    }

    public List<StoreBalance> loadStoreBalance(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<StoreBalance> storeBalanceList = null;
        try {
            storeBalanceList = (List<StoreBalance>) listEntities(StoreBalance.class, request, logger, getMethodName());
        } catch (Exception e) {
            System.out.println("No StoreBalance found");
        }

        // System.out.println("% % % % RESULT % % % " + noWorkingDaysList.toString() );
        return storeBalanceList;
    }

}
