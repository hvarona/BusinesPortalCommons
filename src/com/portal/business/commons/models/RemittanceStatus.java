package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;

@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "remittance_status")
public class RemittanceStatus extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long STARTED = 1L;
    public static final Long PROCESSED = 2L;
    public static final Long SENT = 3L;
    public static final Long CONFIRMED = 4L;
    public static final Long STORE_CLOSED = 5L;
    public static final Long CLOSED = 6L;
    public static final Long NULLED = 7L;
    public static final Long APRROVAL_PENDDING = 8L;
    public static final Long APRROVED = 9L;
    public static final Long DELIVERED = 10L;
    public static final Long RETAINED = 11L; 
    public static final Long TRANSIT = 12L; 
    public static final Long MODIFIED = 13L; 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public RemittanceStatus(Long id) {
        this.id = id;
    }

    public RemittanceStatus() {
    }

    public Long getId() {
        return this.id;
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
        final RemittanceStatus other = (RemittanceStatus) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
