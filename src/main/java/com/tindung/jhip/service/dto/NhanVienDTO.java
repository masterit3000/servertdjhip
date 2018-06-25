package com.tindung.jhip.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.tindung.jhip.domain.enumeration.TrangThaiNhanVien;

/**
 * A DTO for the NhanVien entity.
 */
public class NhanVienDTO implements Serializable {

    private Long id;

    @NotNull
    private String ten;

    @Size(max = 200)
    private String diachi;

    private String dienthoai;

    @Size(max = 11)
    private String cmnd;

    private TrangThaiNhanVien trangthai;

    private ZonedDateTime ngayTao;

    @Size(max = 3000)
    private String ghiChu;

    private Long xaId;

    private Long cuaHangId;

    private Long userId;

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

    public TrangThaiNhanVien getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(TrangThaiNhanVien trangthai) {
        this.trangthai = trangthai;
    }

    public ZonedDateTime getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(ZonedDateTime ngayTao) {
        this.ngayTao = ngayTao;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NhanVienDTO nhanVienDTO = (NhanVienDTO) o;
        if(nhanVienDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhanVienDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhanVienDTO{" +
            "id=" + getId() +
            ", ten='" + getTen() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", dienthoai='" + getDienthoai() + "'" +
            ", cmnd='" + getCmnd() + "'" +
            ", trangthai='" + getTrangthai() + "'" +
            ", ngayTao='" + getNgayTao() + "'" +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
