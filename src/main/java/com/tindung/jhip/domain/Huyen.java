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
 * A Huyen.
 */
@Entity
@Table(name = "huyen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Huyen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @ManyToOne
    private Tinh tinh;

    @OneToMany(mappedBy = "huyen")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Xa> xas = new HashSet<>();

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

    public Huyen ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Tinh getTinh() {
        return tinh;
    }

    public Huyen tinh(Tinh tinh) {
        this.tinh = tinh;
        return this;
    }

    public void setTinh(Tinh tinh) {
        this.tinh = tinh;
    }

    public Set<Xa> getXas() {
        return xas;
    }

    public Huyen xas(Set<Xa> xas) {
        this.xas = xas;
        return this;
    }

    public Huyen addXa(Xa xa) {
        this.xas.add(xa);
        xa.setHuyen(this);
        return this;
    }

    public Huyen removeXa(Xa xa) {
        this.xas.remove(xa);
        xa.setHuyen(null);
        return this;
    }

    public void setXas(Set<Xa> xas) {
        this.xas = xas;
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
        Huyen huyen = (Huyen) o;
        if (huyen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), huyen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Huyen{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            "}";
    }
}
