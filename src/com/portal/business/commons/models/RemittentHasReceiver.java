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
@Table(name = "remittent_has_receiver")
public class RemittentHasReceiver extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //bi-directional many-to-one association to PaymentType
    @ManyToOne
    @JoinColumn(name = "receiverId")
    private Receiver receiver;
    @ManyToOne
    @JoinColumn(name = "remittentId")
    private Remittent remittent;

    public RemittentHasReceiver() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiverId) {
        this.receiver = receiverId;
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
        final RemittentHasReceiver other = (RemittentHasReceiver) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
