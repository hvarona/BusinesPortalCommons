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
@Table(name = "payment_network_has_delivery_form")
public class PaymentNetworkHasDeliveryForm extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paymentNetworkId")
    private PaymentNetwork paymentNetwork;
    @ManyToOne
    @JoinColumn(name = "deliveryFormId")
    private DeliveryForm deliveryForm;

    public PaymentNetworkHasDeliveryForm() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentNetwork getPaymentNetwork() {
        return paymentNetwork;
    }

    public void setPaymentNetwork(PaymentNetwork paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    public DeliveryForm getDeliveryForm() {
        return deliveryForm;
    }

    public void setDeliveryForm(DeliveryForm deliveryForm) {
        this.deliveryForm = deliveryForm;
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String toString() {
        return "PaymentNetworkHasDeliveryForm{" + "id=" + id + ", paymentNetwork=" + paymentNetwork + ", deliveryForm=" + deliveryForm + '}';
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
       final PaymentNetworkHasDeliveryForm other = (PaymentNetworkHasDeliveryForm) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }

}
