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
@Table(name = "payment_patner_has_payment_type")
public class PaymentPatnerHasPaymentType extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //bi-directional many-to-one association to PaymentType
    @ManyToOne
    @JoinColumn(name = "paymentTypeId")
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "paymentPatnerId")
    private PaymentPatner paymentPatner;

    public PaymentPatnerHasPaymentType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentPatner getPaymentPatner() {
        return paymentPatner;
    }

    public void setPaymentPatner(PaymentPatner paymentPatner) {
        this.paymentPatner = paymentPatner;
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
        final PaymentPatnerHasPaymentType other = (PaymentPatnerHasPaymentType) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
