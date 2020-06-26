package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.remittance.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "address")
public class Address extends RemittenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;
    @Column(name = "zipCode")
    private String zipCode;

    @Transient
    private RemittanceCity city;
    @Transient
    private RemittanceState state;
    @Transient
    private RemittanceCountry country;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public RemittanceCity getCity() {
        return city;
    }

    public void setCity(RemittanceCity city) {
        this.city = city;
    }

    public RemittanceState getState() {
        return state;
    }

    public void setState(RemittanceState state) {
        this.state = state;
    }

    public RemittanceCountry getCountry() {
        return country;
    }

    public void setCountry(RemittanceCountry country) {
        this.country = country;
    }

    @Override
    public Object getPk() {
        return this.id;
    }

    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }

}
