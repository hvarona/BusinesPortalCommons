/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.portal.business.commons.models;
import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "account_has_ip_address")
public class AccountHasIpAddress   extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp beginningDate;
    private Timestamp endingDate;
    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "ipAddressId")
    private IpAddress ipAddress;

    public AccountHasIpAddress() {
    }

    public Long getId() {
	return this.id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Timestamp getBeginningDate() {
	return this.beginningDate;
    }

    public void setBeginningDate(Timestamp beginningDate) {
	this.beginningDate = beginningDate;
    }

    public Timestamp getEndingDate() {
	return this.endingDate;
    }

    public void setEndingDate(Timestamp endingDate) {
	this.endingDate = endingDate;
    }

    public IpAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IpAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Account getAccount() {
	return account;
    }

    public void setAccount(Account account) {
	this.account = account;
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
    public String getTableName() {
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
        final AccountHasIpAddress other = (AccountHasIpAddress) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
