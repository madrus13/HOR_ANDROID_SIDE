package com.korotaev.r.ms.testormlite.data.Entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.springframework.transaction.annotation.Transactional;

@Entity(name = "transmissiontype")
@Table(name = "transmissiontype")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class TransmissionType implements Serializable {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private Byte isDeleted;
    @JsonIgnore
    @ForeignCollectionField
    private Collection<Auto> autosById;

    public TransmissionType() {
    }

    @Id
    @Column(name = "Id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "IsDeleted", nullable = true)
    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransmissionType that = (TransmissionType) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "transmissionTypeByTransmissionType")
    @JsonIgnore
    public Collection<Auto> getAutosById() {
        return autosById;
    }

    public void setAutosById(Collection<Auto> autosById) {
        this.autosById = autosById;
    }
}
