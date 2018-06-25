package com.tindung.jhip.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.tindung.jhip.domain.enumeration.THUCHI;

/**
 * A DTO for the ThuChi entity.
 */
public class ThuChiDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3000)
    private String noidung;

    @NotNull
    private ZonedDateTime thoigian;

    private THUCHI thuchi;

    private Long cuaHangId;

    private Long nhanVienId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public ZonedDateTime getThoigian() {
        return thoigian;
    }

    public void setThoigian(ZonedDateTime thoigian) {
        this.thoigian = thoigian;
    }

    public THUCHI getThuchi() {
        return thuchi;
    }

    public void setThuchi(THUCHI thuchi) {
        this.thuchi = thuchi;
    }

    public Long getCuaHangId() {
        return cuaHangId;
    }

    public void setCuaHangId(Long cuaHangId) {
        this.cuaHangId = cuaHangId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThuChiDTO thuChiDTO = (ThuChiDTO) o;
        if(thuChiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thuChiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThuChiDTO{" +
            "id=" + getId() +
            ", noidung='" + getNoidung() + "'" +
            ", thoigian='" + getThoigian() + "'" +
            ", thuchi='" + getThuchi() + "'" +
            "}";
    }
}
