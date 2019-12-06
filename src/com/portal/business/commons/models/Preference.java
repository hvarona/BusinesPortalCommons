package com.portal.business.commons.models;


import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "preference")
@NamedQueries({
    @NamedQuery(name = QueryConstants.PREFERENCE_BY_ENTERPRISE,
    //    	    query = "SELECT p FROM Preference p WHERE p.enterprise.id=:enterpriseId"
    query = "SELECT p FROM Preference p ")
})
public class Preference extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final long SESSION = 1l;
    public static final long BACKGROUND = 2l;
    public static final long TRANSACTION = 3l;
    public static final long COMMISSION = 4l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob()
    private String description;
    private boolean enabled;
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "preference", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<PreferenceField> preferenceFields;

    public Preference() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PreferenceField> getPreferenceFields() {
        return preferenceFields;
    }

    public void setPreferenceFields(List<PreferenceField> preferenceFields) {
        this.preferenceFields = preferenceFields;
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
        final Preference other = (Preference) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
