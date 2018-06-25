package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.TaiSanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TaiSan and its DTO TaiSanDTO.
 */
@Mapper(componentModel = "spring", uses = {HopDongMapper.class})
public interface TaiSanMapper extends EntityMapper<TaiSanDTO, TaiSan> {

    @Mapping(source = "hopDong.id", target = "hopDongId")
    TaiSanDTO toDto(TaiSan taiSan);

    @Mapping(source = "hopDongId", target = "hopDong")
    @Mapping(target = "anhs", ignore = true)
    TaiSan toEntity(TaiSanDTO taiSanDTO);

    default TaiSan fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaiSan taiSan = new TaiSan();
        taiSan.setId(id);
        return taiSan;
    }
}
