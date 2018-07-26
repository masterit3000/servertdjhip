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

    @NotNull
    private Double tienduakhach;

    @NotNull
    private Double tongtien;

    @NotNull
    private Integer tongsongay;

    @NotNull
    private Integer chuky;

    private HopDongDTO hopdong;

    private Double tiendadong;

    public Double getTiendadong() {
        return tiendadong;
    }

    public void setTiendadong(Double tiendadong) {
        this.tiendadong = tiendadong;
    }

    public Double getTienconlai() {
        return tienconlai;
    }

    public void setTienconlai(Double tienconlai) {
        this.tienconlai = tienconlai;
    }
    
    private Double tienconlai;

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
