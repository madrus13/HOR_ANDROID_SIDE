package com.korotaev.r.ms.testormlite.data.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

import javax.persistence.*;

//import org.springframework.transaction.annotation.Transactional;

@Entity(name = "achievement")
@Table(name = "achievement")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class Achievement implements Serializable {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private Long user;
    @DatabaseField
    private Byte isDeleted;
    @DatabaseField
    private Long type;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Achievmenttype achievmenttypeByType;

    public Achievement() {
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
    @Column(name = "Name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "User", nullable = true)
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
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
    @Column(name = "Type", nullable = true)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Achievement that = (Achievement) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id.intValue();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Type", referencedColumnName = "Id", insertable = false, updatable = false)
    public Achievmenttype getAchievmenttypeByType() {
        return achievmenttypeByType;
    }

    public void setAchievmenttypeByType(Achievmenttype achievmenttypeByType) {
        this.achievmenttypeByType = achievmenttypeByType;
    }
}
