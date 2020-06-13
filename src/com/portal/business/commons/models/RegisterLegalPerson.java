package com.portal.business.commons.models;

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
@Table(name = "register_legal_person")
@DiscriminatorValue("3")
public class RegisterLegalPerson extends Register {

    @ManyToOne
    @JoinColumn(name = "idTinType", nullable = false)
    private TinType tinType;

    @Column(name = "identification", nullable = false)
    private String identification;

    @Column(name = "comercialDesignation", nullable = false)
    private String comercialDesignation;

    @Column(name = "businessName", nullable = false)
    private String businessName;

    @Column(name = "businessId", nullable = true)
    private String businessId;

    @Column(name = "businessActivity", nullable = false)
    private String businessActivity;

    @Column(name = "legalAddress", nullable = false)
    private String legalAddress;

    @Column(name = "registerInscriptionDate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date registerInscriptionDate;

    @Column(name = "registerNumber", nullable = false)
    private String registerNumber;

    @Column(name = "paidInCapital", nullable = false)
    private Double paidInCapital;

    @ManyToOne
    @JoinColumn(name = "businessAddress", nullable = false)
    private Address businessAddress;

    @Column(name = "businessPhone", nullable = false)
    private String businessPhone;

    @Column(name = "website", nullable = true)
    private String website;

    @Column(name = "representativeName", nullable = false)
    private String representativeName;

    @Column(name = "representativeLastName", nullable = false)
    private String representativeLastName;

    @ManyToOne
    @JoinColumn(name = "representativeTinType", nullable = false)
    private TinType representativeTinType;

    @Column(name = "representativeId", nullable = false)
    private String representativeIdentification;

    @Column(name = "representativeIdExpireDate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date representativeIdExpireDate;

    @Column(name = "representativeBirthplace", nullable = false)
    private String representativeBirthplace;

    @Column(name = "representativeBirthdate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date representativeBirthdate;

    @Column(name = "representativeMaritalStatus", nullable = false)
    private MaritalStatus representativeMaritalStatus;

    @Column(name = "representativeSex", nullable = false)
    private PersonSexType representativeSex;

    @Column(name = "representativeBeforeId", nullable = true)
    private String representativeBeforeNaturalizedIdentification;

    public RegisterLegalPerson() {
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

    public String getComercialDesignation() {
        return comercialDesignation;
    }

    public void setComercialDesignation(String comercialDesignation) {
        this.comercialDesignation = comercialDesignation;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessActivity() {
        return businessActivity;
    }

    public void setBusinessActivity(String businessActivity) {
        this.businessActivity = businessActivity;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public Date getRegisterInscriptionDate() {
        return registerInscriptionDate;
    }

    public void setRegisterInscriptionDate(Date registerInscriptionDate) {
        this.registerInscriptionDate = registerInscriptionDate;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Double getPaidInCapital() {
        return paidInCapital;
    }

    public void setPaidInCapital(Double paidInCapital) {
        this.paidInCapital = paidInCapital;
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

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativeLastName() {
        return representativeLastName;
    }

    public void setRepresentativeLastName(String representativeLastName) {
        this.representativeLastName = representativeLastName;
    }

    public TinType getRepresentativeTinType() {
        return representativeTinType;
    }

    public void setRepresentativeTinType(TinType representativeTinType) {
        this.representativeTinType = representativeTinType;
    }

    public String getRepresentativeIdentification() {
        return representativeIdentification;
    }

    public void setRepresentativeIdentification(String representativeIdentification) {
        this.representativeIdentification = representativeIdentification;
    }

    public Date getRepresentativeIdExpireDate() {
        return representativeIdExpireDate;
    }

    public void setRepresentativeIdExpireDate(Date representativeIdExpireDate) {
        this.representativeIdExpireDate = representativeIdExpireDate;
    }

    public String getRepresentativeBirthplace() {
        return representativeBirthplace;
    }

    public void setRepresentativeBirthplace(String representativeBirthplace) {
        this.representativeBirthplace = representativeBirthplace;
    }

    public Date getRepresentativeBirthdate() {
        return representativeBirthdate;
    }

    public void setRepresentativeBirthdate(Date representativeBirthdate) {
        this.representativeBirthdate = representativeBirthdate;
    }

    public MaritalStatus getRepresentativeMaritalStatus() {
        return representativeMaritalStatus;
    }

    public void setRepresentativeMaritalStatus(MaritalStatus representativeMaritalStatus) {
        this.representativeMaritalStatus = representativeMaritalStatus;
    }

    public PersonSexType getRepresentativeSex() {
        return representativeSex;
    }

    public void setRepresentativeSex(PersonSexType representativeSex) {
        this.representativeSex = representativeSex;
    }

    public String getRepresentativeBeforeNaturalizedIdentification() {
        return representativeBeforeNaturalizedIdentification;
    }

    public void setRepresentativeBeforeNaturalizedIdentification(String representativeBeforeNaturalizedIdentification) {
        this.representativeBeforeNaturalizedIdentification = representativeBeforeNaturalizedIdentification;
    }

}
