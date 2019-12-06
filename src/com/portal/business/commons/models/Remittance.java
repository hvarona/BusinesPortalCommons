/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.models;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import com.portal.business.commons.utils.QueryConstants;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import java.sql.Timestamp;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author usuario
 */
@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "remittance")
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({
    @NamedQuery(name = QueryConstants.UPDATE_REMITTANCE_NUMBER,
    query = "UPDATE Remittance r SET r.remittanceNumber =:remittanceNumber WHERE r.id=:remittanceId")
})
public class Remittance extends RemittenceEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp applicationDate;
    private String commentary;
    private Float amountOrigin;
    private Float totalAmount;
    private Boolean sendingOptionSMS;
    private Float amountDestiny;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="bankId")
    private Bank bank;
    private String paymentServiceId;
    private String secondaryKey;
    private Float additionalChanges;
    private Timestamp creationDate;
    private Timestamp CreationHour;
    private Long localSales;
    private Long reserveField1;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="remittentId")
    private Remittent remittent;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="receiverId")
    private Receiver receiver;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="correspondentId")
    private Correspondent Correspondent;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="addressRecieverId")
    private Address addressReciever;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="salesTypeId")
    private SaleType salesType;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="addressRemittentId")
    private Address addressRemittent;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="exchangeRateId")
    private ExchangeRate exchangeRate;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="ratePaymentNetworkId")
    private RatePaymentNetwork ratePaymentNetwork;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="salesPriceHistoryId")
    private SalePriceHistory salePriceHistory;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="languageId")
    private Language language;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="originCurrentId")
    private Currency originCurrent;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="destinyCurrentId")
    private Currency destinyCurrent;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="storeId")
    private Store store;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="paymentMethodId")
    private PaymentMethod paymentMethod;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="serviceTypeId")
    private ServiceType serviceType;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="paymentNetworkId")
    private PaymentNetwork paymentNetwork;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="documentId")
    private Document document;
    @XmlTransient
    @OneToMany(mappedBy = "remittance", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<StoreBalanceHistory> balanceHistories;
    @XmlTransient
    @OneToMany(mappedBy = "remittance", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<RemittanceHasRemittenceStatus> remittenceStatus;
    @ManyToOne (cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "pointId") 
    private PaymentNetworkPoint paymentNetworkPoint; 
    private String cashBox; 
    private String cashier; 
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "userId") 
    private User user; 

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="remittanceStatusId")
    private RemittanceStatus status;
    private String remittanceNumber;
    private String paymentKey;
    private String correlative;

    
    
    @Transient
    private RemittanceHasRemittenceStatus currentRemittanceStatus;
    
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="deliveryFormId")
    private DeliveryForm deliveryForm;
        
    public Remittance() {
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

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Correspondent getCorrespondent() {
        return Correspondent;
    }

    public void setCorrespondent(Correspondent Correspondent) {
        this.Correspondent = Correspondent;
    }


    public SaleType getSalesType() {
        return salesType;
    }

    public void setSalesType(SaleType salesType) {
        this.salesType = salesType;
    }

   
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

   

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getPaymentServiceId() {
        return paymentServiceId;
    }

    public void setPaymentServiceId(String paymentServiceId) {
        this.paymentServiceId = paymentServiceId;
    }



    public Address getAddressReciever() {
        return addressReciever;
    }

    public void setAddressReciever(Address addressReciever) {
        this.addressReciever = addressReciever;
    }

    public Address getAddressRemittent() {
        return addressRemittent;
    }

    public void setAddressRemittent(Address addressRemittent) {
        this.addressRemittent = addressRemittent;
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

    public List<RemittanceHasRemittenceStatus> getRemittenceStatus() {
        return remittenceStatus;
    }

    public void setRemittenceStatus(List<RemittanceHasRemittenceStatus> remittenceStatus) {
        this.remittenceStatus = remittenceStatus;
    }

   public PaymentNetworkPoint getPaymentNetworkPoint() { 
        return paymentNetworkPoint; 
    } 

    public void setPaymentNetworkPoint(PaymentNetworkPoint paymentNetworkPoint) { 
        this.paymentNetworkPoint = paymentNetworkPoint; 
    } 

    public String getRemittanceNumber() {
        return remittanceNumber;
    }

    public void setRemittanceNumber(String remittanceNumber) {
        this.remittanceNumber = remittanceNumber;
    }

    public String getCorrelative() {
        return correlative;
    }

    public void setCorrelative(String correlative) {
        this.correlative = correlative;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeliveryForm getDeliveryForm() {
        return deliveryForm;
    }

    public void setDeliveryForm(DeliveryForm deliveryForm) {
        this.deliveryForm = deliveryForm;
    }

    
    @Override 
    public String toString() { 
        return "Remittence{" + "id=" + id + ", applicationDate=" + applicationDate + ", commentary=" + commentary + ", amountOrigin=" + amountOrigin + ", totalAmount=" + totalAmount + ", sendingOptionSMS=" + sendingOptionSMS + ", amountDestiny=" + amountDestiny + ", bank=" + bank + ", paymentService=" + paymentServiceId + ", secondaryKey=" + secondaryKey + ", additionalChanges=" + additionalChanges + ", creationDate=" + creationDate + ", CreationHour=" + CreationHour + ", localSales=" + localSales + ", reserveField1=" + reserveField1 + ", remittent=" + remittent + ", reciever=" + receiver + ", Correspondent=" + Correspondent + ", addressReciever=" + addressReciever + ", salesType=" + salesType + ", addressRemittent=" + addressRemittent + ", exchangeRate=" + exchangeRate + ", ratePaymentNetwork=" + ratePaymentNetwork + ", salesPriceHistory=" + salePriceHistory + ", language=" + language + ", originCurrent=" + originCurrent + ", destinyCurrent=" + destinyCurrent + ", store=" + store + ", paymentMethod=" + paymentMethod + ", serviceType=" + serviceType + ", paymentNetwork=" + paymentNetwork + ", document=" + document + '}'; 
    } 


    @Override 
    public Object getPk() { 
        return getId(); 
    } 

    @Override 
    public String getTableName() { 
        return super.getTableName(this.getClass()); //To change body of generated methods, choose Tools | Templates. 
    } 
 
    public void setCashBox(String cashBox) { 
        this.cashBox = cashBox; 
    } 

    public String getCashBox() { 
        return this.cashBox; 
    } 

    public String getCashier() { 
        return cashier; 
    } 

    public void setCashier(String cashier) { 
        this.cashier = cashier; 
    } 

    public RemittanceStatus getStatus() {
        return status;
    }

    public void setStatus(RemittanceStatus status) {
        this.status = status;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public List<StoreBalanceHistory> getBalanceHistories() {
        return balanceHistories;
    }

    public void setBalanceHistories(List<StoreBalanceHistory> balanceHistories) {
        this.balanceHistories = balanceHistories;
    }

    public void setCreationDate(String creationDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   @Override
   public int hashCode() {
       int hash = 7;
       hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object obj) {
       if (obj == null) {
           return false;
       }
       if (getClass() != obj.getClass()) {
           return false;
       }
       final Remittance other = (Remittance) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }

   public void setCurrentRemittanceStatus(RemittanceHasRemittenceStatus currentRemittanceStatus) {
		this.currentRemittanceStatus = currentRemittanceStatus;
	}
   
	public RemittanceHasRemittenceStatus getCurrentRemittanceStatus() {
		return this.currentRemittanceStatus;
	}
}
