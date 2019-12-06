package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "rate_payment_network")
public class RatePaymentNetwork extends RemittenceEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp beginingDate;
    private Timestamp endingDate;
    private Float amount;
    @ManyToOne
    @JoinColumn(name = "paymentNetworkId")
    private PaymentNetwork paymentNetwork;
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
    

    public RatePaymentNetwork() {
    }

    public RatePaymentNetwork(Long id, Timestamp beginingDate, Timestamp endingDate, Float amount, PaymentNetwork paymentNetwork, Store store) {
        this.id = id;
        this.beginingDate = beginingDate;
        this.endingDate = endingDate;
        this.amount = amount;
        this.paymentNetwork = paymentNetwork;
        this.store = store;
    }
    
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getBeginingDate() {
        return beginingDate;
    }

    public void setBeginingDate(Timestamp beginingDate) {
        this.beginingDate = beginingDate;
    }

    public Timestamp getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Timestamp endingDate) {
        this.endingDate = endingDate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public PaymentNetwork getPaymentNetwork() {
        return paymentNetwork;
    }

    public void setPaymentNetwork(PaymentNetwork paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "RatePaymentNetwork{" + "id=" + id + ", beginingDate=" + beginingDate + ", endingDate=" + endingDate + ", amount=" + amount + ", paymentNetwork=" + paymentNetwork + ", store=" + store + '}';
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
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
        final RatePaymentNetwork other = (RatePaymentNetwork) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

       
}
