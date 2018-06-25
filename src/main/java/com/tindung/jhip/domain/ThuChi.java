package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.tindung.jhip.domain.enumeration.THUCHI;

/**
 * A ThuChi.
 */
@Entity
@Table(name = "thu_chi")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThuChi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 3000)
    @Column(name = "noidung", length = 3000, nullable = false)
    private String noidung;

    @NotNull
    @Column(name = "thoigian", nullable = false)
    private ZonedDateTime thoigian;

    @Enumerated(EnumType.STRING)
    @Column(name = "thuchi")
    private THUCHI thuchi;

    @ManyToOne
    private CuaHang cuaHang;

    @ManyToOne
    private NhanVien nhanVien;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public ThuChi noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public ZonedDateTime getThoigian() {
        return thoigian;
    }

    public ThuChi thoigian(ZonedDateTime thoigian) {
        this.thoigian = thoigian;
        return this;
    }

    public void setThoigian(ZonedDateTime thoigian) {
        this.thoigian = thoigian;
    }

    public THUCHI getThuchi() {
        return thuchi;
    }

    public ThuChi thuchi(THUCHI thuchi) {
        this.thuchi = thuchi;
        return this;
    }

    public void setThuchi(THUCHI thuchi) {
        this.thuchi = thuchi;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public ThuChi cuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
        return this;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public ThuChi nhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        return this;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
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
        ThuChi thuChi = (ThuChi) o;
        if (thuChi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thuChi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThuChi{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", thoigian='" + getThoigian() + "'" +
            ", thuchi='" + getThuchi() + "'" +
            "}";
    }
}
