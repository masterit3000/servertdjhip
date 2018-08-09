package com.tindung.jhip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.tindung.jhip.domain.enumeration.DONGTIEN;

/**
 * A LichSuDongTien.
 */
@Entity
@Table(name = "lich_su_dong_tien")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LichSuDongTien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ngaybatdau", nullable = false)
    private ZonedDateTime ngaybatdau;

    @NotNull
    @Column(name = "ngayketthuc", nullable = false)
    private ZonedDateTime ngayketthuc;

//    @NotNull
    @Column(name = "ngaygiaodich", nullable = false)
    private ZonedDateTime ngaygiaodich;

    public ZonedDateTime getNgaygiaodich() {
        return ngaygiaodich;
    }

    public void setNgaygiaodich(ZonedDateTime ngaygiaodich) {
        this.ngaygiaodich = ngaygiaodich;
    }
    
    @NotNull
    @Column(name = "sotien", nullable = false)
    private Double sotien;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai", nullable = false)
    private DONGTIEN trangthai;

    @Size(max = 3000)
    @Column(name = "ghi_chu", length = 3000)
    private String ghiChu;

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

    public ZonedDateTime getNgaybatdau() {
        return ngaybatdau;
    }

    public LichSuDongTien ngaybatdau(ZonedDateTime ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
        return this;
    }

    public void setNgaybatdau(ZonedDateTime ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public ZonedDateTime getNgayketthuc() {
        return ngayketthuc;
    }

    public LichSuDongTien ngayketthuc(ZonedDateTime ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
        return this;
    }

    public void setNgayketthuc(ZonedDateTime ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public Double getSotien() {
        return sotien;
    }

    public LichSuDongTien sotien(Double sotien) {
        this.sotien = sotien;
        return this;
    }

    public void setSotien(Double sotien) {
        this.sotien = sotien;
    }

    public DONGTIEN getTrangthai() {
        return trangthai;
    }

    public LichSuDongTien trangthai(DONGTIEN trangthai) {
        this.trangthai = trangthai;
        return this;
    }

    public void setTrangthai(DONGTIEN trangthai) {
        this.trangthai = trangthai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public LichSuDongTien ghiChu(String ghiChu) {
        this.ghiChu = ghiChu;
        return this;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public LichSuDongTien nhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        return this;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public HopDong getHopDong() {
        return hopDong;
    }

    public LichSuDongTien hopDong(HopDong hopDong) {
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
        LichSuDongTien lichSuDongTien = (LichSuDongTien) o;
        if (lichSuDongTien.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lichSuDongTien.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LichSuDongTien{"
                + "id=" + getId()
                + ", ngaybatdau='" + getNgaybatdau() + "'"
                + ", ngayketthuc='" + getNgayketthuc() + "'"
                + ", sotien=" + getSotien()
                + ", trangthai='" + getTrangthai() + "'"
                + ", ghiChu='" + getGhiChu() + "'"
                + "}";
    }
}
