package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.HopDongDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HopDong and its DTO HopDongDTO.
 */
@Mapper(componentModel = "spring", uses = {KhachHangMapper.class, CuaHangMapper.class, NhanVienMapper.class})
public interface HopDongMapper extends EntityMapper<HopDongDTO, HopDong> {

    @Mapping(source = "khachHang.id", target = "khachHangId")
    @Mapping(source = "khachHang.ten", target = "khachHangTen")
    @Mapping(source = "cuaHang.id", target = "cuaHangId")
    @Mapping(source = "nhanVien.id", target = "nhanVienId")
    @Mapping(source = "hopdonggoc.id", target = "hopdonggocId")
    HopDongDTO toDto(HopDong hopDong);

    @Mapping(source = "khachHangId", target = "khachHang")
    @Mapping(source = "khachHangTen", target = "khachHang.ten")
    @Mapping(source = "cuaHangId", target = "cuaHang")
    @Mapping(source = "nhanVienId", target = "nhanVien")
    @Mapping(source = "hopdonggocId", target = "hopdonggoc")
    @Mapping(target = "taisans", ignore = true)
    @Mapping(target = "ghinos", ignore = true)
    @Mapping(target = "lichsudongtiens", ignore = true)
    @Mapping(target = "lichsuthaotachds", ignore = true)
    HopDong toEntity(HopDongDTO hopDongDTO);

    default HopDong fromId(Long id) {
        if (id == null) {
            return null;
        }
        HopDong hopDong = new HopDong();
        hopDong.setId(id);
        return hopDong;
    }
}
