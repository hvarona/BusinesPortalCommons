package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.QueryConstants;

@Entity
@Table(name = "state")
@NamedQuery(name = QueryConstants.STATES_BY_COUNTRY,
query = "SELECT s FROM State s WHERE s.country.id=:countryId ORDER BY s.name")
public class State extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long FLORIDA = 1389L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //bi-directional many-to-one association to Country
    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;

    public State() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }
    
    @Override
   public int hashCode() {
       int hash = 7;
       hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
       return hash;
   }

   @Override
   public boolean equals(Object obj) {
       if (obj == null) {
           return false;
       }
       if (getClass() != obj.getClass()) {
           return false;
       }
       final State other = (State) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }

}
