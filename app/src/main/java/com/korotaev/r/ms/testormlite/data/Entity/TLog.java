package com.korotaev.r.ms.testormlite.data.Entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.korotaev.r.ms.testormlite.data.Transactional;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Date;

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

@Entity(name = "TLog")
@Table(name = "TLog")
@Transactional
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
@DatabaseTable
public class TLog implements Serializable {
    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String name;

    @DatabaseField
    private Date date;

    @DatabaseField
    private String type;


    public TLog() {
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
    @Column(name = "Type", nullable = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Basic
    @Column(name = "Date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TLog that = (TLog) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        Integer result = id.intValue();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

}
