package com.portal.business.commons.models;

public class Bank {

    private long id;
    private String name;
    private Country country;
    private String redChapinaId;

    public Bank() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getRedChapinaId() {
        return redChapinaId;
    }

    public void setRedChapinaId(String redChapinaId) {
        this.redChapinaId = redChapinaId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Bank other = (Bank) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
