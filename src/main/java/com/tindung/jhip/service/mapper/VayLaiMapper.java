package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.VayLaiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VayLai and its DTO VayLaiDTO.
 */
@Mapper(componentModel = "spring", uses = {HopDongMapper.class})
public interface VayLaiMapper extends EntityMapper<VayLaiDTO, VayLai> {

    @Mapping(source = "hopdongvl", target = "hopdongvl")
    VayLaiDTO toDto(VayLai vayLai);

    @Mapping(source = "hopdongvl", target = "hopdongvl")
    VayLai toEntity(VayLaiDTO vayLaiDTO);

    default VayLai fromId(Long id) {
        if (id == null) {
            return null;
        }
        VayLai vayLai = new VayLai();
        vayLai.setId(id);
        return vayLai;
    }
}
