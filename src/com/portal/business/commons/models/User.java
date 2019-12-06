package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.generic.RemittenceEntityListerner;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import javax.persistence.NamedQueries;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@EntityListeners(RemittenceEntityListerner.class)
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = QueryConstants.LOGIN_USER,
    query = "SELECT u FROM User u WHERE u.login=:login AND u.password=:password"),
    @NamedQuery(name = QueryConstants.LOAD_USER_BY_LOGIN,
    query = "SELECT u FROM User u WHERE u.login=:login"),
    @NamedQuery(name = QueryConstants.LOAD_USER_BY_EMAIL,
    query = "SELECT u FROM User u WHERE u.email=:email")
})
public class User extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp creationDate;
    private String email;
    private boolean enabled;
    private boolean receiveNotification;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String phoneNumber;
    //bi-directional many-to-one association to UserHasProfileHasEnterprise
//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//    private List<UserHasProfile> userHasProfileHasEnterprises;
    @XmlTransient
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<UserHasProfile> userHasProfile;
        //bi-directional many-to-one association to Language
    @ManyToOne
    @JoinColumn(name = "languageId")
    private Language language;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String login) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    public User(Long id, Timestamp creationDate, String email, boolean enabled, boolean receiveNotification, String firstName, String lastName, String login, String password, String phoneNumber, List<UserHasProfile> userHasProfile) {
        this.id = id;
        this.creationDate = creationDate;
        this.email = email;
        this.enabled = enabled;
        this.receiveNotification = receiveNotification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userHasProfile = userHasProfile;
    }
    
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<UserHasProfile> getUserHasProfile() {
        return userHasProfile;
    }

    public void setUserHasProfile(List<UserHasProfile> userHasProfile) {
        this.userHasProfile = userHasProfile;
    }

    public boolean getReceiveNotification() {
        return receiveNotification;
    }

    public void setReceiveNotification(boolean receiveNotification) {
        this.receiveNotification = receiveNotification;
    }

    public Profile getCurrentProfile() {
        Profile profile = null;
        for (UserHasProfile uhp : this.userHasProfile) {
            if (uhp.getEndingDate() == null ) {
                profile = uhp.getProfile();
            }
        }
        return profile;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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
        final User other = (User) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
