package com.tindung.jhip.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.tindung.jhip.domain.enumeration.NOTRA;

/**
 * A DTO for the GhiNo entity.
 */
public class GhiNoDTO implements Serializable {

    private Long id;

//    @NotNull
    private ZonedDateTime ngayghino;

    @NotNull
    private Double sotien;

    @NotNull
    private NOTRA trangthai;

    @Size(max = 3000)
    private String ghiChu;

    private Long nhanVienId;

    private Long hopDongId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getNgayghino() {
        return ngayghino;
    }

    public void setNgayghino(ZonedDateTime ngayghino) {
        this.ngayghino = ngayghino;
    }

    public Double getSotien() {
        return sotien;
    }

    public void setSotien(Double sotien) {
        this.sotien = sotien;
    }

    public NOTRA getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(NOTRA trangthai) {
        this.trangthai = trangthai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
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

        GhiNoDTO ghiNoDTO = (GhiNoDTO) o;
        if(ghiNoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ghiNoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GhiNoDTO{" +
            "id=" + getId() +
            ", ngayghino='" + getNgayghino() + "'" +
            ", sotien=" + getSotien() +
            ", trangthai='" + getTrangthai() + "'" +
            ", ghiChu='" + getGhiChu() + "'" +
            "}";
    }
}
