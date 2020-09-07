package com.portal.business.commons.models;

import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author yamea
 */
@Entity
@Table(name = "account_type_bank")
public class AccountTypeBank extends RemittenceEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "code")
    private String code;

    public AccountTypeBank() {
    }

    public AccountTypeBank(Integer id) {
        this.id = id;
    }

    public AccountTypeBank(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
       @Override
    public String toString() {
        return "com.portal.business.commons.models.AccountTypeBank[ id=" + id + " ]";
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName() {
        return super.getTableName(this.getClass());
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final AccountTypeBank other = (AccountTypeBank) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
}
