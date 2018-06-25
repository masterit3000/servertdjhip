package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AnhTaiSan.
 */
@Entity
@Table(name = "anh_tai_san")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnhTaiSan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 1000)
    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @ManyToOne
    private TaiSan taiSan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public AnhTaiSan url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TaiSan getTaiSan() {
        return taiSan;
    }

    public AnhTaiSan taiSan(TaiSan taiSan) {
        this.taiSan = taiSan;
        return this;
    }

    public void setTaiSan(TaiSan taiSan) {
        this.taiSan = taiSan;
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
        AnhTaiSan anhTaiSan = (AnhTaiSan) o;
        if (anhTaiSan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anhTaiSan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnhTaiSan{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
