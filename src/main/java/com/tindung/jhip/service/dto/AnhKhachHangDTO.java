package com.tindung.jhip.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AnhKhachHang entity.
 */
public class AnhKhachHangDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 1000)
    private String url;

    private Long khachHangId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(Long khachHangId) {
        this.khachHangId = khachHangId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnhKhachHangDTO anhKhachHangDTO = (AnhKhachHangDTO) o;
        if(anhKhachHangDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anhKhachHangDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnhKhachHangDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
