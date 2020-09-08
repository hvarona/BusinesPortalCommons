package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends RemittenceEntity implements Serializable{

     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "shortName")
    private String shortName;
    @Column(name = "code")
    private String code;
    @Column(name = "alternativeName1")
    private String alternativeName1;
    @Column(name = "alternativeName2")
    private String alternativeName2;
    @Column(name = "alternativeName3")
    private String alternativeName3;
  


    public Country() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlternativeName1() {
        return alternativeName1;
    }

    public void setAlternativeName1(String alternativeName1) {
        this.alternativeName1 = alternativeName1;
    }

    public String getAlternativeName2() {
        return alternativeName2;
    }

    public void setAlternativeName2(String alternativeName2) {
        this.alternativeName2 = alternativeName2;
    }

    public String getAlternativeName3() {
        return alternativeName3;
    }

    public void setAlternativeName3(String alternativeName3) {
        this.alternativeName3 = alternativeName3;
    }

      

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Country other = (Country) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

     @Override
    public Object getPk() {
        return this.getId();
    }

    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }
}
