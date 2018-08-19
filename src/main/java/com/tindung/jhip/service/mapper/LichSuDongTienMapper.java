package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LichSuDongTien and its DTO LichSuDongTienDTO.
 */
@Mapper(componentModel = "spring", uses = {NhanVienMapper.class, HopDongMapper.class})
public interface LichSuDongTienMapper extends EntityMapper<LichSuDongTienDTO, LichSuDongTien> {

    @Mapping(source = "nhanVien.id", target = "nhanVienId")
    @Mapping(source = "hopDong.id", target = "hopDongId")
    @Mapping(source = "hopDong.mahopdong", target = "mahopdong")
    @Mapping(source = "hopDong.khachHang.ten", target = "khachHangTen")
    @Mapping(source = "nhanVien.ten", target = "nhanVienTen")
    LichSuDongTienDTO toDto(LichSuDongTien lichSuDongTien);

    @Mapping(source = "nhanVienId", target = "nhanVien")
    @Mapping(source = "hopDongId", target = "hopDong")
    LichSuDongTien toEntity(LichSuDongTienDTO lichSuDongTienDTO);

    default LichSuDongTien fromId(Long id) {
        if (id == null) {
            return null;
        }
        LichSuDongTien lichSuDongTien = new LichSuDongTien();
        lichSuDongTien.setId(id);
        return lichSuDongTien;
    }
}
