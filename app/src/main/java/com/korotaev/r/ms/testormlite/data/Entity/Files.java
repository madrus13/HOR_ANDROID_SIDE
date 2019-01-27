package com.korotaev.r.ms.testormlite.data.Entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.springframework.transaction.annotation.Transactional;


@Entity(name = "files")
@Table(name = "files")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class Files implements Serializable {

    @DatabaseField(generatedId = true)
    private Long uid;

    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @DatabaseField
    private String description;
    @DatabaseField
    private String fileName;
    @DatabaseField
    private String fileType;
    @DatabaseField
    private Timestamp creationDate;
    @DatabaseField
    private Timestamp modifyDate;
    @DatabaseField
    private String FullPhotoPath;
    @DatabaseField
    private String CompactPhotoPath;
    @DatabaseField
    private Byte isDeleted;
    @DatabaseField
    private Long creationUser;

    public Files() {
    }


    @Column(name = "Id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Description", nullable = true, length = 100)
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
    @Column(name = "IsDeleted", nullable = true)
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Id
    @Column(name = "uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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
    @Column(name = "fileName", nullable = true, length = 200)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "fileType", nullable = true, length = 200)
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Basic
    @Column(name = "FullPhotoPath", nullable = true, length = 200)
    public String getFullPhotoPath() {
        return FullPhotoPath;
    }

    public void setFullPhotoPath(String fullPhotoPath) {
        FullPhotoPath = fullPhotoPath;
    }

    @Basic
    @Column(name = "CompactPhotoPath", nullable = true, length = 200)
    public String getCompactPhotoPath() {
        return CompactPhotoPath;
    }

    public void setCompactPhotoPath(String compactPhotoPath) {
        CompactPhotoPath = compactPhotoPath;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Files request = (Files) o;

        if (id != request.id) return false;
        if (description != null ? !description.equals(request.description) : request.description != null) return false;
        if (creationDate != null ? !creationDate.equals(request.creationDate) : request.creationDate != null)
            return false;
        if (modifyDate != null ? !modifyDate.equals(request.modifyDate) : request.modifyDate != null) return false;
        if (isDeleted != null ? !isDeleted.equals(request.isDeleted) : request.isDeleted != null) return false;

        if (creationUser != null ? !creationUser.equals(request.creationUser) : request.creationUser != null)
            return false;

        if (FullPhotoPath != null ? !FullPhotoPath.equals(request.FullPhotoPath) : request.FullPhotoPath != null)
            return false;
        if (CompactPhotoPath != null ? !CompactPhotoPath.equals(request.CompactPhotoPath) : request.CompactPhotoPath != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        Integer result = 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result += 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result += 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        result += 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result += 31 * result + (creationUser != null ? creationUser.hashCode() : 0);
        result += 31 * result + (FullPhotoPath != null ? FullPhotoPath.hashCode() : 0);

        return result;
    }

}
