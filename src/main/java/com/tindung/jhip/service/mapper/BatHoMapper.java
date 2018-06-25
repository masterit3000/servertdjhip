package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.BatHoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BatHo and its DTO BatHoDTO.
 */
@Mapper(componentModel = "spring", uses = {HopDongMapper.class})
public interface BatHoMapper extends EntityMapper<BatHoDTO, BatHo> {

    @Mapping(source = "hopdongbh.id", target = "hopdongbhId")
    BatHoDTO toDto(BatHo batHo);

    @Mapping(source = "hopdongbhId", target = "hopdongbh")
    BatHo toEntity(BatHoDTO batHoDTO);

    default BatHo fromId(Long id) {
        if (id == null) {
            return null;
        }
        BatHo batHo = new BatHo();
        batHo.setId(id);
        return batHo;
    }
}
