package com.korotaev.r.ms.testormlite.data.Entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.springframework.transaction.annotation.Transactional;

@Entity(name = "request")
@Table(name = "request")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class Request implements Serializable {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String description;
    @DatabaseField
    private Timestamp creationDate;
    @DatabaseField
    private Timestamp modifyDate;
    @DatabaseField
    private Timestamp closeDate;
    @DatabaseField
    private Byte isResolvedByUser;
    @DatabaseField
    private String requestPhotoPath;
    @DatabaseField
    private Byte isDeleted;
    @DatabaseField
    private Long resolvedByUser;
    @DatabaseField
    private Long creationUser;
    @DatabaseField
    private Double latitude;
    @DatabaseField
    private Double longitude;
    @DatabaseField
    private Long type;
    @DatabaseField
    private Long status;
    @DatabaseField
    private Long region;
    @JsonIgnore
    @ForeignCollectionField
    private ForeignCollection<Message> messagesById;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private User userByResolvedByUser;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private User userByCreationUser;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Region regionByRegion;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Requesttype requesttypeByType;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Requeststatus requeststatusByStatus;

    public Request() {
    }

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Region", nullable = true)
    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "CreationDate", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "ModifyDate", nullable = false)
    public Timestamp getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Timestamp modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Basic
    @Column(name = "CloseDate", nullable = true)
    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    @Basic
    @Column(name = "IsResolvedByUser", nullable = true)
    public Byte getIsResolvedByUser() {
        return isResolvedByUser;
    }

    public void setIsResolvedByUser(Byte isResolvedByUser) {
        this.isResolvedByUser = isResolvedByUser;
    }

    @Basic
    @Column(name = "RequestPhotoPath", nullable = true, length = 100)
    public String getRequestPhotoPath() {
        return requestPhotoPath;
    }

    public void setRequestPhotoPath(String requestPhotoPath) {
        this.requestPhotoPath = requestPhotoPath;
    }

    @Basic
    @Column(name = "IsDeleted", nullable = true)
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Basic
    @Column(name = "ResolvedByUser", nullable = true)
    public Long getResolvedByUser() {
        return resolvedByUser;
    }

    public void setResolvedByUser(Long resolvedByUser) {
        this.resolvedByUser = resolvedByUser;
    }

    @Basic
    @Column(name = "CreationUser", nullable = true)
    public Long getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(Long creationUser) {
        this.creationUser = creationUser;
    }

    @Basic
    @Column(name = "Latitude", nullable = true, precision = 0)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "Longitude", nullable = true, precision = 0)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "Type", nullable = true)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Basic
    @Column(name = "Status", nullable = true)
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != request.id) return false;
        if (description != null ? !description.equals(request.description) : request.description != null) return false;
        if (creationDate != null ? !creationDate.equals(request.creationDate) : request.creationDate != null)
            return false;
        if (modifyDate != null ? !modifyDate.equals(request.modifyDate) : request.modifyDate != null) return false;
        if (closeDate != null ? !closeDate.equals(request.closeDate) : request.closeDate != null) return false;
        if (isResolvedByUser != null ? !isResolvedByUser.equals(request.isResolvedByUser) : request.isResolvedByUser != null)
            return false;
        if (requestPhotoPath != null ? !requestPhotoPath.equals(request.requestPhotoPath) : request.requestPhotoPath != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(request.isDeleted) : request.isDeleted != null) return false;
        if (resolvedByUser != null ? !resolvedByUser.equals(request.resolvedByUser) : request.resolvedByUser != null) return false;
        if (creationUser != null ? !creationUser.equals(request.creationUser) : request.creationUser != null)
            return false;
        if (latitude != null ? !latitude.equals(request.latitude) : request.latitude != null) return false;
        if (longitude != null ? !longitude.equals(request.longitude) : request.longitude != null) return false;
        if (type != null ? !type.equals(request.type) : request.type != null) return false;
        if (status != null ? !status.equals(request.status) : request.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (isResolvedByUser != null ? isResolvedByUser.hashCode() : 0);
        result = 31 * result + (requestPhotoPath != null ? requestPhotoPath.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (resolvedByUser != null ? resolvedByUser.hashCode() : 0);
        result = 31 * result + (creationUser != null ? creationUser.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Region", referencedColumnName = "Id", insertable = false, updatable = false)
    public Region getRegionByRegion() {
        return regionByRegion;
    }

    public void setRegionByRegion(Region regionByRegion) {
        this.regionByRegion = regionByRegion;
    }

    @OneToMany(mappedBy = "requestByRequest")
    @JsonIgnore
    public Collection<Message> getMessagesById() {
        return messagesById;
    }

    public void setMessagesById(ForeignCollection<Message> messagesById) {
        this.messagesById = messagesById;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ResolvedByUser", referencedColumnName = "Id", insertable = false, updatable = false)
    public User getUserByResolvedByUser() {
        return userByResolvedByUser;
    }

    public void setUserByResolvedByUser(User userByResolvedByUser) {
        this.userByResolvedByUser = userByResolvedByUser;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "CreationUser", referencedColumnName = "Id", insertable = false, updatable = false)
    public User getUserByCreationUser() {
        return userByCreationUser;
    }

    public void setUserByCreationUser(User userByCreationUser) {
        this.userByCreationUser = userByCreationUser;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Type", referencedColumnName = "Id", insertable = false, updatable = false)
    public Requesttype getRequesttypeByType() {
        return requesttypeByType;
    }

    public void setRequesttypeByType(Requesttype requesttypeByType) {
        this.requesttypeByType = requesttypeByType;
    }

    @ManyToOne
    @JoinColumn(name = "Status", referencedColumnName = "Id", insertable = false, updatable = false)
    public Requeststatus getRequeststatusByStatus() {
        return requeststatusByStatus;
    }

    public void setRequeststatusByStatus(Requeststatus requeststatusByStatus) {
        this.requeststatusByStatus = requeststatusByStatus;
    }
}
