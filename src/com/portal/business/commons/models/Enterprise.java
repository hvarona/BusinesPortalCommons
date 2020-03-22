package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;


@Entity
@Table(name = "enterprise")
public class Enterprise extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long ALODIGA_USA = 1L;
    public static final Long ALODIGA_VENEZUELA = 2L;
    public static final Long ALODIGA_COLOMBIA = 3L;
    public static final Long ALODIGA_PANAMA = 4L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String atcNumber;
    private String email;
    private boolean enabled;
    private String invoiceAddress;
    private String name;
    private String url;


    public Enterprise() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAtcNumber() {
        return this.atcNumber;
    }

    public void setAtcNumber(String atcNumber) {
        this.atcNumber = atcNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getInvoiceAddress() {
        return this.invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
    @Override
    public String toString() {
        return "Enterprise{" + "id=" + id + ", address=" + address + ", atcNumber=" + atcNumber + ", email=" + email + ", enabled=" + enabled + ", invoiceAddress=" + invoiceAddress + ", name=" + name + ", url=" + url + '}';
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
        final Enterprise other = (Enterprise) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}