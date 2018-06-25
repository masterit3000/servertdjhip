package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.HuyenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Huyen and its DTO HuyenDTO.
 */
@Mapper(componentModel = "spring", uses = {TinhMapper.class})
public interface HuyenMapper extends EntityMapper<HuyenDTO, Huyen> {

    @Mapping(source = "tinh.id", target = "tinhId")
    HuyenDTO toDto(Huyen huyen);

    @Mapping(source = "tinhId", target = "tinh")
    @Mapping(target = "xas", ignore = true)
    Huyen toEntity(HuyenDTO huyenDTO);

    default Huyen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Huyen huyen = new Huyen();
        huyen.setId(id);
        return huyen;
    }
}
