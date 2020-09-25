package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Table(name = "bppreference_field")
public class BPPreferenceField extends RemittenceEntity implements Serializable {

    public BPPreferenceField() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "preferenceTypeId")
    private BPPreferenceType preferenceType;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "preferenceId")
    private BPPreference preference;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "preferenceField", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<BPPreferenceFieldData> data;

    @Transient
    private BPLanguage currentLanguage;

    @Transient
    private String currentValue;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BPPreferenceType getPreferenceType() {
        return preferenceType;
    }

    public void setPreferenceType(BPPreferenceType preferenceType) {
        this.preferenceType = preferenceType;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public BPPreference getPreference() {
        return preference;
    }

    public void setPreference(BPPreference preference) {
        this.preference = preference;
    }

    public List<BPPreferenceFieldData> getData() {
        return data;
    }

    public void setData(List<BPPreferenceFieldData> data) {
        this.data = data;
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String toString() {
        return super.toString();
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
        final BPPreferenceField other = (BPPreferenceField) obj;
        return !((this.id == null) ? (other.id != null) : !this.id.equals(other.id));
    }

    public BPLanguage getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(BPLanguage currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public String getDisplayName() {
        if (currentLanguage == null || data == null) {
            return name;
        }
        for (BPPreferenceFieldData dat : data) {
            if (dat.getLanguage().getIso().equals(currentLanguage.getIso())) {
                return dat.getName();
            }
        }
        return name;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public boolean getCurrentBooleanValue() {
        return currentValue != null && currentValue.equals("1");
    }

    public void setCurrentBooleanValue(boolean currentValue) {
        this.currentValue = currentValue ? "1" : "0";
    }

}
