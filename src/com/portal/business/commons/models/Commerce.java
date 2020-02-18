package com.portal.business.commons.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author hvarona
 */
@Entity
@Table(name = "commerce")
@DiscriminatorValue("3")
public class Commerce extends User {

    @Column(name = "name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "idStatus")
    public CommerceStatus status;

    public Commerce() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommerceStatus getStatus() {
        return status;
    }

    public void setStatus(CommerceStatus status) {
        this.status = status;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

}
