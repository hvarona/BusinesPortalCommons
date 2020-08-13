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

@Entity
@Table(name = "state")
public class RegisterState implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", length = 50)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "countryId")
    private RegisterCountry country;

    public RegisterState() {
    }

    public RegisterState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegisterCountry getCountry() {
        return this.country;
    }

    public void setCountry(RegisterCountry country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final RegisterState other = (RegisterState) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
