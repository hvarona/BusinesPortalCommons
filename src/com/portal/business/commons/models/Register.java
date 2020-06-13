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

    @Column(name = "commercialRegister", unique = false, nullable = true, length = 100000)
    private byte[] commercialRegister;

    @Column(name = "commercialId", unique = false, nullable = true, length = 100000)
    private byte[] commercialId;

    @Column(name = "individualId", unique = false, nullable = true, length = 100000)
    private byte[] individualId;

    @Column(name = "bankSupport", unique = false, nullable = true, length = 100000)
    private byte[] bankSupport;

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

    public byte[] getCommercialRegister() {
        return commercialRegister;
    }

    public void setCommercialRegister(byte[] commercialRegister) {
        this.commercialRegister = commercialRegister;
    }

    public byte[] getCommercialId() {
        return commercialId;
    }

    public void setCommercialId(byte[] commercialId) {
        this.commercialId = commercialId;
    }

    public byte[] getIndividualId() {
        return individualId;
    }

    public void setIndividualId(byte[] individualId) {
        this.individualId = individualId;
    }

    public byte[] getBankSupport() {
        return bankSupport;
    }

    public void setBankSupport(byte[] bankSupport) {
        this.bankSupport = bankSupport;
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
