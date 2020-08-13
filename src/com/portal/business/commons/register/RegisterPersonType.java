package com.portal.business.commons.register;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author henry
 */
@Entity
@Table(name = "personType")
public class RegisterPersonType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private RegisterCountry country;


    @Column(name = "indNaturalPerson")
    private boolean indNaturalPerson;

    public RegisterPersonType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RegisterCountry getCountry() {
        return country;
    }

    public void setCountry(RegisterCountry country) {
        this.country = country;
    }

    public boolean isIndNaturalPerson() {
        return indNaturalPerson;
    }

    public void setIndNaturalPerson(boolean indNaturalPerson) {
        this.indNaturalPerson = indNaturalPerson;
    }

}
