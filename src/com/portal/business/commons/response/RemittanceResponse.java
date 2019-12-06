package com.portal.business.commons.response;

import com.portal.business.commons.models.Address;
import com.portal.business.commons.models.Bank;
import com.portal.business.commons.models.Correspondent;
import com.portal.business.commons.models.Country;
import com.portal.business.commons.models.Currency;
import com.portal.business.commons.models.Document;
import com.portal.business.commons.models.ExchangeRate;
import com.portal.business.commons.models.Language;
import com.portal.business.commons.models.PaymentMethod;
import com.portal.business.commons.models.PaymentNetwork;
import com.portal.business.commons.models.PaymentNetworkPoint;
import com.portal.business.commons.models.RatePaymentNetwork;
import com.portal.business.commons.models.Receiver;
import com.portal.business.commons.models.RemittanceHasRemittenceStatus;
import com.portal.business.commons.models.RemittanceStatus;
import com.portal.business.commons.models.Remittent;
import com.portal.business.commons.models.SalePriceHistory;
import com.portal.business.commons.models.SaleType;
import com.portal.business.commons.models.ServiceType;
import com.portal.business.commons.models.Store;
import java.io.Serializable;
import java.sql.Timestamp;

public class RemittanceResponse implements Serializable{

    private static final long serialVersionUID = -1857658340531160550L;

    private Long id;
    private Timestamp applicationDate;
    private String commentary;
    private Float amountOrigin;
    private Float totalAmount;
    private Boolean sendingOptionSMS;
    private Float amountDestiny;
    private Bank bank;
    private String paymentServiceId;
    private String secondaryKey;
    private Float additionalChanges;
    private Timestamp creationDate;
    private Timestamp CreationHour;
    private Long localSales;
    private Long reserveField1;
    private Remittent remittent;
    private Correspondent Correspondent;
    private Address addressReciever;
    private SaleType salesType;
    private Address addressRemittent;
    private ExchangeRate exchangeRate;
    private RatePaymentNetwork ratePaymentNetwork;
    private SalePriceHistory salePriceHistory;
    private Language language;
    private Currency originCurrent;
    private Currency destinyCurrent;
    private Store store;
    private PaymentMethod paymentMethod;
    private ServiceType serviceType;
    private PaymentNetwork paymentNetwork;
    private Document document;
    private PaymentNetworkPoint point; 
    private Country paymentCountry; 
    private Country countryOrigin; 
    private String cashBox; 
    private String cashier; 
    private String remittanceNumber;
    private String paymentKey;
    private String correlative;

    public RemittanceResponse(Long id, Timestamp applicationDate, String commentary, Float amountOrigin, Float totalAmount, Boolean sendingOptionSMS, Float amountDestiny, Bank bank, String paymentServiceId, String secondaryKey, Float additionalChanges, Timestamp creationDate, Timestamp CreationHour, Long localSales, Long reserveField1, Remittent remittent, Correspondent Correspondent, Address addressReciever, SaleType salesType, Address addressRemittent, ExchangeRate exchangeRate, RatePaymentNetwork ratePaymentNetwork, SalePriceHistory salePriceHistory, Language language, Currency originCurrent, Currency destinyCurrent, Store store, PaymentMethod paymentMethod, ServiceType serviceType, PaymentNetwork paymentNetwork, Document document, PaymentNetworkPoint point, Country paymentCountry, Country countryOrigin, String cashBox, String cashier, String remittanceNumber, String paymentKey, String correlative) {
        this.id = id;
        this.applicationDate = applicationDate;
        this.commentary = commentary;
        this.amountOrigin = amountOrigin;
        this.totalAmount = totalAmount;
        this.sendingOptionSMS = sendingOptionSMS;
        this.amountDestiny = amountDestiny;
        this.bank = bank;
        this.paymentServiceId = paymentServiceId;
        this.secondaryKey = secondaryKey;
        this.additionalChanges = additionalChanges;
        this.creationDate = creationDate;
        this.CreationHour = CreationHour;
        this.localSales = localSales;
        this.reserveField1 = reserveField1;
        this.remittent = remittent;
        this.Correspondent = Correspondent;
        this.addressReciever = addressReciever;
        this.salesType = salesType;
        this.addressRemittent = addressRemittent;
        this.exchangeRate = exchangeRate;
        this.ratePaymentNetwork = ratePaymentNetwork;
        this.salePriceHistory = salePriceHistory;
        this.language = language;
        this.originCurrent = originCurrent;
        this.destinyCurrent = destinyCurrent;
        this.store = store;
        this.paymentMethod = paymentMethod;
        this.serviceType = serviceType;
        this.paymentNetwork = paymentNetwork;
        this.document = document;
        this.point = point;
        this.paymentCountry = paymentCountry;
        this.countryOrigin = countryOrigin;
        this.cashBox = cashBox;
        this.cashier = cashier;
        this.remittanceNumber = remittanceNumber;
        this.paymentKey = paymentKey;
        this.correlative = correlative;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Float getAmountOrigin() {
        return amountOrigin;
    }

    public void setAmountOrigin(Float amountOrigin) {
        this.amountOrigin = amountOrigin;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getSendingOptionSMS() {
        return sendingOptionSMS;
    }

    public void setSendingOptionSMS(Boolean sendingOptionSMS) {
        this.sendingOptionSMS = sendingOptionSMS;
    }

    public Float getAmountDestiny() {
        return amountDestiny;
    }

    public void setAmountDestiny(Float amountDestiny) {
        this.amountDestiny = amountDestiny;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getPaymentServiceId() {
        return paymentServiceId;
    }

    public void setPaymentServiceId(String paymentServiceId) {
        this.paymentServiceId = paymentServiceId;
    }

    public String getSecondaryKey() {
        return secondaryKey;
    }

    public void setSecondaryKey(String secondaryKey) {
        this.secondaryKey = secondaryKey;
    }

    public Float getAdditionalChanges() {
        return additionalChanges;
    }

    public void setAdditionalChanges(Float additionalChanges) {
        this.additionalChanges = additionalChanges;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getCreationHour() {
        return CreationHour;
    }

    public void setCreationHour(Timestamp CreationHour) {
        this.CreationHour = CreationHour;
    }

    public Long getLocalSales() {
        return localSales;
    }

    public void setLocalSales(Long localSales) {
        this.localSales = localSales;
    }

    public Long getReserveField1() {
        return reserveField1;
    }

    public void setReserveField1(Long reserveField1) {
        this.reserveField1 = reserveField1;
    }

    public Remittent getRemittent() {
        return remittent;
    }

    public void setRemittent(Remittent remittent) {
        this.remittent = remittent;
    }

    public Correspondent getCorrespondent() {
        return Correspondent;
    }

    public void setCorrespondent(Correspondent Correspondent) {
        this.Correspondent = Correspondent;
    }

    public Address getAddressReciever() {
        return addressReciever;
    }

    public void setAddressReciever(Address addressReciever) {
        this.addressReciever = addressReciever;
    }

    public SaleType getSalesType() {
        return salesType;
    }

    public void setSalesType(SaleType salesType) {
        this.salesType = salesType;
    }

    public Address getAddressRemittent() {
        return addressRemittent;
    }

    public void setAddressRemittent(Address addressRemittent) {
        this.addressRemittent = addressRemittent;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public RatePaymentNetwork getRatePaymentNetwork() {
        return ratePaymentNetwork;
    }

    public void setRatePaymentNetwork(RatePaymentNetwork ratePaymentNetwork) {
        this.ratePaymentNetwork = ratePaymentNetwork;
    }

    public SalePriceHistory getSalePriceHistory() {
        return salePriceHistory;
    }

    public void setSalePriceHistory(SalePriceHistory salePriceHistory) {
        this.salePriceHistory = salePriceHistory;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Currency getOriginCurrent() {
        return originCurrent;
    }

    public void setOriginCurrent(Currency originCurrent) {
        this.originCurrent = originCurrent;
    }

    public Currency getDestinyCurrent() {
        return destinyCurrent;
    }

    public void setDestinyCurrent(Currency destinyCurrent) {
        this.destinyCurrent = destinyCurrent;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public PaymentNetwork getPaymentNetwork() {
        return paymentNetwork;
    }

    public void setPaymentNetwork(PaymentNetwork paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public PaymentNetworkPoint getPoint() {
        return point;
    }

    public void setPoint(PaymentNetworkPoint point) {
        this.point = point;
    }

    public Country getPaymentCountry() {
        return paymentCountry;
    }

    public void setPaymentCountry(Country paymentCountry) {
        this.paymentCountry = paymentCountry;
    }

    public Country getCountryOrigin() {
        return countryOrigin;
    }

    public void setCountryOrigin(Country countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public String getCashBox() {
        return cashBox;
    }

    public void setCashBox(String cashBox) {
        this.cashBox = cashBox;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public String getRemittanceNumber() {
        return remittanceNumber;
    }

    public void setRemittanceNumber(String remittanceNumber) {
        this.remittanceNumber = remittanceNumber;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public String getCorrelative() {
        return correlative;
    }

    public void setCorrelative(String correlative) {
        this.correlative = correlative;
    }
        

    
    
}
