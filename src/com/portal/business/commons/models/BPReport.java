package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.IndexComparator;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import java.util.Collections;

@Entity
@Table(name = "bpreport")
public class BPReport extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Long REPORT_WALLET = 20l;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob()
    private String description;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "reportTypeId")
    private BPReportType reportType;
    private boolean enabled;
    private String name;
    @Lob()
    private String query;
    private String webServiceUrl;
    //bi-directional many-to-one association to ReportHasProfile
    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<BPReportHasProfile> reportHasProfiles;
    //bi-directional many-to-one association to ReportParameter
    @OneToMany(mappedBy = "report", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<BPReportParameter> reportParameters;

    public BPReport() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getWebServiceUrl() {
        return this.webServiceUrl;
    }

    public void setWebServiceUrl(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    public List<BPReportHasProfile> getReportHasProfiles() {
        return this.reportHasProfiles;
    }

    public void setReportHasProfiles(List<BPReportHasProfile> reportHasProfiles) {
        this.reportHasProfiles = reportHasProfiles;
    }

    public List<BPReportParameter> getReportParameters() {
        Collections.sort(reportParameters, new IndexComparator());
        return this.reportParameters;
    }

    public void setReportParameters(List<BPReportParameter> reportParameters) {
        this.reportParameters = reportParameters;
    }

    public BPReportType getReportType() {
        return reportType;
    }

    public void setReportType(BPReportType reportType) {
        this.reportType = reportType;
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
        final BPReport other = (BPReport) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
