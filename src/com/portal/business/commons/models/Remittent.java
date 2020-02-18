package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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




@Entity
@Table(name = "remittent")
@NamedQueries({
    @NamedQuery(name = QueryConstants.LOAD_REMITTENT_BY_PHONE_NUMBER,
    query = "SELECT r FROM Remittent r WHERE r.person.phoneNumber=:phoneNumber"),
    @NamedQuery(name = QueryConstants.LOAD_REMITTENCE_BY_EMAIL,
    query = "SELECT r FROM Remittent r WHERE r.person.email=:email")
})
public class Remittent extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "personId")
    private Person person;
     @XmlTransient
    @OneToMany(mappedBy = "remittent", cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private List<StoreHasRemittent> storeHasRemittents;



    public Remittent() {
        super();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

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
        final Remittent other = (Remittent) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
