package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Images.
 */
@Entity
@Table(name = "images")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Images implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Images name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Images images = (Images) o;
        if (images.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), images.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Lob
    @Column(name = "file")
    private byte[] file;

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Images{"
                + "id=" + getId()
                + ", name='" + getName() + "'"
                + "}";
    }
}
