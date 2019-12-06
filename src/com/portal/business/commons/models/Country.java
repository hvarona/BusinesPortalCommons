package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;

@Entity
@Table(name = "country")
public class Country extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long USA = 47L;
    public static final Long VENEZUELA = 1L;
    public static final Long COLOMBIA = 35L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortName;
    private String code;
    private String alternativeName1;
    private String alternativeName2;
    private String alternativeName3;
    private String iso;

    public Country() {
    }

    public Country(Long id, String name, String shortName, String code, String alternativeName1, String alternativeName2, String alternativeName3, String iso) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.code = code;
        this.alternativeName1 = alternativeName1;
        this.alternativeName2 = alternativeName2;
        this.alternativeName3 = alternativeName3;
        this.iso = iso;
    }

    public Country(Long id, String name, String shortName, String code) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.code = code;
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

    public String getShortName() {
        return this.shortName;
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

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", name=" + name + ", shortName=" + shortName + ", code=" + code + ", alternativeName1=" + alternativeName1 + ", alternativeName2=" + alternativeName2 + ", alternativeName3=" + alternativeName3 +", iso =" + iso +'}';
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
        final Country other = (Country) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    
}
