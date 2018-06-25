package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.NhatKyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NhatKy and its DTO NhatKyDTO.
 */
@Mapper(componentModel = "spring", uses = {CuaHangMapper.class, NhanVienMapper.class})
public interface NhatKyMapper extends EntityMapper<NhatKyDTO, NhatKy> {

    @Mapping(source = "cuaHang.id", target = "cuaHangId")
    @Mapping(source = "nhanVien.id", target = "nhanVienId")
    NhatKyDTO toDto(NhatKy nhatKy);

    @Mapping(source = "cuaHangId", target = "cuaHang")
    @Mapping(source = "nhanVienId", target = "nhanVien")
    NhatKy toEntity(NhatKyDTO nhatKyDTO);

    default NhatKy fromId(Long id) {
        if (id == null) {
            return null;
        }
        NhatKy nhatKy = new NhatKy();
        nhatKy.setId(id);
        return nhatKy;
    }
}
