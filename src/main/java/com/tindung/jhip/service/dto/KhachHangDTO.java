package com.tindung.jhip.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.tindung.jhip.domain.enumeration.TrangThaiKhachHang;

/**
 * A DTO for the KhachHang entity.
 */
public class KhachHangDTO implements Serializable {

    private String tenTinh;
    private String tenHuyen;
    private String tenXa;

    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
    }

    public String getTenHuyen() {
        return tenHuyen;
    }

    public void setTenHuyen(String tenHuyen) {
        this.tenHuyen = tenHuyen;
    }

    public String getTenXa() {
        return tenXa;
    }

    public void setTenXa(String tenXa) {
        this.tenXa = tenXa;
    }

    private Long id;

    @NotNull
    private String ten;

    @Size(max = 200)
    private String diachi;

    private String dienthoai;

    @Size(max = 11)
    private String cmnd;

    private TrangThaiKhachHang trangthai;

    private ZonedDateTime ngayTao;

    private String email;

    private String facebook;

    @Size(max = 3000)
    private String ghiChu;

    private Long xaId;

    private Long cuaHangId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public TrangThaiKhachHang getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(TrangThaiKhachHang trangthai) {
        this.trangthai = trangthai;
    }

    public ZonedDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(ZonedDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Long getXaId() {
        return xaId;
    }

    public void setXaId(Long xaId) {
        this.xaId = xaId;
    }

    public Long getCuaHangId() {
        return cuaHangId;
    }

    public void setCuaHangId(Long cuaHangId) {
        this.cuaHangId = cuaHangId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KhachHangDTO khachHangDTO = (KhachHangDTO) o;
        if (khachHangDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), khachHangDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KhachHangDTO{"
                + "id=" + getId()
                + ", ten='" + getTen() + "'"
                + ", diachi='" + getDiachi() + "'"
                + ", dienthoai='" + getDienthoai() + "'"
                + ", cmnd='" + getCmnd() + "'"
                + ", trangthai='" + getTrangthai() + "'"
                + ", ngayTao='" + getNgayTao() + "'"
                + ", email='" + getEmail() + "'"
                + ", facebook='" + getFacebook() + "'"
                + ", ghiChu='" + getGhiChu() + "'"
                + "}";
    }
}
