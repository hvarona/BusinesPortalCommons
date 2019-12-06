package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sales_type")
public class SaleType extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long CASH = 1L;
    public static final Long CREDIT_CARD = 2L;


    public SaleType() {
    }
    @Id
    private Long id;
    private String name;
    private boolean enabled;
    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    

    @Override
    public String toString() {
        return "RechargeType{ id=" + this.id + ",name=" + this.name + " }";

    }
    
    @Override
    public Object getPk() {
        return getId();
    }


    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }
}
