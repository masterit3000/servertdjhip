package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.CuaHangDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CuaHang and its DTO CuaHangDTO.
 */
@Mapper(componentModel = "spring", uses = {XaMapper.class})
public interface CuaHangMapper extends EntityMapper<CuaHangDTO, CuaHang> {

    @Mapping(source = "xa.id", target = "xaId")
    CuaHangDTO toDto(CuaHang cuaHang);

    @Mapping(source = "xaId", target = "xa")
    @Mapping(target = "thuchis", ignore = true)
    @Mapping(target = "khachhangs", ignore = true)
    @Mapping(target = "nhanviens", ignore = true)
    @Mapping(target = "hopdongs", ignore = true)
    CuaHang toEntity(CuaHangDTO cuaHangDTO);

    default CuaHang fromId(Long id) {
        if (id == null) {
            return null;
        }
        CuaHang cuaHang = new CuaHang();
        cuaHang.setId(id);
        return cuaHang;
    }
}
