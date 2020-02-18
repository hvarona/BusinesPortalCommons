package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "sale_price_history")
public class SalePriceHistory extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float currentAmount;
    private Timestamp beginningDate;
    private Timestamp endingdate;
    private Float oldAmount;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "salePriceId")
    private SalePrice salePrice;
  

    public SalePriceHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCurrentAmount() {
        return this.currentAmount;
    }

    public void setCurrentAmount(Float currentAmount) {
        this.currentAmount = currentAmount;
    }

  
    public Float getOldAmount() {
        return this.oldAmount;
    }

    public void setOldAmount(Float oldAmount) {
        this.oldAmount = oldAmount;
    }

    public Timestamp getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(Timestamp beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Timestamp getEndingdate() {
        return endingdate;
    }

    public void setEndingdate(Timestamp endingdate) {
        this.endingdate = endingdate;
    }

    public SalePrice getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(SalePrice salePrice) {
        this.salePrice = salePrice;
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
       final SalePriceHistory other = (SalePriceHistory) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }


}
