package com.tindung.jhip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tinh.
 */
@Entity
@Table(name = "tinh")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tinh implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @OneToMany(mappedBy = "tinh")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Huyen> huyens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public Tinh ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Set<Huyen> getHuyens() {
        return huyens;
    }

    public Tinh huyens(Set<Huyen> huyens) {
        this.huyens = huyens;
        return this;
    }

    public Tinh addHuyen(Huyen huyen) {
        this.huyens.add(huyen);
        huyen.setTinh(this);
        return this;
    }

    public Tinh removeHuyen(Huyen huyen) {
        this.huyens.remove(huyen);
        huyen.setTinh(null);
        return this;
    }

    public void setHuyens(Set<Huyen> huyens) {
        this.huyens = huyens;
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
        Tinh tinh = (Tinh) o;
        if (tinh.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tinh.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tinh{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
