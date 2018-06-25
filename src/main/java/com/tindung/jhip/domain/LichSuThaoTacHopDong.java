package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LichSuThaoTacHopDong.
 */
@Entity
@Table(name = "lich_su_thao_tac_hop_dong")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LichSuThaoTacHopDong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "thoigian", nullable = false)
    private ZonedDateTime thoigian;

    @NotNull
    @Size(max = 3000)
    @Column(name = "noidung", length = 3000, nullable = false)
    private String noidung;

    @ManyToOne
    private NhanVien nhanVien;

    @ManyToOne
    private HopDong hopDong;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getThoigian() {
        return thoigian;
    }

    public LichSuThaoTacHopDong thoigian(ZonedDateTime thoigian) {
        this.thoigian = thoigian;
        return this;
    }

    public void setThoigian(ZonedDateTime thoigian) {
        this.thoigian = thoigian;
    }

    public String getNoidung() {
        return noidung;
    }

    public LichSuThaoTacHopDong noidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public LichSuThaoTacHopDong nhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        return this;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public HopDong getHopDong() {
        return hopDong;
    }

    public LichSuThaoTacHopDong hopDong(HopDong hopDong) {
        this.hopDong = hopDong;
        return this;
    }

    public void setHopDong(HopDong hopDong) {
        this.hopDong = hopDong;
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
        LichSuThaoTacHopDong lichSuThaoTacHopDong = (LichSuThaoTacHopDong) o;
        if (lichSuThaoTacHopDong.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lichSuThaoTacHopDong.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LichSuThaoTacHopDong{" +
            "id=" + getId() +
            ", thoigian='" + getThoigian() + "'" +
            ", noidung='" + getNoidung() + "'" +
            "}";
    }
}
