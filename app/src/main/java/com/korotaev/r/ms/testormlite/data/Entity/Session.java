package com.korotaev.r.ms.testormlite.data.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

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

@Entity(name = "session")
@Table(name = "session")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class Session implements Serializable {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String token;
    @DatabaseField
    private Timestamp creationDate;
    @DatabaseField
    private Long user;

    @DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
    @JsonIgnore
    private User userByUser;

    public Session() {
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
    @Column(name = "Token", nullable = false, length = 50)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
    @Column(name = "User", nullable = true)
    @JsonIgnore
    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (id != session.id) return false;
        if (token != null ? !token.equals(session.token) : session.token != null) return false;
        if (creationDate != null ? !creationDate.equals(session.creationDate) : session.creationDate != null)
            return false;
        if (user != null ? !user.equals(session.user) : session.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "User", referencedColumnName = "Id", insertable = false, updatable = false)
    @JsonIgnore
    public User getUserByUser() {
        return userByUser;
    }

    public void setUserByUser(User userByUser) {
        this.userByUser = userByUser;
    }
}
