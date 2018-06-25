package com.tindung.jhip.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the AnhTaiSan entity.
 */
public class AnhTaiSanDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 1000)
    private String url;

    private Long taiSanId;

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

    public Long getTaiSanId() {
        return taiSanId;
    }

    public void setTaiSanId(Long taiSanId) {
        this.taiSanId = taiSanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnhTaiSanDTO anhTaiSanDTO = (AnhTaiSanDTO) o;
        if(anhTaiSanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anhTaiSanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnhTaiSanDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
