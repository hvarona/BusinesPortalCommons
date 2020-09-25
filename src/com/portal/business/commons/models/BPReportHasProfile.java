package com.portal.business.commons.models;

import com.portal.business.commons.exceptions.TableNotFoundException;
import com.portal.business.commons.generic.RemittenceEntity;
import com.portal.business.commons.utils.QueryConstants;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "bpreport_has_profile")
@NamedQueries({
    @NamedQuery(name = QueryConstants.REPORT_BY_PROFILE,
    query = "SELECT r FROM BPReportHasProfile r WHERE r.profile.id=:profileId"),
    @NamedQuery(name = QueryConstants.DELETE_REPORT_PROFILE,
    query = "DELETE FROM BPReportHasProfile rp WHERE rp.report.id=:reportId")
})
public class BPReportHasProfile extends RemittenceEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //bi-directional many-to-one association to Profile
    @ManyToOne
    @JoinColumn(name = "profileId")
    private BPProfile profile;
    @ManyToOne
    @JoinColumn(name = "reportId")
    private BPReport report;

    public BPReportHasProfile() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BPProfile getProfile() {
        return this.profile;
    }

    public void setProfile(BPProfile profile) {
        this.profile = profile;
    }

    public BPReport getReport() {
        return report;
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
        final BPReportHasProfile other = (BPReportHasProfile) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
