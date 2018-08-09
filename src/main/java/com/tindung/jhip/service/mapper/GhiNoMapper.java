package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.GhiNoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GhiNo and its DTO GhiNoDTO.
 */
@Mapper(componentModel = "spring", uses = {NhanVienMapper.class, HopDongMapper.class})
public interface GhiNoMapper extends EntityMapper<GhiNoDTO, GhiNo> {

    @Mapping(source = "nhanVien.id", target = "nhanVienId")
    @Mapping(source = "hopDong.id", target = "hopDongId")
    @Mapping(source = "nhanVien.ten", target = "nhanVienTen")
    @Mapping(source = "hopDong.khachHang.ten", target = "khachHangTen")
    GhiNoDTO toDto(GhiNo ghiNo);

    @Mapping(source = "nhanVienId", target = "nhanVien")
    @Mapping(source = "hopDongId", target = "hopDong")
    GhiNo toEntity(GhiNoDTO ghiNoDTO);

    default GhiNo fromId(Long id) {
        if (id == null) {
            return null;
        }
        GhiNo ghiNo = new GhiNo();
        ghiNo.setId(id);
        return ghiNo;
    }
}
