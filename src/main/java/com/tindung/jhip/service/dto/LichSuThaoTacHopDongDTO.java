package com.tindung.jhip.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the LichSuThaoTacHopDong entity.
 */
public class LichSuThaoTacHopDongDTO implements Serializable {

    private Long id;

//    @NotNull
    private ZonedDateTime thoigian;

    @NotNull
    @Size(max = 3000)
    private String noidung;

    private Long nhanVienId;

    private Long hopDongId;
    private String nhanVienTen;
    @NotNull
    private Double soTienGhiNo;
    @NotNull
    private Double soTienGhiCo;
    
    public Double getSoTienGhiNo() {
        return soTienGhiNo;
    }

    public void setSoTienGhiNo(Double soTienGhiNo) {
        this.soTienGhiNo = soTienGhiNo;
    }

    public Double getSoTienGhiCo() {
        return soTienGhiCo;
    }

    public void setSoTienGhiCo(Double soTienGhiCo) {
        this.soTienGhiCo = soTienGhiCo;
    }

    public String getNhanVienTen() {
        return nhanVienTen;
    }

    public void setNhanVienTen(String nhanVienTen) {
        this.nhanVienTen = nhanVienTen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getThoigian() {
        return thoigian;
    }

    public void setThoigian(ZonedDateTime thoigian) {
        this.thoigian = thoigian;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public Long getHopDongId() {
        return hopDongId;
    }

    public void setHopDongId(Long hopDongId) {
        this.hopDongId = hopDongId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = (LichSuThaoTacHopDongDTO) o;
        if (lichSuThaoTacHopDongDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lichSuThaoTacHopDongDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LichSuThaoTacHopDongDTO{"
                + "id=" + getId()
                + ", thoigian='" + getThoigian() + "'"
                + ", noidung='" + getNoidung() + "'"
                + "}";
    }
}
