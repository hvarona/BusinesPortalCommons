package com.portal.business.commons.managers;

import com.portal.business.commons.data.RemittanceData;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.RemittanceStatus;
import java.util.List;



@SuppressWarnings("all")
public class ContentManager {

    private static ContentManager instance;
    private List<RemittanceStatus> remittanceStatus;
    private RemittanceData remittanceData;
//    private Map<Long, List<Banner>> bannersByTypeId;
//    private List<BannerType> bannerTypes;
//    private List<AssignmentType> assignmentTypes;
//    private List<RechargeType> rechargeTypes;
//    private List<RechargeStatus> rechargeStatus;
//    private List<AssignmentStatus> assignmentStatus;
//    private List<Banner> banners;
//    private List<TransactionSource> transactionSources;
//    private List<TransactionType> transactionTypes;
//    private List<ClosureProcessStatus> closureProcessStatus;
//    private List<DistributorClosureProcessStatus> distributorClosureProcessStatus;
//    private BannerEJB bannerEJB;
//    private TransactionEJB transactionEJB;

    public static synchronized ContentManager getInstance() throws Exception {
        if (instance == null) {
            instance = new ContentManager();
        }
        return instance;
    }

    public static void refresh() throws Exception {
        instance = new ContentManager();
    }

    private ContentManager() throws Exception {
        remittanceData = new RemittanceData();
        WsRequest request = new WsRequest();
        remittanceStatus = remittanceData.getRemittenceStatus(request);
        
//        banners = new ArrayList<Banner>();
//        bannerTypes = new ArrayList<BannerType>();
//        bannersByTypeId = new HashMap<Long, List<Banner>>();
//        bannerEJB = (BannerEJB) EJBServiceLocator.getInstance().get(EjbConstants.BANNER_EJB);
//        transactionEJB = (TransactionEJB) EJBServiceLocator.getInstance().get(EjbConstants.TRANSACTION_EJB);
//        bannerTypes = bannerEJB.getBannerTypes();
//        for (BannerType bannerType : bannerTypes) {
//            try {
//                banners = bannerEJB.getBannersByType(bannerType.getId());
//                bannersByTypeId.put(bannerType.getId(), banners);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//
//
//
//
//            }
//        }
//        transactionSources = transactionEJB.getTransactionSources(new EJBRequest());
//
//        transactionTypes = transactionEJB.getTransactionTypes(new EJBRequest());
//
//        assignmentTypes = transactionEJB.getAssignmentTypes();
//
//        assignmentStatus = transactionEJB.getAssignmentStatus();
//
//        rechargeTypes = transactionEJB.getRechargeTypes();
//
//        rechargeStatus = transactionEJB.getRechargeStatus();
//
//        closureProcessStatus = transactionEJB.getClosureProcessStatus();
//
//        distributorClosureProcessStatus = transactionEJB.getDistributorClosureProcessStatus();
    }
    
     public RemittanceStatus getRemittanceStatusById(Long remittanceStatusId) throws NullParameterException{
        if(remittanceStatusId == null){
            throw new NullParameterException("Parameter remittanceStatusId cannot be null");
        }
        for(RemittanceStatus status : remittanceStatus){
            if(status.getId().equals(remittanceStatusId)){
                return status;
            }
        }
        return null;
    }

//    public List<Banner> getBannersByTypeId(Long bannerTypeId) throws NullParameterException{
//        if(bannerTypeId == null){
//            throw new NullParameterException("Parameter bannerTypeId cannot be null");
//        }
//        return bannersByTypeId.get(bannerTypeId);
//    }
//
//    public List<TransactionSource> getTransactionSources() throws NullParameterException{
//        return transactionSources;
//    }
//    public TransactionSource getTransactionSourceById(Long transactionSourceId) throws NullParameterException{
//        if(transactionSourceId == null){
//            throw new NullParameterException("Parameter transactionSourceId cannot be null");
//        }
//        for(TransactionSource transactionSource : transactionSources){
//            if(transactionSource.getId().equals(transactionSourceId)){
//                return transactionSource;
//            }
//        }
//        return null;
//    }
//
//    public List<TransactionType> getTransactionTypes() throws NullParameterException{
//        return transactionTypes;
//    }
//
//    public TransactionType getTransactionTypeById(Long transactionTypeId) throws NullParameterException{
//        if(transactionTypeId == null){
//            throw new NullParameterException("Parameter transactionTypeId cannot be null");
//        }
//        for(TransactionType transactionType : transactionTypes){
//            if(transactionType.getId().equals(transactionTypeId)){
//                return transactionType;
//            }
//        }
//        return null;
//    }
//
//     public AssignmentType getAssignmentTypeById(Long assignmentTypeId) throws NullParameterException{
//        if(assignmentTypeId == null){
//            throw new NullParameterException("Parameter assignmentTypeId cannot be null");
//        }
//        for(AssignmentType assignmentType : assignmentTypes){
//            if(assignmentType.getId().equals(assignmentTypeId)){
//                return assignmentType;
//            }
//        }
//        return null;
//    }
//
//    public AssignmentStatus getAssignmentStatusById(String assignmentStatusId) throws NullParameterException{
//        if(assignmentStatusId == null){
//            throw new NullParameterException("Parameter assignmentStatusId cannot be null");
//        }
//        for(AssignmentStatus status : assignmentStatus){
//            if(status.getId().equals(assignmentStatusId)){
//                return status;
//            }
//        }
//        return null;
//    }
//
//    public RechargeStatus getRechargeStatusById(String rechargeStatusId) throws NullParameterException{
//        if(rechargeStatusId == null){
//            throw new NullParameterException("Parameter rechargeStatusId cannot be null");
//        }
//        for(RechargeStatus status : rechargeStatus){
//            if(status.getId().equals(rechargeStatusId)){
//                return status;
//            }
//        }
//        return null;
//    }
//
//    public RechargeType getRechargeTypeById(String rechargeTypeId) throws NullParameterException{
//        if(rechargeTypeId == null){
//            throw new NullParameterException("Parameter rechargeTypeId cannot be null");
//        }
//        for(RechargeType rechargeType : rechargeTypes){
//            if(rechargeType.getId().equals(rechargeTypeId)){
//                return rechargeType;
//            }
//        }
//        return null;
//    }
//
//    public ClosureProcessStatus getClosureProcessStatusById(String closureProcessStatusId) throws NullParameterException{
//        if(closureProcessStatusId == null){
//            throw new NullParameterException("Parameter closureProcessStatusId cannot be null");
//        }
//        for(ClosureProcessStatus status : closureProcessStatus){
//            if(status.getId().equals(closureProcessStatusId)){
//                return status;
//            }
//        }
//        return null;
//    }
//
//     public DistributorClosureProcessStatus getDistributorClosureProcessStatusById(String distributorClosureProcessStatusId) throws NullParameterException{
//        if(distributorClosureProcessStatusId == null){
//            throw new NullParameterException("Parameter distributorClosureProcessStatusId cannot be null");
//        }
//        for(DistributorClosureProcessStatus status : distributorClosureProcessStatus){
//            if(status.getId().equals(distributorClosureProcessStatusId)){
//                return status;
//            }
//        }
//        return null;
//    }


}
