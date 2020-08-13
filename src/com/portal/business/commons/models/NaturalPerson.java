package com.portal.business.commons.models;

import com.portal.business.commons.enumeration.MaritalStatus;
import com.portal.business.commons.enumeration.PersonSexType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author henry
 */
@Entity
@Table(name = "person_natural")
@DiscriminatorValue("3")
public class NaturalPerson extends Person {

    @Column(name = "idExpireDate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date idExpireDate;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "birthplace", nullable = false)
    private String birthplace;

    @Column(name = "birthdate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;

    @Column(name = "maritalStatus", nullable = false)
    private MaritalStatus maritalStatus;

    @Column(name = "sex", nullable = false)
    private PersonSexType sex;

    public NaturalPerson() {
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

}
