package com.portal.business.commons.data;

import com.portal.business.commons.exceptions.EmptyListException;
import com.portal.business.commons.exceptions.GeneralException;
import com.portal.business.commons.exceptions.NullParameterException;
import com.portal.business.commons.exceptions.RegisterNotFoundException;
import com.portal.business.commons.generic.AbstractBusinessPortalWs;
import com.portal.business.commons.generic.WsRequest;
import com.portal.business.commons.models.Account;
import com.portal.business.commons.models.Bank;
import com.portal.business.commons.models.City;
import com.portal.business.commons.models.Correspondent;
import com.portal.business.commons.models.Country;
import com.portal.business.commons.models.CountryTranslation;
import com.portal.business.commons.models.County;
import com.portal.business.commons.models.Currency;
import com.portal.business.commons.models.DeliveryForm;
import com.portal.business.commons.models.Document;
import com.portal.business.commons.models.Enterprise;
import com.portal.business.commons.models.ExchangeRate;
import com.portal.business.commons.models.IpAddress;
import com.portal.business.commons.models.IpBlackList;
import com.portal.business.commons.models.Language;
import com.portal.business.commons.models.PaymentMethod;
import com.portal.business.commons.models.PaymentNetwork;
import com.portal.business.commons.models.PaymentNetworkPoint;
import com.portal.business.commons.models.PaymentNetworkType;
import com.portal.business.commons.models.Period;
import com.portal.business.commons.models.PeriodAnulment;
import com.portal.business.commons.models.RatePaymentNetwork;
import com.portal.business.commons.models.SMS;
import com.portal.business.commons.models.SaleType;
import com.portal.business.commons.models.State;
import com.portal.business.commons.models.Store;
import com.portal.business.commons.models.TinType;
import com.portal.business.commons.utils.EjbConstants;
import com.portal.business.commons.utils.QueryConstants;
import com.portal.business.commons.utils.EjbUtils;
import com.portal.business.commons.utils.Mail;
import com.portal.business.commons.utils.SendMail;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author usuario
 */
public class UtilsData  extends AbstractBusinessPortalWs {
  
    private static final String dCase = "abcdefghijklmnopqrstuvwxyz";
    private static final String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String intChar = "0123456789";
    private static Random r = new Random();
    private static String pass = "";
    private static final Logger logger = Logger.getLogger(UtilsData.class);


    public List<City> getCitiesByCounty(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<City> cities = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(QueryConstants.PARAM_COUNTY_ID)) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_COUNTY_ID), null);
        }
        cities = (List<City>) getNamedQueryResult(UtilsData.class, QueryConstants.CITIES_BY_COUNTY, request, getMethodName(), logger, "cities");
        return cities;
    }

    public List<City> getCitiesByState(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<City> cities = null;
        Map<String, Object> params = request.getParams();
        if (!params.containsKey(QueryConstants.PARAM_STATE_ID)) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "id"), null);
        }

        cities = (List<City>) getNamedQueryResult(UtilsData.class, QueryConstants.CITIES_BY_STATE, request, getMethodName(), logger, "cities");

        return cities;
    }
    
    public List<City> getCity(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<City> cities = (List<City>) listEntities(City.class, request, logger, getMethodName());
        return cities;
    }

    public List<County> getCountiesByState(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<County> counties = null;
        Map<String, Object> params = request.getParams();

        if (!params.containsKey(QueryConstants.PARAM_STATE_ID)) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_STATE_ID), null);
        }
        counties = (List<County>) getNamedQueryResult(UtilsData.class, QueryConstants.COUNTIES_BY_STATE, request, getMethodName(), logger, "counties");
        return counties;
    }

    public List<Country> getCountries(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<Country> countries = (List<Country>) listEntities(Country.class, request, logger, getMethodName());

        return countries;
    }

     public List<Country> getCountries() throws EmptyListException, GeneralException, NullParameterException {
        List<Country> countries = null;
        Query query = null;
        try {
            query = createQuery("SELECT c FROM Country c ORDER BY c.name");
            countries = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (countries.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return countries;
    }

    public List<Currency> getCurrencies() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<Currency> currencies = (List<Currency>) listEntities(Currency.class, request, logger, getMethodName());

        return currencies;
    }

    public List<Enterprise> getEnterprises() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<Enterprise> enterprises = (List<Enterprise>) listEntities(Enterprise.class, request, logger, getMethodName());

        return enterprises;
    }
    
    
    public List<Correspondent> getCorrespondentList() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<Correspondent> correspondentList = (List<Correspondent>) listEntities(Correspondent.class, request, logger, getMethodName());

        return correspondentList;
    }

    public List<Language> getLanguages() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<Language> languages = (List<Language>) listEntities(Language.class, request, logger, getMethodName());
        return languages;
    }

    
    public List<State> getStateByCountry(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        
        List<State> states = null;
        Map<String, Object> params = request.getParams();
       
        if (!params.containsKey(QueryConstants.PARAM_COUNTRY_ID)) {
            
            System.out.println("Error en el metodo getStateByCountry(WsRequest request)");
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_COUNTRY_ID), null);
            
        }
        
        states = (List<State>) getNamedQueryResult(UtilsData.class, QueryConstants.STATES_BY_COUNTRY, request, getMethodName(), logger, "states");
        return states;
    }
    
    public List<State> getState(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
         List<State> states = (List<State>) listEntities(State.class, request, logger, getMethodName());
        return states;
    }

    public List<TinType> getTinTypes() throws EmptyListException, GeneralException, NullParameterException {
        WsRequest request = new WsRequest();
        List<TinType> tinTypes = (List<TinType>) listEntities(TinType.class, request, logger, getMethodName());

        return tinTypes;
    }

    public List<TinType> getTinTypesByEnterprise(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        List<TinType> tinTypes = null;
        if (!request.getParams().containsKey(QueryConstants.PARAM_ENTERPRISE_ID)) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), QueryConstants.PARAM_ENTERPRISE_ID), null);
        }
        tinTypes = (List<TinType>) getNamedQueryResult(UtilsData.class, QueryConstants.TIN_TYPES_BY_ENTERPRISE, request, getMethodName(), logger, "tinTypes");
        return tinTypes;
    }

    public City loadCity(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        City city = (City) loadEntity(City.class, request, logger, getMethodName());
        return city;
    }

    public List<City> loadCityList(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<City>) listEntities(City.class, request, logger, getMethodName());
    }
    
    public Country loadCountry(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Country country = (Country) loadEntity(Country.class, request, logger, getMethodName());
        return country;
    }

    public County loadCounty(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        County county = (County) loadEntity(County.class, request, logger, getMethodName());
        return county;
    }

    public Currency loadCurrency(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Currency currency = (Currency) loadEntity(Currency.class, request, logger, getMethodName());
        return currency;
    }

    public Enterprise loadEnterprise(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Enterprise enterprise = (Enterprise) loadEntity(Enterprise.class, request, logger, getMethodName());

        return enterprise;
    }
    
    public Correspondent loadCorrespondent(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Correspondent correspondent = (Correspondent) loadEntity(Correspondent.class, request, logger, getMethodName());

        return correspondent;
    }
    
    public PaymentMethod loadPaymentMethod(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        PaymentMethod paymentMethod = (PaymentMethod) loadEntity(PaymentMethod.class, request, logger, getMethodName());

        return paymentMethod;
    }
    
    
    public List<PaymentMethod> loadPaymentMethods(WsRequest request) throws EmptyListException, GeneralException, NullParameterException {
        //PaymentMethod paymentMethod = (PaymentMethod) loadEntity(PaymentMethod.class, request, logger, getMethodName());

        return (List<PaymentMethod>) listEntities(PaymentMethod.class, request, logger, getMethodName());
    }

    public List<Language> getLanguages(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Language>) listEntities(Language.class, request, logger, getMethodName());
    }
    
    public Language loadLanguage(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Language language = (Language) loadEntity(Language.class, request, logger, getMethodName());
        return language;
    }

    public State loadState(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        State state = (State) loadEntity(State.class, request, logger, getMethodName());

        return state;
    }
    
    public List<State> loadStateList(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<State>) listEntities(State.class, request, logger, getMethodName());
    }
    
     public List<County> loadCountyList(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<County>) listEntities(County.class, request, logger, getMethodName());
    }
     
       public List<Country> loadCountryList(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
            return (List<Country>) listEntities(Country.class, request, logger, getMethodName());
    }


    public Enterprise saveEnterprise(WsRequest request) throws NullParameterException, GeneralException {
        return (Enterprise) saveEntity(request, logger, getMethodName());
    }


    public SMS saveSMS(WsRequest request) throws NullParameterException, GeneralException {
        return (SMS) saveEntity(request, logger, getMethodName());
    }
    
    public List<SMS> searchSMS(Date beginningDate, Date endingDate, Account account) throws GeneralException, NullParameterException, EmptyListException {
        List<SMS> list = new ArrayList<SMS>();
        if (beginningDate == null) {
            throw new NullParameterException("parameter beginningDate cannot be null");
        }else if (endingDate == null) {
            throw new NullParameterException("parameter endingDate cannot be null");
        }

        StringBuilder sqlBuilder = new StringBuilder("SELECT s FROM SMS s WHERE s.creationDate BETWEEN ?1 AND ?2 ");

        if(account != null){
            sqlBuilder.append(" AND s.account.id=").append(account.getId());
        }

        Query query = null;
        try {
            query = createQuery(sqlBuilder.toString());
            query.setParameter("1", EjbUtils.getBeginningDate(beginningDate));
            query.setParameter("2", EjbUtils.getEndingDate(endingDate));
            list = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (list.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return list;
    }


    public Country loadCountryByName(String name) throws RegisterNotFoundException, NullParameterException, GeneralException {
        List<Country> list = new ArrayList<Country>();
        Country country = new Country();
        try {
            if (name == null) {
                throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "name"), null);
            }
            StringBuilder sqlBuilder = new StringBuilder("SELECT DISTINCT ct.country FROM CountryTranslation ct ");
            sqlBuilder.append("WHERE ct.alias LIKE '%").append(name).append("'")//Problema con el caso FRANCE y GUYANA FRANCESA
                    .append(" OR ct.country.alternativeName1 LIKE '%").append(name).append("'").append(" OR ct.country.alternativeName2 LIKE '%").append(name).append("'").append(" OR ct.country.alternativeName3 LIKE '%").append(name).append("'");
            //  country = (Country) createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getSingleResult();
            list = createQuery(sqlBuilder.toString()).setHint("toplink.refresh", "true").getResultList();
            if (list.isEmpty()) {
                System.out.println("name: " + name);
                throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, Country.class.getSimpleName(), "loadCountryByName", Country.class.getSimpleName(), null), null);
            }else{
                country = list.get(0);
            }
        } catch (RegisterNotFoundException ex) {
            System.out.println("name: " + name);
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, Country.class.getSimpleName(), "loadCountryByName", Country.class.getSimpleName(), null), ex);
        } catch (Exception ex) {
            System.out.println("name: " + name);
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }

        return country;
    }

//    
//    public Country saveCountry(Country country) throws NullParameterException, GeneralException {
//        if (country == null) {
//            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "country"), null);
//        }
//        return (Country) saveEntity(country);
//    }

    public CountryTranslation saveCountryTranslation(CountryTranslation countryTranslation) throws NullParameterException, GeneralException{
     if (countryTranslation == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "countryTranslation"), null);
        }
        return (CountryTranslation) saveEntity(countryTranslation);
    }

        public List<CountryTranslation> getCountryTranslationByCountryIdAndLanguageId(Long countryId, Long languageId) throws EmptyListException, NullParameterException, GeneralException{
    List<CountryTranslation> countryTranslations = new ArrayList<CountryTranslation>();
        try {
            if (countryId == null) {
                throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "countryId"), null);
            }else if (languageId == null) {
                throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "languageId"), null);
            }
            Query query = createQuery("SELECT ct FROM CountryTranslation ct WHERE ct.country.id =?1 AND ct.language.id = ?2");
            query.setParameter("1", countryId);
            query.setParameter("2", languageId);
            countryTranslations = (ArrayList<CountryTranslation>) query.getResultList();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        if (countryTranslations.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return countryTranslations;
    }

    public List<CountryTranslation> getCountryTranslationByCountryId(Long countryId) throws EmptyListException, NullParameterException, GeneralException{
List<CountryTranslation> countryTranslations = new ArrayList<CountryTranslation>();
        try {
            if (countryId == null) {
                throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "countryId"), null);
            }
            Query query = createQuery("SELECT ct FROM CountryTranslation ct WHERE ct.country.id =?1");
            query.setParameter("1", countryId);
            countryTranslations = (List<CountryTranslation>) query.getResultList();
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        if (countryTranslations.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return countryTranslations;
    }

    public Country loadCountryByShortName(String referenceCode)  throws RegisterNotFoundException, NullParameterException, GeneralException
    {
        Country country = new Country();
        try
        {
            if (referenceCode == null)
            {
                throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "referenceCode"), null);
            }

            Query query = createQuery("SELECT c FROM Country c WHERE c.shortName = ?1");
            query.setParameter("1", referenceCode);            
            country = (Country) query.setHint("toplink.refresh", "true").getSingleResult();
        }
        catch (NoResultException ex)
        {
            System.out.println("shortName: "+referenceCode);
            throw new RegisterNotFoundException(logger, sysError.format(EjbConstants.ERR_REGISTER_NOT_FOUND_EXCEPTION, Country.class.getSimpleName(), "loadCountryByShortName", Country.class.getSimpleName(), null), ex);
        } 
        catch (Exception ex)
        {
            System.out.println("shortName: "+referenceCode);
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), ex);
        }
        return country;
    }
    
     public boolean isIpAddresInBlackList(String ipAddress) throws NullParameterException, GeneralException {
         if (ipAddress == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ipAddress"), null);
        }
        List<IpBlackList> list = null;
        try {
            Query query =  createQuery("SELECT b FROM IpBlackList b WHERE b.ipAddress=?1");
            query.setParameter("1", ipAddress);
            list = (List<IpBlackList>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (list.isEmpty()) {
           return false;
        }
        return true;
    }

    public IpBlackList saveIpBlackList(IpBlackList ipBlackList) throws NullParameterException, GeneralException {
        if (ipBlackList == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "saveIpBlackList"), null);
        }
        return (IpBlackList) saveEntity(ipBlackList);
    }

    public IpAddress loadIpddress(String ipAddress) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (ipAddress == null)
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "loadIpddress"), null);
        List<IpAddress> list = null;

        try {
            Query query = createQuery("SELECT i FROM IpAddress i WHERE i.ipAddress=?1");
            query.setParameter("1", ipAddress);
            list = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            logger.error("Exception in method loadIpddress: Exception text: ", e);
            throw new GeneralException("Exception in method loadIpddress: Exception text: " + e.getMessage(), e.getStackTrace());
        }
        if (list.isEmpty()) {
            throw new RegisterNotFoundException("Not IpAddress found in method loadIpddress");
        }
        return list.get(0);
    }

     public List<IpBlackList> getBlackList() throws GeneralException, EmptyListException {
        List<IpBlackList> ipBlackList = new ArrayList<IpBlackList>();
        try {
            Query query = createQuery("SELECT i FROM IpBlackList i");
            ipBlackList = (List<IpBlackList>) query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
        if (ipBlackList.isEmpty()) {
            throw new EmptyListException(logger, sysError.format(EjbConstants.ERR_EMPTY_LIST_EXCEPTION, this.getClass(), getMethodName()), null);
        }
        return ipBlackList;
    }

     public IpBlackList loadBlackList(String ipBlackList) throws RegisterNotFoundException, NullParameterException, GeneralException {
        if (ipBlackList == null)
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "loadIpddress"), null);
        List<IpBlackList> list = null;

        try {
            Query query = createQuery("SELECT i FROM IpBlackList i WHERE i.ipAddress=?1");
            query.setParameter("1", ipBlackList);
            list = query.setHint("toplink.refresh", "true").getResultList();
        } catch (Exception e) {
            logger.error("Exception in method loadBlackList: Exception text: ", e);
            throw new GeneralException("Exception in method loadBlackList: Exception text: " + e.getMessage(), e.getStackTrace());
        }
        if (list.isEmpty()) {
            throw new RegisterNotFoundException("Not ipBlackList found in method loadBlackList");
        }
        return list.get(0);
    }


     public void deleteIpBlackList(String ipBlackList) throws NullParameterException, GeneralException {
        if (ipBlackList == null) {
            throw new NullParameterException(sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "enterpriseId"), null);
        }
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Query query = createQuery("DELETE FROM IpBlackList i WHERE i.ipAddress=?1");
            query.setParameter("1", ipBlackList);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), e.getMessage()), null);
        }
    }

     /*-------------------------------------------*/
     
         public State saveState(State state) throws NullParameterException, GeneralException {
        if (state == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "state"), null);
        }
        return (State) saveEntity(state);
    }
    
      public City saveCity(City city) throws NullParameterException, GeneralException {
        if (city == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "city"), null);
        }
        return (City) saveEntity(city);
    }
    
      public County saveCounty(County county) throws NullParameterException, GeneralException {
        if (county == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "county"), null);
        }
        return (County) saveEntity(county);
    }
      
      public Country saveCountry(Country country) throws NullParameterException, GeneralException {
        if (country == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "country"), null);
        }
        return (Country) saveEntity(country);
    } 
        
 
      
      public Bank saveBank(Bank bank) throws NullParameterException, GeneralException {
        if (bank == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "bank"), null);
        }
        return (Bank) saveEntity(bank);
    }
      
    public Bank loadBank(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Bank bank = (Bank) loadEntity(Bank.class, request, logger, getMethodName());
        return bank;
    }
            
    public Correspondent saveCorrespondent(Correspondent correspondent) throws NullParameterException, GeneralException {
        if (correspondent == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "correspondent"), null);
        }
        return (Correspondent) saveEntity(correspondent);
    }
      
    public String generatedKey(int length) {
        while (pass.length() != 16) {
            int rPick = r.nextInt(4);
            if (rPick == 0) {
                int spot = r.nextInt(25);
                pass += dCase.charAt(spot);
            } else if (rPick == 1) {
                int spot = r.nextInt(25);
                pass += uCase.charAt(spot);
            } else if (rPick == 3) {
                int spot = r.nextInt(9);
                pass += intChar.charAt(spot);
            }
        }
        System.out.println("Generated Pass: " + pass);
        return pass;
    }
    
     public void sendMail(Mail mail) throws GeneralException, NullParameterException {
        SendMail SendMail = new SendMail();
        try {
            SendMail.sendMail(mail);
        } catch (Exception ex) {
            throw new GeneralException(logger, sysError.format(EjbConstants.ERR_GENERAL_EXCEPTION, this.getClass(), getMethodName(), ex.getMessage()), null);
        }
    }
    
     public PaymentNetwork savePaymentNetwork(PaymentNetwork paymentNetwork) throws NullParameterException, GeneralException {
        if (paymentNetwork == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetwork"), null);
        }
        return (PaymentNetwork) saveEntity(paymentNetwork);
    } 
     
    public PaymentNetworkPoint savePaymentNetworkPoint(PaymentNetworkPoint paymentNetworkPoint) throws NullParameterException, GeneralException {
        if (paymentNetworkPoint == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetworkPoint"), null);
        }
        return (PaymentNetworkPoint) saveEntity(paymentNetworkPoint);
    } 
     
     public RatePaymentNetwork saveRatePaymentNetwork(RatePaymentNetwork ratePaymentNetwork) throws NullParameterException, GeneralException {
        if (ratePaymentNetwork == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "ratePaymentNetwork"), null);
        }
        return (RatePaymentNetwork) saveEntity(ratePaymentNetwork);
    } 
     
     public PeriodAnulment savePeriodAnulment(PeriodAnulment periodAnulment) throws NullParameterException, GeneralException {
        if (periodAnulment == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "periodAnulment"), null);
        }
        return (PeriodAnulment) saveEntity(periodAnulment);
    } 
     
      public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws NullParameterException, GeneralException {
        if (exchangeRate == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "exchangeRate"), null);
        }
        return (ExchangeRate) saveEntity(exchangeRate);
    }
     
     public PaymentNetworkType savePaymentNetworkType(PaymentNetworkType paymentNetworkType) throws NullParameterException, GeneralException {
        if (paymentNetworkType == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "paymentNetworkType"), null);
        }
        return (PaymentNetworkType) saveEntity(paymentNetworkType);
    } 
    
      
    public DeliveryForm saveDeliveryForm(DeliveryForm deliveryForm) throws NullParameterException, GeneralException {
        if (deliveryForm == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "deliveryForm"), null);
        }
        return (DeliveryForm) saveEntity(deliveryForm);
    }
          
    public List<Period> getPeriods(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<Period>) listEntities(Period.class, request, logger, getMethodName());
    }
    
    public Period loadPeriod(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Period period = (Period) loadEntity(Period.class, request, logger, getMethodName());
        return period;
    }
    
    public Document saveDocument(Document document) throws NullParameterException, GeneralException {
        if (document == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "document"), null);
        }
        return (Document) saveEntity(document);
    }
      
    public Document loadDocument(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        Document document = (Document) loadEntity(Document.class, request, logger, getMethodName());
        return document;
    }
  
       public Store saveStore(Store store) throws NullParameterException, GeneralException {
        if (store == null) {
            throw new NullParameterException(logger, sysError.format(EjbConstants.ERR_NULL_PARAMETER, this.getClass(), getMethodName(), "store"), null);
        }
        return (Store) saveEntity(store);
    }   
       
    public SaleType loadSaleType(WsRequest request) throws RegisterNotFoundException, NullParameterException, GeneralException {
        SaleType saleType = (SaleType) loadEntity(SaleType.class, request, logger, getMethodName());
        return saleType;
    }   
    
    public List<SaleType> getSaleTypes(WsRequest request) throws GeneralException, EmptyListException, NullParameterException {
        return (List<SaleType>) listEntities(SaleType.class, request, logger, getMethodName());
    }
       
}
