package com.korotaev.r.ms.testormlite.data.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import org.springframework.transaction.annotation.Transactional;

@Entity(name = "tool")
@Table(name = "tool")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class Tool implements Serializable {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private Long type;
    @DatabaseField
    private Long user;
    @DatabaseField
    private Byte isDeleted;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private Tooltypes tooltypesByType;
    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    private User userByUser;

    public Tool() {
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
    @Column(name = "Type", nullable = true)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tool tool = (Tool) o;

        if (id != tool.id) return false;
        if (type != null ? !type.equals(tool.type) : tool.type != null) return false;
        if (user != null ? !user.equals(tool.user) : tool.user != null) return false;
        if (isDeleted != null ? !isDeleted.equals(tool.isDeleted) : tool.isDeleted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Type", referencedColumnName = "Id", insertable = false, updatable = false)
    public Tooltypes getTooltypesByType() {
        return tooltypesByType;
    }

    public void setTooltypesByType(Tooltypes tooltypesByType) {
        this.tooltypesByType = tooltypesByType;
    }

    @ManyToOne
    @JoinColumn(name = "User", referencedColumnName = "Id", insertable = false, updatable = false)
    public User getUserByUser() {
        return userByUser;
    }

    public void setUserByUser(User userByUser) {
        this.userByUser = userByUser;
    }
}
