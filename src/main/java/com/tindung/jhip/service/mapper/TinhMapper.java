package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.TinhDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tinh and its DTO TinhDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TinhMapper extends EntityMapper<TinhDTO, Tinh> {


    @Mapping(target = "huyens", ignore = true)
    Tinh toEntity(TinhDTO tinhDTO);

    default Tinh fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tinh tinh = new Tinh();
        tinh.setId(id);
        return tinh;
    }
}
