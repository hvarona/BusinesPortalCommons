package com.portal.business.commons.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author hvarona
 */
@Entity
@Table(name = "operator")
@DiscriminatorValue("2")
public class Operator extends User {

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lasttname")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "idCommerce")
    private Business commerce;

    @ManyToMany
    @JoinTable(
            name = "operator_exclude_permission",
            joinColumns = @JoinColumn(name = "idPermission"),
            inverseJoinColumns = @JoinColumn(name = "idOperator")
    )
    private List<Permission> excludedPermission;

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

    public Business getCommerce() {
        return commerce;
    }

    public void setCommerce(Business commerce) {
        this.commerce = commerce;
    }

    public List<Permission> getExcludedPermission() {
        return excludedPermission;
    }

    public void setExcludedPermission(List<Permission> excludedPermission) {
        this.excludedPermission = excludedPermission;
    }

    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

}
