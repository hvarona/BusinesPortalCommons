package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "payment_network")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentNetwork extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long CASH = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "countryId")
    private Country country;
    @XmlTransient
    @OneToMany(mappedBy = "paymentNetwork", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<PaymentNetworkHasDeliveryForm> paymentNetworkHasDeliveryForms;
    @XmlTransient
    @OneToMany(mappedBy = "paymentNetwork", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<PaymentNetworkHasPoint> paymentNetworkHasPoints;
    
     public PaymentNetwork() {
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

   
    public void setPaymentNetworkHasDeliveryForms(List<PaymentNetworkHasDeliveryForm> paymentNetworkHasDeliveryForms) {
        this.paymentNetworkHasDeliveryForms = paymentNetworkHasDeliveryForms;
    }

    public List<PaymentNetworkHasPoint> getPaymentNetworkHasPoints() {
        return paymentNetworkHasPoints;
    }

    public void setPaymentNetworkHasPoints(List<PaymentNetworkHasPoint> paymentNetworkHasPoints) {
        this.paymentNetworkHasPoints = paymentNetworkHasPoints;
    }
        
    @Override
    public String toString() {
        return "PaymentNetwork{" + "id=" + id + ", name=" + name + ", enabled=" + enabled + '}';
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
       final PaymentNetwork other = (PaymentNetwork) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }
  
}
