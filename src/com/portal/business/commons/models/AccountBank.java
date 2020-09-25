package com.portal.business.commons.models;

import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hvarona
 */
@Entity
@Table(name = "account_bank")
public class AccountBank extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "businessId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Business commerce;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "updateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "accountTypeBankId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccountTypeBank accountTypeBankId;
    @JoinColumn(name = "bankId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BPBank bankId;
    @JoinColumn(name = "statusAccountBankId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StatusAccountBank statusAccountBankId;

    public AccountBank() {
    }

    public AccountBank(Long id) {
        this.id = id;
    }

    public AccountBank(Long id, Business commerce, String accountNumber, Date createDate) {
        this.id = id;
        this.commerce = commerce;
        this.accountNumber = accountNumber;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Business getCommerce() {
        return commerce;
    }

    public void setCommerce(Business commerce) {
        this.commerce = commerce;
    }

  

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public AccountTypeBank getAccountTypeBankId() {
        return accountTypeBankId;
    }

    public void setAccountTypeBankId(AccountTypeBank accountTypeBankId) {
        this.accountTypeBankId = accountTypeBankId;
    }

    public BPBank getBankId() {
        return bankId;
    }

    public void setBankId(BPBank bankId) {
        this.bankId = bankId;
    }

    public StatusAccountBank getStatusAccountBankId() {
        return statusAccountBankId;
    }

    public void setStatusAccountBankId(StatusAccountBank statusAccountBankId) {
        this.statusAccountBankId = statusAccountBankId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final AccountBank other = (AccountBank) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.portal.business.commons.models.AccountBank[ id=" + id + " ]";
    }
    
     @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName() {
        return super.getTableName(this.getClass());
    }


}
