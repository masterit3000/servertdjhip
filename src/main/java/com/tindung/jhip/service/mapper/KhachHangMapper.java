package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.KhachHangDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity KhachHang and its DTO KhachHangDTO.
 */
@Mapper(componentModel = "spring", uses = {XaMapper.class, CuaHangMapper.class})
public interface KhachHangMapper extends EntityMapper<KhachHangDTO, KhachHang> {

    @Mapping(source = "xa.id", target = "xaId")
    @Mapping(source = "cuaHang.id", target = "cuaHangId")
    @Mapping(source = "xa.ten", target = "tenXa")
    @Mapping(source = "xa.huyen.ten", target = "tenHuyen")
    @Mapping(source = "xa.huyen.tinh.ten", target = "tenTinh")
    KhachHangDTO toDto(KhachHang khachHang);

    @Mapping(source = "xaId", target = "xa")
    @Mapping(target = "anhs", ignore = true)
    @Mapping(target = "hopdongs", ignore = true)
    @Mapping(source = "cuaHangId", target = "cuaHang")
    KhachHang toEntity(KhachHangDTO khachHangDTO);

    default KhachHang fromId(Long id) {
        if (id == null) {
            return null;
        }
        KhachHang khachHang = new KhachHang();
        khachHang.setId(id);
        return khachHang;
    }
}
