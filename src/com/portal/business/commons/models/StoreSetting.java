package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "store_setting")
@NamedQueries({
    @NamedQuery(name = QueryConstants.STORE_SETTING_BY_PREFERENCE_FIELD,
    query = "SELECT ss FROM StoreSetting ss WHERE ss.preferenceField.id=:preferenceFieldId AND ss.store.id=:storeId AND ss.endingDate IS NULL ORDER BY ss.id DESC")
})
public class StoreSetting extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp beginningDate;
    private Timestamp endingDate;
    private String value;
    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "preferenceFieldId")
    private PreferenceField preferenceField;

    public StoreSetting() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getBeginningDate() {
        return this.beginningDate;
    }

    public void setBeginningDate(Timestamp beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Timestamp getEndingDate() {
        return this.endingDate;
    }

    public void setEndingDate(Timestamp endingDate) {
        this.endingDate = endingDate;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PreferenceField getPreferenceField() {
        return preferenceField;
    }

    public void setPreferenceField(PreferenceField preferenceField) {
        this.preferenceField = preferenceField;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "StoreSetting{" + "id=" + id + ", beginningDate=" + beginningDate + ", endingDate=" + endingDate + ", value=" + value + ", store=" + store + ", preferenceField=" + preferenceField + '}';
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
        final StoreSetting other = (StoreSetting) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
