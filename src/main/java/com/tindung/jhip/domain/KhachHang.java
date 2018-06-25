package com.tindung.jhip.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.tindung.jhip.domain.enumeration.TrangThaiKhachHang;

/**
 * A KhachHang.
 */
@Entity
@Table(name = "khach_hang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KhachHang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "ten", nullable = false)
    private String ten;

    @Size(max = 200)
    @Column(name = "diachi", length = 200)
    private String diachi;

    @Column(name = "dienthoai")
    private String dienthoai;

    @Size(max = 11)
    @Column(name = "cmnd", length = 11)
    private String cmnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangthai")
    private TrangThaiKhachHang trangthai;

    @Column(name = "ngay_tao")
    private ZonedDateTime ngayTao;

    @Column(name = "email")
    private String email;

    @Column(name = "facebook")
    private String facebook;

    @Size(max = 3000)
    @Column(name = "ghi_chu", length = 3000)
    private String ghiChu;

    @ManyToOne
    private Xa xa;

    @OneToMany(mappedBy = "khachHang")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnhKhachHang> anhs = new HashSet<>();

    @OneToMany(mappedBy = "khachHang")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<HopDong> hopdongs = new HashSet<>();

    @ManyToOne
    private CuaHang cuaHang;

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

    public KhachHang ten(String ten) {
        this.ten = ten;
        return this;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public KhachHang diachi(String diachi) {
        this.diachi = diachi;
        return this;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public KhachHang dienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
        return this;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getCmnd() {
        return cmnd;
    }

    public KhachHang cmnd(String cmnd) {
        this.cmnd = cmnd;
        return this;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public TrangThaiKhachHang getTrangthai() {
        return trangthai;
    }

    public KhachHang trangthai(TrangThaiKhachHang trangthai) {
        this.trangthai = trangthai;
        return this;
    }

    public void setTrangthai(TrangThaiKhachHang trangthai) {
        this.trangthai = trangthai;
    }

    public ZonedDateTime getNgayTao() {
        return ngayTao;
    }

    public KhachHang ngayTao(ZonedDateTime ngayTao) {
        this.ngayTao = ngayTao;
        return this;
    }

    public void setNgayTao(ZonedDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getEmail() {
        return email;
    }

    public KhachHang email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public KhachHang facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public KhachHang ghiChu(String ghiChu) {
        this.ghiChu = ghiChu;
        return this;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Xa getXa() {
        return xa;
    }

    public KhachHang xa(Xa xa) {
        this.xa = xa;
        return this;
    }

    public void setXa(Xa xa) {
        this.xa = xa;
    }

    public Set<AnhKhachHang> getAnhs() {
        return anhs;
    }

    public KhachHang anhs(Set<AnhKhachHang> anhKhachHangs) {
        this.anhs = anhKhachHangs;
        return this;
    }

    public KhachHang addAnh(AnhKhachHang anhKhachHang) {
        this.anhs.add(anhKhachHang);
        anhKhachHang.setKhachHang(this);
        return this;
    }

    public KhachHang removeAnh(AnhKhachHang anhKhachHang) {
        this.anhs.remove(anhKhachHang);
        anhKhachHang.setKhachHang(null);
        return this;
    }

    public void setAnhs(Set<AnhKhachHang> anhKhachHangs) {
        this.anhs = anhKhachHangs;
    }

    public Set<HopDong> getHopdongs() {
        return hopdongs;
    }

    public KhachHang hopdongs(Set<HopDong> hopDongs) {
        this.hopdongs = hopDongs;
        return this;
    }

    public KhachHang addHopdong(HopDong hopDong) {
        this.hopdongs.add(hopDong);
        hopDong.setKhachHang(this);
        return this;
    }

    public KhachHang removeHopdong(HopDong hopDong) {
        this.hopdongs.remove(hopDong);
        hopDong.setKhachHang(null);
        return this;
    }

    public void setHopdongs(Set<HopDong> hopDongs) {
        this.hopdongs = hopDongs;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public KhachHang cuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
        return this;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
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
        KhachHang khachHang = (KhachHang) o;
        if (khachHang.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), khachHang.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KhachHang{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", dienthoai='" + getDienthoai() + "'" +
            ", cmnd='" + getCmnd() + "'" +
            ", trangthai='" + getTrangthai() + "'" +
            ", ngayTao='" + getNgayTao() + "'" +
            ", email='" + getEmail() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
