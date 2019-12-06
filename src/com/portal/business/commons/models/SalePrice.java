package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "sale_price")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalePrice extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "enterpriseId")
    private Enterprise  enterprise;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "countryId")
    private Country country;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "correspondentId")
    private Correspondent correspondent;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "salesTypeId")
    private SaleType saleType;
    @XmlTransient
    @OneToMany(mappedBy = "salePrice", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<SalePriceHistory> salePriceHistorys;
    
    public SalePrice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Correspondent getCorrespondent() {
        return correspondent;
    }

    public void setCorrespondent(Correspondent correspondent) {
        this.correspondent = correspondent;
    }

    public SaleType getSaleType() {
        return saleType;
    }

    public void setSaleType(SaleType saleType) {
        this.saleType = saleType;
    }

    public List<SalePriceHistory> getSalePriceHistorys() {
        return salePriceHistorys;
    }

    public void setSalePriceHistorys(List<SalePriceHistory> salePriceHistorys) {
        this.salePriceHistorys = salePriceHistorys;
    }
    
    public SalePriceHistory getCurrentSalePriceHistory() {
        SalePriceHistory salePriceHistory = new SalePriceHistory();
        for (SalePriceHistory ahp : this.salePriceHistorys) {
            if (ahp.getEndingdate() == null) {
                salePriceHistory = ahp;
            }
        }
        return salePriceHistory;
    }
    
    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName(Class<?> clazz) {
        return super.getTableName(clazz); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public String toString() {
        return "Saleprice{" + "id=" + id + ", correspondent=" + correspondent + ", country=" + country + ", enterprise=" + enterprise + ", paymentMethod=" + saleType + '}';
    }
    
    
}
