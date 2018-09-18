package com.tindung.jhip.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BatHo entity.
 */
public class BatHoDTO implements Serializable {

    private Long id;
    private String nhanVienTen;
    private String khachHangTen;
    private int soNgayTraCham;
    private double soTienTraCham;

    public int getSoNgayTraCham() {
        return soNgayTraCham;
    }

    public void setSoNgayTraCham(int soNgayTraCham) {
        this.soNgayTraCham = soNgayTraCham;
    }

    public double getSoTienTraCham() {
        return soTienTraCham;
    }

    public void setSoTienTraCham(double soTienTraCham) {
        this.soTienTraCham = soTienTraCham;
    }

    public String getNhanVienTen() {
        return nhanVienTen;
    }

    public void setNhanVienTen(String nhanVienTen) {
        this.nhanVienTen = nhanVienTen;
    }

    public String getKhachHangTen() {
        return khachHangTen;
    }

    public void setKhachHangTen(String khachHangTen) {
        this.khachHangTen = khachHangTen;
    }
    @NotNull
    private Double tienduakhach;

    @NotNull
    private Double tongtien;

    @NotNull
    private Integer tongsongay;

    @NotNull
    private Integer chuky;

    private HopDongDTO hopdong;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTienduakhach() {
        return tienduakhach;
    }

    public void setTienduakhach(Double tienduakhach) {
        this.tienduakhach = tienduakhach;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }

    public Integer getTongsongay() {
        return tongsongay;
    }

    public void setTongsongay(Integer tongsongay) {
        this.tongsongay = tongsongay;
    }

    public Integer getChuky() {
        return chuky;
    }

    public void setChuky(Integer chuky) {
        this.chuky = chuky;
    }

    public HopDongDTO getHopdong() {
        return hopdong;
    }

    public void setHopdong(HopDongDTO hopdong) {
        this.hopdong = hopdong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BatHoDTO batHoDTO = (BatHoDTO) o;
        if (batHoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), batHoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BatHoDTO{"
                + "id=" + getId()
                + ", tienduakhach=" + getTienduakhach()
                + ", tongtien=" + getTongtien()
                + ", tongsongay=" + getTongsongay()
                + ", chuky=" + getChuky()
                + "}";
    }
}
