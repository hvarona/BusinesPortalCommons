package com.portal.business.commons.models;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "account")
@NamedQueries({
    @NamedQuery(name = QueryConstants.LOAD_ACCOUNT_BY_LOGIN,
    query = "SELECT a FROM Account a WHERE a.login=:login"),
    @NamedQuery(name = QueryConstants.LOAD_ACCOUNT_BY_EMAIL,
    query = "SELECT a FROM Account a WHERE a.email=:email")
})
public class Account extends RemittenceEntity implements Serializable {
       private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String fullName;
    private Timestamp creationDate;
    private String email;
    private String phoneNumber;
    private Boolean enabled;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "languageId")
    private Language language;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<AccountHasIpAddress> accountHasIpAddress;
    

    public Account() {
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<AccountHasIpAddress> getAccountHasIpAddress() {
        List<AccountHasIpAddress> list = new ArrayList<AccountHasIpAddress>();
        for (AccountHasIpAddress ipAddress : accountHasIpAddress){
            if (ipAddress.getEndingDate()==null )
                list.add(ipAddress);
        }
        return list;
    }

    public void setAccountHasIpAddress(List<AccountHasIpAddress> accountHasIpAddress) {
        this.accountHasIpAddress = accountHasIpAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getLogin() {
        return login;
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
        final Account other = (Account) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


}
