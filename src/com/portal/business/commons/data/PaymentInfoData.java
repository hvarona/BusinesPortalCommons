package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.InvalidCreditCardException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.PaymentInfoDisabledException;
import com.portal.business.commons.exceptions.PaymentInfoNotFoundException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Address;
import com.portal.business.commons.models.PaymentPatner;
import com.portal.business.commons.models.PaymentType;
//import com.remettence.commons.models.TransactionStatus;
//import com.remettence.commons.utils.CreditCardUtils;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.GeneralUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;


/**
 *
 * @author lromero
 */
public class PaymentInfoData extends AbstractBusinessPortalWs {

     private static final Logger logger = Logger.getLogger(PaymentInfoData.class);

//    public PaymentInfo savePaymentInfo(PaymentInfo paymentInfo) throws GeneralException, NullParameterException {
//
//        if (paymentInfo == null) {
//            throw new NullParameterException("Parameter cannot be null in method savePaymentInfo");
//        }
//        try {
//            List<PaymentInfo> paymentInfoList = null;
//            try {
//                paymentInfoList = getPaymentInfosByCustomer(paymentInfo.getUserId(),true);
//            } catch(PaymentInfoNotFoundException pe) {
//            }
//            if(paymentInfoList != null) {
//                for(PaymentInfo paymentInfoAux : paymentInfoList) {
//                    if(paymentInfo.getCreditCardNumber().trim().equals(paymentInfoAux.getCreditCardNumber().trim())) {
//                        paymentInfo.setId(paymentInfoAux.getId());
//                        break;
//                    }
//                }
//            }
//        paymentInfo = (PaymentInfo) saveEntity(paymentInfo);
//        } catch (Exception e) {
////            EjbUtils.rollback(userTransaction, logger);
//            throw new GeneralException("Exception in method savePaymentInfo: " + e.getMessage(), e.getStackTrace());
//        }
//
//        return paymentInfo;
//
//    }
//
//         /**
//     *
//     * @param paymentInfoId
//     * @throws NullParameterException
//     * @throws GeneralException
//     */
//    public void deletePaymentInfo(Long paymentInfoId) throws NullParameterException, GeneralException {
//        if (paymentInfoId == null) {
//            throw new NullParameterException("Parameter cannot be null in method deletePaymentInfo");
//        }
//        try {
//            Query query = entityManager.createQuery("UPDATE PaymentInfo pi SET pi.endingDate=:now WHERE pi.id=" + paymentInfoId);
//            query.setParameter("now", new Date(new java.util.Date().getTime()));
//            executeQuery(query);
//        } catch (Exception e) {
//            throw new GeneralException("Exception in method deletePaymentInfo: " + e.getMessage(), e.getStackTrace());
//        }
//    }
//
//     public List<PaymentInfo> getPaymentInfosByCustomer(Long userId, boolean includeExpirated) throws GeneralException, NullParameterException, PaymentInfoNotFoundException {
//
//        if (userId == null) {
//            throw new NullParameterException("Parameters cannot be null in method getPaymentInfosByCustomer");
//        }
//        List<PaymentInfo> list = null;
//        try {
//            String expirationCondition = " AND (p.endingDate IS NULL OR p.endingDate < CURRENT_DATE)";
//            String sql = "SELECT p FROM PaymentInfo p WHERE p.userId=" + userId;
//            sql += !includeExpirated ? expirationCondition : "";
//            list = entityManager.createQuery(sql).setHint("toplink.refresh", "true").getResultList();
//        } catch (Exception e) {
//            throw new GeneralException("Exception in method getPaymentsInfoByWebUser: " + e.getMessage(), e.getStackTrace());
//        }
//        if (list == null || list.isEmpty()) {
//            throw new PaymentInfoNotFoundException("PaymentInfo not found in method getPaymentsInfoByWebUser");
//        }
//        return list;
//
//    }
//
//     public PaymentInfo getPaymentInfoById(Long paymentInfoId, boolean includeExpirated) throws GeneralException, NullParameterException, PaymentInfoNotFoundException,PaymentInfoDisabledException {
//
//        if (paymentInfoId == null) {
//            throw new NullParameterException("Parameters cannot be null in method getPaymentInfoById");
//        }
//       PaymentInfo paymentInfo = null;
//        try {
//            String sql = "SELECT p FROM PaymentInfo p WHERE p.id=" + paymentInfoId;
//            paymentInfo = (PaymentInfo) entityManager.createQuery(sql).setHint("toplink.refresh", "true").getSingleResult();
//            if (paymentInfo.getEndingDate() != null ){
//                  throw new PaymentInfoDisabledException("PaymentInfo disabled: " + paymentInfoId);
//            }
//        } catch (EntityNotFoundException e) {
//            throw new PaymentInfoNotFoundException("No se encontro un paymentInfo : " + paymentInfoId);
//        } catch (NoResultException e) {
//            throw new PaymentInfoNotFoundException("Exception in method getPaymentInfoById: " + e.getMessage(), e.getStackTrace());
//        } catch (PaymentInfoDisabledException e) {
//            throw new PaymentInfoDisabledException("Exception in method getPaymentInfoById: " + e.getMessage(), e.getStackTrace());
//        } catch (Exception e) {
//             throw new GeneralException("Exception in method getPaymentInfoById: " + e.getMessage(), e.getStackTrace());
//        }
//        return paymentInfo;
//
//    }
//
//    public int numberPaymentInfoByUserId(Long userId) throws NullParameterException, GeneralException {
//        if (userId == null) {
//            throw new NullParameterException("Parameters cannot be null in method numberPaymentInfoByUserId");
//        }
//        int numberPaymentInfo = 0;
//        List<PaymentInfo> paymentInfos = null;
//        try {
//
//            paymentInfos = getPaymentInfosByCustomer(userId, false);
//            numberPaymentInfo = paymentInfos.size();
//        } catch (PaymentInfoNotFoundException e) {
//            numberPaymentInfo = 0;
//        } catch (Exception e) {
//            throw new GeneralException("Exception in method numberPaymentInfoByUserId: " + e.getMessage(), e.getStackTrace());
//        }
//        return numberPaymentInfo;
//    }
//
//
//    public boolean validatePaymentInfo(PaymentInfo info, Long transactionTypeId)throws NullParameterException, GeneralException{
//        if (info == null) {
//            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentInfo"), null);
//        }
//        boolean valid=true;
//        info.setPaymentPatner(entityManager.find(PaymentPatner.class, 1l));
//        Date now = new Date((new java.util.Date()).getTime());
//        Timestamp nowTimestamp = new Timestamp(now.getTime());
//        info.setBeginningDate(nowTimestamp);
//        info.setPaymentType(entityManager.find(PaymentType.class, PaymentType.CREDIT_CARD));
//        EntityTransaction userTransaction = entityManager.getTransaction();
//   //     UserTransaction userTransaction = context.getUserTransaction();
//        Long numberTransaccionCreditCardNumber = 0L;
//        Date nowBeginning = GeneralUtils.getBeginningDate(new Date((new java.util.Date()).getTime()));
//        Date nowEnding = GeneralUtils.getEndingDate(new Date((new java.util.Date()).getTime()));
//        Timestamp nowBeginningDate = new Timestamp(nowBeginning.getTime());
//        Timestamp nowEndingDate = new Timestamp(nowEnding.getTime());
//        try {
//            if (info.getId() == null) {
//                if (!userTransaction.isActive())
//                    userTransaction.begin();
//                entityManager.persist(info);
//                entityManager.flush();
//            }
//            StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(t.id) FROM payment_info p1, transaction t ,payment_info p2" +
//                    " WHERE p1.creditCardNumber=p2.creditCardNumber AND p1.id=t.paymentInfoId AND t.transactionStatusId="+ TransactionStatus.PROCESSED + " AND p2.id="+info.getId() + " AND t.creationDate BETWEEN DATE('" + nowBeginningDate + "') AND ('" + nowEndingDate + "')");
//                if (transactionTypeId != null) {
//                    sqlBuilder.append(" AND t.transactionTypeId=").append(transactionTypeId);
//                }
//             List result = new ArrayList();
//             System.out.println(sqlBuilder.toString());
//
//            result = (List) entityManager.createNativeQuery(sqlBuilder.toString()).getSingleResult();
//            numberTransaccionCreditCardNumber = (Long) result.get(0);
//            if (numberTransaccionCreditCardNumber >= 7) {
//                System.out.println("TIENE MAS DE DOS TRANSACCIONES");
//                valid = false;
//            }
//            if (info.getId() == null) {
//                entityManager.remove(info);
//                userTransaction.commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (userTransaction.isActive()) {
//                userTransaction.rollback();
//            }
//            logger.error("Exception in method validatePaymentInfo: ", e);
//            throw new GeneralException("Exception in method validatePaymentInfo: " + e.getMessage(), e.getStackTrace());
//        }
//        return valid;
//    }
//
//    public boolean paymentInfoIsUnique(PaymentInfo info)throws NullParameterException, GeneralException{
//    	if(info == null) {
//            throw new NullParameterException("Parameter paymentInfo cannot be null in method loadPaymentInfo");
//        }
//    	boolean result = false;
//        PaymentData paymentData = new PaymentData();
//        WsRequest request = new WsRequest();
//        request.setParam(1l);
//        try{
//            info.setPaymentPatner(paymentData.loadPaymentPatner(request));
//        }catch (RegisterNotFoundException e){}
//        java.util.Date date= new java.util.Date();
//        info.setBeginningDate(new Timestamp(date.getTime()));
//        request.setParam(PaymentType.CREDIT_CARD);
//        try{
//            info.setPaymentType(paymentData.loadPaymentType(request));
//        }catch (RegisterNotFoundException e){}
//    	EntityTransaction userTransaction = entityManager.getTransaction();
//    	try {
//                if (!userTransaction.isActive())
//                    userTransaction.begin();
//    		entityManager.persist(info);
//    		entityManager.flush();
//    		String sql = "SELECT p FROM PaymentInfo p, PaymentInfo p2 " +
//    				"WHERE p.creditCardNumber=p2.creditCardNumber AND p.endingDate IS NULL AND p2.id=:infoId";
//    		List<PaymentInfo> infos = entityManager.createQuery(sql).setParameter("infoId", info.getId()).getResultList();
//    		if(infos.size()==0 || (infos.size()==1 && infos.get(0).getUserId()==info.getUserId())){
//    			result=true;
//    		}
//
//    		entityManager.remove(info);
//    		userTransaction.commit();
//
//        } catch (Exception e) {
//            logger.error("Exception in method loadPaymentInfo: ", e);
//            throw new GeneralException("Exception in method loadPaymentInfo: " + e.getMessage(), e.getStackTrace());
//        }
//    	return result;
//    }
//
//    public void validateCreditCard(String creditCardNumber) throws InvalidCreditCardException, GeneralException {
//
//        if(creditCardNumber.length() < 15 || creditCardNumber.length() > 16) throw new InvalidCreditCardException("Invalid credit card length");
//        if(!CreditCardUtils.validate(creditCardNumber)) throw new InvalidCreditCardException("Invalid credit card format");
//
//    }
//
//     public  Address  saveAddress(Address address) throws GeneralException, NullParameterException{
//        try {
//            address = (Address) saveEntity(address);
//        } catch (GeneralException ex) {
//            throw new GeneralException(sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
//        } catch (NullParameterException ex) {
//            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "payment"), null);
//        }
//        return address;
//    }
//
//    public PaymentInfo loadPaymentInfo(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
//        PaymentInfo paymentInfo = (PaymentInfo) loadEntity(PaymentInfo.class, request, logger, getMethodName());
//        return paymentInfo;
//    }

}
