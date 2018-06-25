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
 * A TaiSan.
 */
@Entity
@Table(name = "tai_san")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaiSan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 3000)
    @Column(name = "mota", length = 3000, nullable = false)
    private String mota;

    @NotNull
    @Column(name = "sohieu", nullable = false)
    private String sohieu;

    @NotNull
    @Size(max = 1000)
    @Column(name = "noicat", length = 1000, nullable = false)
    private String noicat;

    @ManyToOne
    private HopDong hopDong;

    @OneToMany(mappedBy = "taiSan")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnhTaiSan> anhs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMota() {
        return mota;
    }

    public TaiSan mota(String mota) {
        this.mota = mota;
        return this;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getSohieu() {
        return sohieu;
    }

    public TaiSan sohieu(String sohieu) {
        this.sohieu = sohieu;
        return this;
    }

    public void setSohieu(String sohieu) {
        this.sohieu = sohieu;
    }

    public String getNoicat() {
        return noicat;
    }

    public TaiSan noicat(String noicat) {
        this.noicat = noicat;
        return this;
    }

    public void setNoicat(String noicat) {
        this.noicat = noicat;
    }

    public HopDong getHopDong() {
        return hopDong;
    }

    public TaiSan hopDong(HopDong hopDong) {
        this.hopDong = hopDong;
        return this;
    }

    public void setHopDong(HopDong hopDong) {
        this.hopDong = hopDong;
    }

    public Set<AnhTaiSan> getAnhs() {
        return anhs;
    }

    public TaiSan anhs(Set<AnhTaiSan> anhTaiSans) {
        this.anhs = anhTaiSans;
        return this;
    }

    public TaiSan addAnh(AnhTaiSan anhTaiSan) {
        this.anhs.add(anhTaiSan);
        anhTaiSan.setTaiSan(this);
        return this;
    }

    public TaiSan removeAnh(AnhTaiSan anhTaiSan) {
        this.anhs.remove(anhTaiSan);
        anhTaiSan.setTaiSan(null);
        return this;
    }

    public void setAnhs(Set<AnhTaiSan> anhTaiSans) {
        this.anhs = anhTaiSans;
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
        TaiSan taiSan = (TaiSan) o;
        if (taiSan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taiSan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaiSan{" +
            "id=" + getId() +
            ", mota='" + getMota() + "'" +
            ", sohieu='" + getSohieu() + "'" +
            ", noicat='" + getNoicat() + "'" +
            "}";
    }
}
