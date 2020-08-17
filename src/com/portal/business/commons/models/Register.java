package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author henry
 */
@Entity
@Table(name = "register")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "RTYPE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class Register extends RemittenceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "applianceDate")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date applianceDate;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "website")
    private String website;

    @ManyToOne
    @JoinColumn(name = "idAddress")
    private Address businessAddress;

    @ManyToOne
    @JoinColumn(name = "idPerson")
    private Person person;

    public Register() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getApplianceDate() {
        return applianceDate;
    }

    public void setApplianceDate(Date applianceDate) {
        this.applianceDate = applianceDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(Address businessAddress) {
        this.businessAddress = businessAddress;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public Object getPk() {
        return id;
    }

    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }

}
