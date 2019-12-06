package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.City;
import com.portal.business.commons.models.Country;
import com.portal.business.commons.models.Receiver;
import com.portal.business.commons.models.RemittentHasReceiver;
import com.portal.business.commons.models.State;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 * @author frank
 */
public class ReceiverData extends AbstractBusinessPortalWs{
   private static final Logger logger = Logger.getLogger(PersonData.class);
 
   public ReceiverData(){}
    
   public List<Receiver> selectListReceiver(){
        	
        Query q = entityManager.createQuery("SELECT a FROM Receiver a");
	List lista = q.getResultList();
        Receiver Obj = null;
        
        try{
       		     for(int i = 0; i<lista.size(); i++){
			    Obj = (Receiver)lista.get(i);
			    System.out.println("Id: "+Obj.getId()+"\n");
			    System.out.println("IdPerson: "+ Obj.getPerson().getFirstName() +"\n");
                        }
            
        }catch(Exception e){
        
            e.getMessage();
        
        }finally{
        
            entityManager.close();
	    emf.close();
  
        }
        
        return lista;
    }
   
    public RemittentHasReceiver loadReceiverByRemittent1(int id){
        
	Query q = entityManager.createQuery("SELECT re FROM RemittentHasReceiver re  WHERE re.id = ?1");
        q.setParameter(1, id);
	List lista = q.getResultList();
        RemittentHasReceiver Obj = null;
        
        try{
		for(int i = 0; i<lista.size(); i++){
                    
			Obj = (RemittentHasReceiver)lista.get(i);
			System.out.println(Obj.getId());
		}

       }catch(Exception e){
		
		System.out.println(e.getMessage());
		
		}finally{
	
		entityManager.close();
		emf.close();
	}
        
      return Obj;
	
    }
    
    public List<Receiver> loadReceiverByRemittent(Long remittentId) throws EmptyListException, GeneralException, NullParameterException {

        List<Receiver> receivers = null;
        
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT re.receiver FROM RemittentHasReceiver re where re.remittent.id=?1");
            query.setParameter(1, remittentId);
            receivers = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException e) {
            throw new EmptyListException(e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return receivers;
    }
    
   
    public List<Receiver> getReceivers(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Receiver>) listEntities(Receiver.class, request, logger, getMethodName());
    }
    
    public Receiver loadReceiverByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

        List<Receiver> receivers = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_EMAIL)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_EMAIL), null);
        }
        try {
            receivers = (List<Receiver>) getNamedQueryResult(Receiver.class, QueryConstants.LOAD_RECEIVER_BY_EMAIL, request, getMethodName(), logger, "Receiver");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Receiver"), null);
        }

        return receivers.get(0);
    }
    
        public Receiver loadReceiverByPhoneNumber(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Receiver> receivers = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_PHONE_NUMBER)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_PHONE_NUMBER), null);
        }
        try {
            receivers = (List<Receiver>) getNamedQueryResult(Receiver.class, QueryConstants.LOAD_RECEIVER_BY_PHONE_NUMBER, request, getMethodName(), logger, "Receiver");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Receiver"), null);
        }
        return receivers.get(0);
    }
     
     public List<Country> loadCountry() throws EmptyListException, GeneralException, NullParameterException {

        List<Country> country = null;
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT re.name FROM Country re");
        //    query.setParameter(1, remittentId);
            country = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException e) {
            throw new EmptyListException(e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return country;
    }
     
    
     public List<State> loadStateByCountry(Long idCountry) throws EmptyListException, GeneralException, NullParameterException {

        List<State> state = null;
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT st.name FROM State st WHERE st.country.id = ?1");
            query.setParameter(1, idCountry);
            state = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException e) {
            throw new EmptyListException(e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return state;
    } 
     
    
       public List<City> loadCityByState(Long idCity) throws EmptyListException, GeneralException, NullParameterException {

        List<City> City = null;
        try {
          //Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT ci.name FROM City ci WHERE ci.state.id = ?1");
            query.setParameter(1, idCity);
            City = query.setHint("toplink.refresh", "true").getResultList();
        } catch (NoResultException e) {
            throw new EmptyListException(e.getMessage());
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return City;
    } 
     
/*-----------------------------------------------------------------------------------------------*/
  
    public Receiver saveReceiver(Receiver receiver) throws NullParameterException, GeneralException {
        
        if (receiver == null) {
            
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "receiver"), null);
        
        }
        
        return (Receiver) saveEntity(receiver);
    
    }
    
    public Receiver saveReceiver(WsRequest request) throws GeneralException, NullParameterException {
        
        return (Receiver) saveEntity(request, logger, getMethodName());
    
    }

	public Receiver loadReceiver(WsRequest request) throws NullParameterException, GeneralException, RegisterNotFoundException {
		return (Receiver) this.loadEntity(Receiver.class, request.getParam(), logger, "loadReceiver");
	}
   
}
