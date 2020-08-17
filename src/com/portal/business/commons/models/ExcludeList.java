/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author luis
 */
@Entity
@Table(name = "ExcludeList")
@NamedQueries({
    @NamedQuery(name = "ExcludeList.findAll", query = "SELECT e FROM ExcludeList e"),
    @NamedQuery(name = "ExcludeList.findById", query = "SELECT e FROM ExcludeList e WHERE e.id = :id"),
    @NamedQuery(name = "ExcludeList.findBySdnType", query = "SELECT e FROM ExcludeList e WHERE e.sdnType = :sdnType"),
    @NamedQuery(name = "ExcludeList.findByLastName", query = "SELECT e FROM ExcludeList e WHERE e.lastName = :lastName"),
    @NamedQuery(name = "ExcludeList.findByFirstName", query = "SELECT e FROM ExcludeList e WHERE e.firstName = :firstName"),
    @NamedQuery(name = "ExcludeList.findByTitle", query = "SELECT e FROM ExcludeList e WHERE e.title = :title"),
    @NamedQuery(name = "ExcludeList.findByDateOfBirth", query = "SELECT e FROM ExcludeList e WHERE e.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "ExcludeList.findByPlaceOfBirth", query = "SELECT e FROM ExcludeList e WHERE e.placeOfBirth = :placeOfBirth"),
    @NamedQuery(name = "ExcludeList.findByAddressList", query = "SELECT e FROM ExcludeList e WHERE e.addressList = :addressList"),
    @NamedQuery(name = "ExcludeList.findByRemarks", query = "SELECT e FROM ExcludeList e WHERE e.remarks = :remarks"),
    @NamedQuery(name = "ExcludeList.findByIdType", query = "SELECT e FROM ExcludeList e WHERE e.idType = :idType"),
    @NamedQuery(name = "ExcludeList.findByIdValue", query = "SELECT e FROM ExcludeList e WHERE e.idValue = :idValue"),
    @NamedQuery(name = "ExcludeList.findByIdList", query = "SELECT e FROM ExcludeList e WHERE e.idList = :idList"),
    @NamedQuery(name = "ExcludeList.findByIdCountry", query = "SELECT e FROM ExcludeList e WHERE e.idCountry = :idCountry")})



public class ExcludeList extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //@Column(name = "sdnType")
    private String sdnType;
    //@Column(name = "lastName")
    private String lastName;
    //@Column(name = "firstName")
    private String firstName;
    //@Column(name = "title")
    private String title;
    //@Column(name = "dateOfBirth")
    private String dateOfBirth;
    //@Column(name = "placeOfBirth")
    private String placeOfBirth;
    //@Column(name = "addressList")
    private String addressList;
    //@Column(name = "remarks")
    private String remarks;
    //@Column(name = "idType")
    private String idType;

    private String idValue;
    
    //@Column(name = "idCountry")
    private String idCountry;
    
    private String idList;
    


    public ExcludeList() {
    }

    public ExcludeList(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSdnType() {
        return sdnType;
    }

    public void setSdnType(String sdnType) {
        this.sdnType = sdnType;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getAddressList() {
        return addressList;
    }

    public void setAddressList(String addressList) {
        this.addressList = addressList;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(String idCountry) {
        this.idCountry = idCountry;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExcludeList)) {
            return false;
        }
        ExcludeList other = (ExcludeList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "soalexclude.ExcludeList[ id=" + id + "\n"
                + "sdnType="        + sdnType + "\n"
                + "lastName="       + lastName + "\n"
                + "firstName="      + firstName + "\n"
                + "title="          + title + "\n"
                + "dateOfBirth="    + dateOfBirth + "\n"
                + "placeOfBirth="   + placeOfBirth + "\n"
                + "addressList="    + addressList + "\n"
                + "remarks="        + remarks + "\n"
                + "idType="         + idType + "\n"
                + "idList="         + idList + "\n"
                + "idCountry="      + idCountry +"\n"
                //+"lastName=" + idNumber;
                + "-------]";
    }

    @Override
    public Object getPk() {
        return getId();
    }

    @Override
    public String getTableName() throws TableNotFoundException {
        return super.getTableName(this.getClass());
    }
    
}
