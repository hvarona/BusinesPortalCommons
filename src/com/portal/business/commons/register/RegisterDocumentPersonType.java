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
@Table(name = "documentsPersonType")
public class RegisterDocumentPersonType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "description")
    private String descriuption;

    @ManyToOne
    @JoinColumn(name = "personTypeId")
    private RegisterPersonType personType;

    public RegisterDocumentPersonType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriuption() {
        return descriuption;
    }

    public void setDescriuption(String descriuption) {
        this.descriuption = descriuption;
    }

    public RegisterPersonType getPersonType() {
        return personType;
    }

    public void setPersonType(RegisterPersonType personType) {
        this.personType = personType;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegisterDocumentPersonType other = (RegisterDocumentPersonType) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    

}
