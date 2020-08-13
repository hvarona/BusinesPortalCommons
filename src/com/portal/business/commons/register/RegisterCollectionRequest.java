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
@Table(name = "collection_request")
public class RegisterCollectionRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private RegisterCountry country;

    @ManyToOne
    @JoinColumn(name = "personTypeId")
    private RegisterPersonType personType;

    @ManyToOne
    @JoinColumn(name = "collectionTypeId")
    private RegisterCollectionType type;

    @Column(name = "description")
    private String description;

    public RegisterCollectionRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegisterCountry getCountry() {
        return country;
    }

    public void setCountry(RegisterCountry country) {
        this.country = country;
    }

    public RegisterPersonType getPersonType() {
        return personType;
    }

    public void setPersonType(RegisterPersonType personType) {
        this.personType = personType;
    }

    public RegisterCollectionType getType() {
        return type;
    }

    public void setType(RegisterCollectionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
