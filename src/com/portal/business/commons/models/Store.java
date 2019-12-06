/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.portal.business.commons.models;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "store")
@NamedQueries({
    @NamedQuery(name = QueryConstants.LOAD_STORE_BY_LOGIN,
    query = "SELECT s FROM Store s WHERE s.login=:login"),
    @NamedQuery(name = QueryConstants.LOAD_STORE_BY_EMAIL,
    query = "SELECT s FROM Store s WHERE s.email=:email")
})
public class Store extends RemittenceEntity implements Serializable {
       private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Timestamp creationDate;
    private String email;
    private boolean enabled;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "addressId")
    private Address address;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "enterpriseId")
    private Enterprise enterprise;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "languageId")
    private Language language;
    private Float balance;
    private Float crediLimit;
    private Timestamp lastBillingDate;
    private Timestamp nexBillingDate;
    private Boolean isPrePaid;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "periodId")
    private Period period;
    @XmlTransient
    @OneToMany(mappedBy = "store", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<StoreHasRemittent> storeHasRemittents;



    public Store() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Boolean getIsPrePaid() {
        return isPrePaid;
    }

    public void setIsPrePaid(Boolean isPrePaid) {
        this.isPrePaid = isPrePaid;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getCrediLimit() {
        return crediLimit;
    }

    public void setCrediLimit(Float crediLimit) {
        this.crediLimit = crediLimit;
    }

    public Timestamp getLastBillingDate() {
        return lastBillingDate;
    }

    public void setLastBillingDate(Timestamp lastBillingDate) {
        this.lastBillingDate = lastBillingDate;
    }

    public Timestamp getNexBillingDate() {
        return nexBillingDate;
    }

    public void setNexBillingDate(Timestamp nexBillingDate) {
        this.nexBillingDate = nexBillingDate;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
    
//        public List<StoreBalanceHistory> getBalanceHistories() {
//        return balanceHistories;
//    }
//
//    public void setBalanceHistories(List<StoreBalanceHistory> balanceHistories) {
//        this.balanceHistories = balanceHistories;
//    }


    public List<StoreHasRemittent> getStoreHasRemittents() {
        return storeHasRemittents;
    }

    public void setStoreHasRemittents(List<StoreHasRemittent> storeHasRemittents) {
        this.storeHasRemittents = storeHasRemittents;
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
       final Store other = (Store) obj;
       if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
           return false;
       }
       return true;
   }


}
