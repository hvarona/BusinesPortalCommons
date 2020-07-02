package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.models.CardPreRequest;
import com.portal.business.commons.models.Permission;
import com.portal.business.commons.utils.EjbConstants;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author henry
 */
public class CardPreRequestData extends AbstractBusinessPortalWs {

    private static final Logger LOG = Logger.getLogger(CardPreRequestData.class);

    public CardPreRequest saveorUpdateCardPreReques(CardPreRequest cardPreRequest) throws NullParameterException, GeneralException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            if (!transaction.isActive()) {
                transaction.begin();
            }
            if (cardPreRequest.getId() != null) {
                entityManagerWrapper.update(cardPreRequest);
            } else {
                entityManagerWrapper.save(cardPreRequest);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception e1) {
                throw new GeneralException("GeneralException saveEntity");
            }
            throw new GeneralException("GeneralException saveEntity");
        }
        return cardPreRequest;
    }

    public CardPreRequest getRequestById(Long requestId) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (requestId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "requestId"), null);
        }
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<CardPreRequest> cq = cb.createQuery(CardPreRequest.class);
            Root<CardPreRequest> from = cq.from(CardPreRequest.class);

            cq.select(from).where(cb.equal(from.get("id"), requestId));

            Query query = entityManager.createQuery(cq);
            query.setHint("toplink.refresh", "true");
            return (CardPreRequest) query.getSingleResult();

        } catch (NoResultException ex) {
            throw new RegisterNotFoundException("No se encontro la solicitud");
        } catch (Exception ex) {
            ex.getMessage();
            throw new GeneralException("No se encontro la solicitud");
        }
    }
}
