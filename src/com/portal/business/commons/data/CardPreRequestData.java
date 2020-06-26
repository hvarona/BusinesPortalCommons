package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.models.CardPreRequest;
import javax.persistence.EntityTransaction;
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
}
