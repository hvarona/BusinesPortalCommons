package com.portal.business.commons.models;

import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "store_close")
public class StoreClose extends RemittenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "storeid")
    private Store store;

    @Column(name = "closeDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;

    @Column(name = "closeAmount")
    private Float closeAmount;

    public StoreClose() {
    }

    public StoreClose(Long id) {
        this.id = id;
    }

    public StoreClose(Long id, Store store, Timestamp closeDate, Float closeAmount) {
        this.id = id;
        this.store = store;
        this.closeDate = closeDate;
        this.closeAmount = closeAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Float getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(Float closeAmount) {
        this.closeAmount = closeAmount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StoreClose)) {
            return false;
        }
        StoreClose other = (StoreClose) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StoreClose{" + "id=" + id + ", store=" + store + ", closedate=" + closeDate + ", closeamount=" + closeAmount + '}';
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName() {
        return super.getTableName(this.getClass()); //To change body of generated methods, choose Tools | Templates.
    }

}
