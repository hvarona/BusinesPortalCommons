package com.portal.business.commons.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.QueryConstants;

@Entity
@Table(name = "bpreport_parameter")
@NamedQuery(name = QueryConstants.DELETE_REPORT_PARAMETER,
query = "DELETE FROM ReportParameter rp WHERE rp.report.id=:reportId")
public class BPReportParameter extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String defaultValue;
    private Integer indexOrder;
    private String name;
    private boolean required;
    //bi-directional many-to-one association to ParameterType
    @ManyToOne
    @JoinColumn(name = "parameterTypeId")
    private BPParameterType parameterType;
    //bi-directional many-to-one association to Report
    @ManyToOne
    @JoinColumn(name = "reportId")
    private BPReport report;

    public BPReportParameter() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getIndexOrder() {
        return this.indexOrder;
    }

    public void setIndexOrder(Integer index) {
        this.indexOrder = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public BPParameterType getParameterType() {
        return this.parameterType;
    }

    public void setParameterType(BPParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public BPReport getReport() {
        return this.report;
    }

    public void setReport(BPReport report) {
        this.report = report;
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
        final BPReportParameter other = (BPReportParameter) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
