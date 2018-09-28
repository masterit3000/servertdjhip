package com.tindung.jhip.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Images entity.
 */
public class ImagesDTO implements Serializable {

    private Long id;

    private String name;

    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImagesDTO imagesDTO = (ImagesDTO) o;
        if (imagesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), imagesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImagesDTO{"
                + "id=" + getId()
                + ", name='" + getName() + "'"
                + "}";
    }
}
