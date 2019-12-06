package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.ExchangeRate;
import com.portal.business.commons.utils.EjbConstants;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author frank
 */
public class ExchangeRateData extends AbstractBusinessPortalWs {
    
    private static final Logger logger = Logger.getLogger(PaymentTypeData.class);

    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws NullParameterException, GeneralException {
        if (exchangeRate == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "exchangeRate"), null);
         }
         return (ExchangeRate) saveEntity(exchangeRate);
    }
    
     
    public ExchangeRate lastExchangeRateByCountryId(Long countryId) throws RegisterNotFoundException, GeneralException, NullParameterException {

        if (countryId == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "countryId"), null);
        }
        List<ExchangeRate> exchangeRate = null;
        ExchangeRate objValidar = null;

        try {

            Query query = createQuery("SELECT b FROM ExchangeRate b WHERE b.beginingDate = (SELECT MAX(a.beginingDate) FROM ExchangeRate a WHERE a.country.id = " + countryId + ") AND b.country.id =" + countryId);
            exchangeRate = query.setHint("toplink.refresh", "true").getResultList();

        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (exchangeRate.isEmpty()) {
            throw new RegisterNotFoundException("ExchangeRate Empty");
        }

        return exchangeRate.get(0);
    }

    public List<ExchangeRate> loadExchangeRate(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<ExchangeRate>) listEntities(ExchangeRate.class, request, logger, getMethodName());
    }
}
