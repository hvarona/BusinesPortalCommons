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

@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "payment_network_has_point")
public class PaymentNetworkHasPoint extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paymentNetworkId")
    private PaymentNetwork paymentNetwork;
    @ManyToOne
    @JoinColumn(name = "pointId")
    private PaymentNetworkPoint paymentNetworkPoint;

    public PaymentNetworkHasPoint() {
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

    public PaymentNetworkPoint getPaymentNetworkPoint() {
        return paymentNetworkPoint;
    }

    public void setPaymentNetworkPoint(PaymentNetworkPoint paymentNetworkPoint) {
        this.paymentNetworkPoint = paymentNetworkPoint;
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String toString() {
        return "PaymentNetworkHasPoint{" + "id=" + id + ", paymentNetwork=" + paymentNetwork + ", paymentNetworkPoint=" + paymentNetworkPoint + '}';
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
        final PaymentNetworkHasPoint other = (PaymentNetworkHasPoint) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
