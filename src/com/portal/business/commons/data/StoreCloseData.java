package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;

import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.models.StoreClose;
import com.portal.business.commons.utils.EjbConstants;
import java.sql.Timestamp;
import java.util.List;
import org.apache.log4j.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis caamano
 */
public class StoreCloseData extends AbstractBusinessPortalWs {

    private static final Logger logger = Logger.getLogger(StoreCloseData.class);

    public List<StoreClose> getListStoreClose(Store store) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<StoreClose> listStoreClose;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<StoreClose> cq = cb.createQuery(StoreClose.class);
            Root<StoreClose> from = cq.from(StoreClose.class);
            cq.select(from);
            cq.where(cb.equal(from.get("store"), store));
            Query query = entityManager.createQuery(cq);
            listStoreClose = query.getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (listStoreClose == null || listStoreClose.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return listStoreClose;
    }

    public StoreClose setStoreClose(Long storeId, Timestamp closeDate) throws GeneralException, NullParameterException, RegisterNotFoundException {

        StoreClose storeClose = null;
        Store store = null;
        StoreData storeData = new StoreData();
        WsRequest request = new WsRequest();
        request.setParam(storeId);

        store = storeData.loadStore(request);

        StringBuilder sqlBuilder = new StringBuilder("SELECT t FROM StoreClose t WHERE t.store.id = " + storeId);// WHERE t.closedate = ?1 AND t.store.id = ?2");
        Query query = entityManager.createQuery(sqlBuilder.toString());
        Object result = null;

        try {
            // Check if the storeclose already exists in the database
            result = query.getSingleResult();
            storeClose = (StoreClose) result;

        } catch (NoResultException e) {
            // The storeclose doesnt exist so we create it
            storeClose = new StoreClose();
            storeClose.setCloseAmount(0F);  //setCloseamount(0);
            storeClose.setCloseDate(closeDate);  //setClosedate(closeDate);
            storeClose.setStore(store); //setStoreid(storeId);
        } catch (NullPointerException e) {
            System.out.println("The requested Store doesn't exist in Database");

        } finally {
        }

        /*
        TODO:
        1. LISTO ver porque al guardar la fecha de timestamp, cuando se persiste, se guarda en 0000000 y no sirve
        2. LISTO corregir el query que busca el store porque el  SELECT busca todos y debe filtrarse por id de tienda o algo asi
        o convertirlo a jpa nativo por medio de un comando
         */
        String queryStr = "    SELECT sum(totalAmount)\n"
                + "      FROM remettences.remittance a\n"
                + "INNER JOIN remettences.remittance_status_has_remittence b\n"
                + "        ON a.id = b.remittenceStatusId\n"
                + "     WHERE b.endingDate IS NULL\n"
                + "       AND b.remittenceStatusId not in (5,6,7)\n"
                + "       AND (a.storeId = " + storeId + ");";

        sqlBuilder = new StringBuilder(queryStr);
        query = entityManager.createNativeQuery(sqlBuilder.toString());

        Float closeAmount;
        try {
            // Check if the storeclose already exists in the database
            result = query.getSingleResult();
            System.out.println("$ $ $ $ $ $ $ $ $  result " + result);

            if (result == null) {
                closeAmount = 0F;
            } else {
                closeAmount = Float.parseFloat(result.toString());
            }

        } catch (NoResultException e) {
            // The storeclose doesnt exist so we create it
            closeAmount = 0F;
        }

        storeClose.setCloseAmount(closeAmount);
        storeClose.setCloseDate(closeDate);  //setClosedate(closeDate);

        return (StoreClose) saveEntity(storeClose);
    }

    public StoreClose saveStoreClose(WsRequest request) throws GeneralException, NullParameterException {

        return (StoreClose) saveEntity(request, logger, getMethodName());

    }

//    public void saveStoreClose(WsRequest request) {
//        StoreClose storeClose = null;
//        Store store = null;
//        StoreData storeData = new StoreData();
//        
//        request.setParam(storeId);
//        
//        
//            store = storeData.loadStore(request);
//        
//
//        StringBuilder sqlBuilder = new StringBuilder("SELECT t FROM StoreClose t WHERE t.store.id = " + storeId );// WHERE t.closedate = ?1 AND t.store.id = ?2");
//        Query query = entityManager.createQuery(sqlBuilder.toString());     
//        Object result = null;
//
//        try {
//            // Check if the storeclose already exists in the database
//            result = query.getSingleResult();
//            storeClose = (StoreClose) result;     
//
//        } catch (NoResultException e) {
//            // The storeclose doesnt exist so we create it
//            storeClose = new StoreClose();
//            storeClose.setCloseamount(0F);  //setCloseamount(0);
//            storeClose.setClosedate(closeDate);  //setClosedate(closeDate);
//            storeClose.setStore(store); //setStoreid(storeId);
//       } catch(NullPointerException e){
//                       System.out.println("The requested Store doesn't exist in Database");
//
//       }finally{}
//                    
//        /*
//        TODO:
//        1. LISTO ver porque al guardar la fecha de timestamp, cuando se persiste, se guarda en 0000000 y no sirve
//        2. LISTO corregir el query que busca el store porque el  SELECT busca todos y debe filtrarse por id de tienda o algo asi
//        o convertirlo a jpa nativo por medio de un comando
//        */ 
//        
//        String queryStr =   "    SELECT sum(totalAmount)\n" + 
//                            "      FROM remettences.remittance a\n" +
//                            "INNER JOIN remettences.remittance_status_has_remittence b\n" +
//                            "        ON a.id = b.remittenceStatusId\n" +
//                            "     WHERE b.endingDate IS NULL\n" + 
//                            "       AND b.remittenceStatusId not in (5,6,7)\n" +
//                            "       AND (a.storeId = " + storeId + ");";
//            
//
//        sqlBuilder = new StringBuilder(queryStr);
//        query = entityManager.createNativeQuery(sqlBuilder.toString());
//              
//        Float closeAmount;
//        try {
//            // Check if the storeclose already exists in the database
//            result = query.getSingleResult();
//            System.out.println("$$$$$$$$$$ result " + result);
//
//            closeAmount = Float.parseFloat(result.toString()) ;
//
//        } catch (NoResultException e) {
//            // The storeclose doesnt exist so we create it
//            closeAmount = 0F;
//        }
//        
//        storeClose.setCloseamount(closeAmount);
//        storeClose.setClosedate(closeDate);  //setClosedate(closeDate);
//
//        return (StoreClose) saveEntity(storeClose);     }
//    public List<StoreClose> loadStoreClose(WsRequest request) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public List<StoreClose> loadStoreClose(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException, EmptyListException {
        List<StoreClose> storeCloseList = null;
        try {
            storeCloseList = (List<StoreClose>) listEntities(StoreClose.class, request, logger, getMethodName());
        } catch (Exception e) {
            System.out.println("No StoreClose found");
        }

        // System.out.println("% % % % RESULT % % % " + noWorkingDaysList.toString() );
        return storeCloseList;
    }

}
