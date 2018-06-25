package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.AnhTaiSanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AnhTaiSan and its DTO AnhTaiSanDTO.
 */
@Mapper(componentModel = "spring", uses = {TaiSanMapper.class})
public interface AnhTaiSanMapper extends EntityMapper<AnhTaiSanDTO, AnhTaiSan> {

    @Mapping(source = "taiSan.id", target = "taiSanId")
    AnhTaiSanDTO toDto(AnhTaiSan anhTaiSan);

    @Mapping(source = "taiSanId", target = "taiSan")
    AnhTaiSan toEntity(AnhTaiSanDTO anhTaiSanDTO);

    default AnhTaiSan fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnhTaiSan anhTaiSan = new AnhTaiSan();
        anhTaiSan.setId(id);
        return anhTaiSan;
    }
}
