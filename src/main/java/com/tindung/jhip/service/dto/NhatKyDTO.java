package com.tindung.jhip.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the NhatKy entity.
 */
public class NhatKyDTO implements Serializable {

    private Long id;

    private ZonedDateTime thoiGian;

    @Size(max = 300)
    private String noiDung;

    private Long cuaHangId;

    private Long nhanVienId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(ZonedDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
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

        NhatKyDTO nhatKyDTO = (NhatKyDTO) o;
        if(nhatKyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhatKyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhatKyDTO{" +
            "id=" + getId() +
            ", thoiGian='" + getThoiGian() + "'" +
            ", noiDung='" + getNoiDung() + "'" +
            "}";
    }
}
