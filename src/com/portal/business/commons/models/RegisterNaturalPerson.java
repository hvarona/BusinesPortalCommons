package com.portal.business.commons.models;

import com.portal.business.commons.remittance.RemittanceAddress;
import com.portal.business.commons.enumeration.MaritalStatus;
import com.portal.business.commons.enumeration.PersonSexType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author henry
 */
@Entity
@Table(name = "register_natural_person")
@DiscriminatorValue("2")
public class RegisterNaturalPerson extends Register {

    @ManyToOne
    @JoinColumn(name = "idTinType", nullable = false)
    private TinType tinType;

    @Column(name = "identification", nullable = false)
    private String identification;

    @Column(name = "idExpireDate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date idExpireDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "marriedSurname", nullable = true)
    private String marriedSurname;

    @Column(name = "birthplace", nullable = false)
    private String birthplace;

    @Column(name = "birthdate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;

    @Column(name = "maritalStatus", nullable = false)
    private MaritalStatus maritalStatus;
    @Column(name = "sex", nullable = false)
    private PersonSexType sex;

    @Column(name = "beforeId", nullable = true)
    private String beforeNaturalizedIdentification;

    @Column(name = "businessId", nullable = true)
    private String businessId;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "businessAddress", nullable = false)
    private Address businessAddress;

    @Column(name = "businessPhone", nullable = false)
    private String businessPhone;

    @Column(name = "website", nullable = true)
    private String website;

    public RegisterNaturalPerson() {
    }

    public TinType getTinType() {
        return tinType;
    }

    public void setTinType(TinType tinType) {
        this.tinType = tinType;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Date getIdExpireDate() {
        return idExpireDate;
    }

    public void setIdExpireDate(Date idExpireDate) {
        this.idExpireDate = idExpireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMarriedSurname() {
        return marriedSurname;
    }

    public void setMarriedSurname(String marriedSurname) {
        this.marriedSurname = marriedSurname;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public PersonSexType getSex() {
        return sex;
    }

    public void setSex(PersonSexType sex) {
        this.sex = sex;
    }

    public String getBeforeNaturalizedIdentification() {
        return beforeNaturalizedIdentification;
    }

    public void setBeforeNaturalizedIdentification(String beforeNaturalizedIdentification) {
        this.beforeNaturalizedIdentification = beforeNaturalizedIdentification;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(Address businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
