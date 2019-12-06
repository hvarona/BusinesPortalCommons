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
import java.sql.Timestamp;
import javax.persistence.CascadeType;

@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "remittance_status_has_remittence")
public class RemittanceHasRemittenceStatus extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //bi-directional many-to-one association to PaymentType
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "remittenceId")
    private Remittance remittance;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "remittenceStatusId")
    private RemittanceStatus remittenceStatus;
    private Timestamp beginningDate;
    private Timestamp endingDate;
    private String comments;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    public RemittanceHasRemittenceStatus() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Remittance getRemittance() {
        return remittance;
    }

    public void setRemittance(Remittance remittance) {
        this.remittance = remittance;
    }

    public RemittanceStatus getRemittenceStatus() {
        return remittenceStatus;
    }

    public void setRemittenceStatus(RemittanceStatus remittenceStatus) {
        this.remittenceStatus = remittenceStatus;
    }

    public Timestamp getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(Timestamp beginningDate) {
        this.beginningDate = beginningDate;
    }

    public Timestamp getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Timestamp endingDate) {
        this.endingDate = endingDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        final RemittanceHasRemittenceStatus other = (RemittanceHasRemittenceStatus) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
