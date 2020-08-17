package com.portal.business.commons.models;

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
@Table(name = "person_legal")
@DiscriminatorValue("2")
public class LegalPerson extends Person {

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
    @JoinColumn(name = "idRepresentative")
    private NaturalPerson representative;

    public LegalPerson() {
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

    public NaturalPerson getRepresentative() {
        return representative;
    }

    public void setRepresentative(NaturalPerson representative) {
        this.representative = representative;
    }

}
