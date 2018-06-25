package com.tindung.jhip.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TaiSan entity.
 */
public class TaiSanDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 3000)
    private String mota;

    @NotNull
    private String sohieu;

    @NotNull
    @Size(max = 1000)
    private String noicat;

    private Long hopDongId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getSohieu() {
        return sohieu;
    }

    public void setSohieu(String sohieu) {
        this.sohieu = sohieu;
    }

    public String getNoicat() {
        return noicat;
    }

    public void setNoicat(String noicat) {
        this.noicat = noicat;
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

        TaiSanDTO taiSanDTO = (TaiSanDTO) o;
        if(taiSanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taiSanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaiSanDTO{" +
            "id=" + getId() +
            ", mota='" + getMota() + "'" +
            ", sohieu='" + getSohieu() + "'" +
            ", noicat='" + getNoicat() + "'" +
            "}";
    }
}
