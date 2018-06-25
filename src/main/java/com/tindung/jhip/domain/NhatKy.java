package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A NhatKy.
 */
@Entity
@Table(name = "nhat_ky")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhatKy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thoi_gian")
    private ZonedDateTime thoiGian;

    @Size(max = 300)
    @Column(name = "noi_dung", length = 300)
    private String noiDung;

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

    public ZonedDateTime getThoiGian() {
        return thoiGian;
    }

    public NhatKy thoiGian(ZonedDateTime thoiGian) {
        this.thoiGian = thoiGian;
        return this;
    }

    public void setThoiGian(ZonedDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public NhatKy noiDung(String noiDung) {
        this.noiDung = noiDung;
        return this;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public NhatKy cuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
        return this;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public NhatKy nhanVien(NhanVien nhanVien) {
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
        NhatKy nhatKy = (NhatKy) o;
        if (nhatKy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhatKy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhatKy{" +
            "id=" + getId() +
            ", thoiGian='" + getThoiGian() + "'" +
            ", noiDung='" + getNoiDung() + "'" +
            "}";
    }
}
