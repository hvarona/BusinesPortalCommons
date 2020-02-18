package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;

@Entity
@Table(name = "store_has_remittent")
public class StoreHasRemittent extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
//        (cascade={CascadeType.REFRESH})
    @JoinColumn(name = "storeId")
    private Store store;
    @ManyToOne
//        (cascade={CascadeType.REFRESH})
    @JoinColumn(name = "remittentId")
    private Remittent remittent;

    public StoreHasRemittent() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Remittent getRemittent() {
        return remittent;
    }

    public void setRemittent(Remittent remittent) {
        this.remittent = remittent;
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
       final StoreHasRemittent other = (StoreHasRemittent) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }
}
