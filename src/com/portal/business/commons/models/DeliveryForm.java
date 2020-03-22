package com.portal.business.commons.models;

/**
 *
 * @author henry
 */
public class DeliveryForm {

    private long id;

    private String name;

    public DeliveryForm() {
    }

    public DeliveryForm(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DeliveryForm other = (DeliveryForm) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
