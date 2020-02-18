package com.portal.business.commons.models;

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
@Table(name = "store_close")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "StoreClose.findAll", query = "SELECT s FROM StoreClose s"),
//    @NamedQuery(name = "StoreClose.findById", query = "SELECT s FROM StoreClose s WHERE s.id = :id"),
//    @NamedQuery(name = "StoreClose.findByStoreid", query = "SELECT s FROM StoreClose s WHERE s.store.id = :storeid"),
//    @NamedQuery(name = "StoreClose.findByClosedate", query = "SELECT s FROM StoreClose s WHERE s.closedate = :closedate"),
//    @NamedQuery(name = "StoreClose.findByCloseamount", query = "SELECT s FROM StoreClose s WHERE s.closeamount = :closeamount")})

public class StoreClose extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "storeid")
    private Store store;

    //private Date dateclosedate; 

    private Timestamp closedate;
    private Float closeamount;

    public StoreClose() {
    }

    
        
//    public Date getDateclosedate() {
//        return dateclosedate;
//    }
//
//    public void setDateclosedate(Date dateclosedate) {
//        this.dateclosedate = dateclosedate;
//    }



    public StoreClose(Long id) {
        this.id = id;
    }

    public StoreClose(Long id, Store store, Timestamp closedate, Float closeamount) {
        this.id = id;
        this.store = store;
        this.closedate = closedate;
        this.closeamount = closeamount;
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

    public Timestamp getClosedate() {
        return closedate;
    }

    public void setClosedate(Timestamp closedate) {
        this.closedate = closedate;
    }

    public Float getCloseamount() {
        return closeamount;
    }

    public void setCloseamount(Float closeamount) {
        this.closeamount = closeamount;
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
        return "StoreClose{" + "id=" + id + ", store=" + store + ", closedate=" + closedate + ", closeamount=" + closeamount + '}';
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
