package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;

import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Address;
import com.portal.business.commons.models.Person;
import com.portal.business.commons.models.Receiver;
import com.portal.business.commons.models.Remittent;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author usuario
 */
public class PersonData extends AbstractBusinessPortalWs {


  private static final Logger logger = Logger.getLogger(PersonData.class);



  public List<Remittent> getRemittent(WsRequest request) throws GeneralException, NullParameterException, EmptyListException {
        return (List<Remittent>) listEntities(Remittent.class, request, logger, getMethodName());
  }

    public List<Remittent> getRemittentsByConditions(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        Boolean isFilter = (Boolean) request.getParam();
        if (isFilter == null || isFilter.equals("null")) {
            isFilter = false;
        }
        Map orderField = new HashMap();
        orderField.put("id", QueryConstants.ORDER_DESC);
        return (List<Remittent>) createSearchQuery(Remittent.class, request, orderField, logger, getMethodName(), "remittents", isFilter);
    }

    public Remittent loadRemittent(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (Remittent) loadEntity(Remittent.class, request, logger, getMethodName());
    }

    public Receiver loadReceiverByRemittent(Long remittentId) throws RegisterNotFoundException, GeneralException, NullParameterException {

        Receiver receiver = null;
        try {
//            Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT person FROM Person p,Receiver r,RemittenceHasReceiver re where re.remittent.id=?1 and re.receiver.id=r.id");
            query.setParameter("1", remittentId);
            receiver = (Receiver) query.setHint("toplink.refresh", "true").getSingleResult();
        } catch (NoResultException e) {
            throw new RegisterNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return receiver;

    }
    
     public Remittent loadRemittentByStore(Long storeId) throws RegisterNotFoundException, GeneralException, NullParameterException {

        Remittent remittent = null;
        try {
//            Query query = createQuery("SELECT pinFree.pin.customer FROM PinFree pinFree WHERE pinFree.ani =?1");
            Query query = createQuery("SELECT person FROM Person p,Remittent r,RemittenceHasReceiver re where re.remittent.id=?1 and re.receiver.id=r.id");
            query.setParameter("1", storeId);
            remittent = (Remittent) query.setHint("toplink.refresh", "true").getSingleResult();
        } catch (NoResultException e) {
            throw new RegisterNotFoundException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        return remittent;

    }

    public Receiver loadReceiverByByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

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
    
    public Remittent loadRemittenceByByEmail(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {

        List<Remittent> remittents = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_EMAIL)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_EMAIL), null);
        }
        try {
            remittents = (List<Remittent>) getNamedQueryResult(Receiver.class, QueryConstants.LOAD_REMITTENCE_BY_EMAIL, request, getMethodName(), logger, "Remittent");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Remittent"), null);
        }

        return remittents.get(0);
    }

    public Receiver loadReceiverByPhoneNumber(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Receiver> receivers = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_LOGIN)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_PHONE_NUMBER), null);
        }
        try {
            receivers = (List<Receiver>) getNamedQueryResult(Receiver.class, QueryConstants.LOAD_RECEIVER_BY_PHONE_NUMBER, request, getMethodName(), logger, "Receiver");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Receiver"), null);
        }
        return receivers.get(0);
    }
    
     public Remittent loadRemittentByPhoneNumber(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Remittent> remittents = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_LOGIN)) {
            throw new NullParameterException( sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_PHONE_NUMBER), null);
        }
        try {
            remittents = (List<Remittent>) getNamedQueryResult(Remittent.class, QueryConstants.LOAD_REMITTENT_BY_PHONE_NUMBER, request, getMethodName(), logger, "Remittent");
        } catch (EmptyListException e) {
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName(), "Remittent"), null);
        }
        return remittents.get(0);
    }

    public Remittent saveRemittent(WsRequest request) throws GeneralException, NullParameterException {
        return (Remittent) saveEntity(request, logger, getMethodName());
    }

    public Remittent saveRemittent(Remittent remittent) throws NullParameterException, GeneralException {
        if (remittent == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "remittent"), null);
        }
        return (Remittent) saveEntity(remittent);
    }

     public Receiver saveReceiver(WsRequest request) throws GeneralException, NullParameterException {
        return (Receiver) saveEntity(request, logger, getMethodName());
    }

    public Receiver saveReceiver(Receiver receiver) throws NullParameterException, GeneralException {
        if (receiver == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "receiver"), null);
        }
        return (Receiver) saveEntity(receiver);
    }
    
    
     public Address saveAddress(Address address) throws NullParameterException, GeneralException {
        if (address == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "address"), null);
        }
        return (Address) saveEntity(address);
    }

    public Address loadAddress(WsRequest request) throws GeneralException, RegisterNotFoundException, NullParameterException {
        return (Address) loadEntity(Address.class, request, logger, getMethodName());
    }
     
        public Person savePerson(Person person) throws NullParameterException, GeneralException {
        if (person == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "country"), null);
        }
        return (Person) saveEntity(person);
    }

  
}
