package com.portal.business.commons.models;

import com.portal.business.commons.remittance.RemittanceCurrency;
import com.portal.business.commons.remittance.RemittanceCountry;
import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bpexchange_rate")
public class BPExchangeRate extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private Timestamp beginingDate;
    private Timestamp endingDate;
    private Float amount;
    /*@ManyToOne
    @JoinColumn(name = "countryId")*/
    @Transient
    private RemittanceCountry country;
    /*@ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name="currencyId")*/
    @Transient
    private RemittanceCurrency currency;
    

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

    public RemittanceCountry getCountry() {
        return country;
    }

    public void setCountry(RemittanceCountry country) {
        this.country = country;
    }

    public RemittanceCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(RemittanceCurrency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return super.toString();
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
        final BPExchangeRate other = (BPExchangeRate) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
