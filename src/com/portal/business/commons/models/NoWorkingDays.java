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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "no_working_days")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NoWorkingDays.findAll", query = "SELECT n FROM NoWorkingDays n"),
    @NamedQuery(name = "NoWorkingDays.findById", query = "SELECT n FROM NoWorkingDays n WHERE n.id = :id"),
    @NamedQuery(name = "NoWorkingDays.findByDay", query = "SELECT n FROM NoWorkingDays n WHERE n.day = :day"),
    @NamedQuery(name = "NoWorkingDays.findByMonth", query = "SELECT n FROM NoWorkingDays n WHERE n.month = :month"),
    @NamedQuery(name = "NoWorkingDays.findByYear", query = "SELECT n FROM NoWorkingDays n WHERE n.year = :year"),
    @NamedQuery(name = "NoWorkingDays.findByEnabled", query = "SELECT n FROM NoWorkingDays n WHERE n.enabled = :enabled")})
public class NoWorkingDays extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int day;
    private int month;
    private int year;
    private Boolean enabled;
    private String description;



    public NoWorkingDays() {
    }

    public NoWorkingDays(Long id) {
        this.id = id;
    }

//    public NoWorkingDays(Long id, int day, int month, int year) {
//        this.id = id;
//        this.day = day;
//        this.month = month;
//        this.year = year;
//    }

        public NoWorkingDays(Long id, int day, int month, int year, String description) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.description = description;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof NoWorkingDays)) {
            return false;
        }
        NoWorkingDays other = (NoWorkingDays) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.remettence.commons.data.NoWorkingDays[ id=" + id + " " + description + " ]";
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
