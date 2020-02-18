package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Commerce;
import com.portal.business.commons.models.Pos;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author hvarona
 */
public class PosData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(PosData.class);

    public List<Pos> getPosList(Commerce commerce) throws EmptyListException, GeneralException {

        List<Pos> posList = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Pos> cq = cb.createQuery(Pos.class);
            Root<Pos> from = cq.from(Pos.class);
            Join<Pos, Store> join = from.join(("store"));
            posList = entityManager.createQuery(cq.select(from).where(cb.equal(join.get("commerce"), commerce))).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (posList == null || posList.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return posList;
    }

    public Pos loadPos(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        return (Pos) loadEntity(Pos.class, request, LOG, getMethodName());
    }

    public List<Store> getStoreList(Commerce commerce) throws EmptyListException, GeneralException {

        List<Store> stores = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Store> cq = cb.createQuery(Store.class);
            Root<Store> from = cq.from(Store.class);
            stores = entityManager.createQuery(cq.select(from).where(cb.equal(from.get("commerce"), commerce))).getResultList();
        } catch (Exception e) {
            throw new GeneralException(LOG, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (stores == null || stores.isEmpty()) {
            throw new EmptyListException(LOG, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return stores;
    }

    public Pos savePos(Pos pos) throws NullParameterException, GeneralException {
        if (pos == null) {
            throw new NullParameterException(LOG, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "pos"), null);
        }
        return (Pos) saveEntity(pos);
    }

}