package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "store_balance")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement

public class StoreBalance extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float creditLimit;
    private Timestamp beginningDate;
    private Timestamp endingDate;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "storeId")
    private Store store;
    
    
//    //$$$
//    @ManyToOne(cascade = {CascadeType.REFRESH})
//    @JoinColumn(name = "countryId")
//    private Country country;
//
//    public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }
//    //$$$
      

    public StoreBalance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Float creditLimit) {
        this.creditLimit = creditLimit;
    }

   
    public Timestamp getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(Timestamp beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Timestamp getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Timestamp endingDate) {
        this.endingDate = endingDate;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public StoreBalance(Long id, Float creditLimit, Timestamp beginningDate, Timestamp endingDate, Store store, Country country) {
        this.id = id;
        this.creditLimit = creditLimit;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.store = store;
//        this.country = country;
    }

    
//    public StoreBalance(Long id, Float creditLimit, Timestamp beginningDate, Timestamp endingDate, Store store) {
//        this.id = id;
//        this.creditLimit = creditLimit;
//        this.beginningDate = beginningDate;
//        this.endingDate = endingDate;
//        this.store = store;
//        this.country = country;
//    }

//    @Override
//    public String toString() {
//        return "StoreBalance{" + "id=" + id + ", creditLimit=" + creditLimit + ", beginningDate=" + beginningDate + ", endingDate=" + endingDate + ", store=" + store + '}';
//    }
  
    @Override
    public String toString() {
        return "StoreBalance{" + "id=" + id + ", creditLimit=" + creditLimit + ", beginningDate=" + beginningDate + ", endingDate=" + endingDate + ", store=" + store.toString() + '}';
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
        final StoreBalance other = (StoreBalance) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
}
