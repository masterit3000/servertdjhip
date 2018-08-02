package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.NhanVienDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity NhanVien and its DTO NhanVienDTO.
 */
@Mapper(componentModel = "spring", uses = {XaMapper.class, CuaHangMapper.class, UserMapper.class})
public interface NhanVienMapper extends EntityMapper<NhanVienDTO, NhanVien> {

    @Mapping(source = "xa.id", target = "xaId")
    @Mapping(source = "cuaHang.id", target = "cuaHangId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "xa.ten", target = "tenXa")
    @Mapping(source = "xa.huyen.ten", target = "tenHuyen")
    @Mapping(source = "xa.huyen.tinh.ten", target = "tenTinh")
    NhanVienDTO toDto(NhanVien nhanVien);

    @Mapping(source = "xaId", target = "xa")
    @Mapping(source = "cuaHangId", target = "cuaHang")
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "nhatkies", ignore = true)
    @Mapping(target = "thuchis", ignore = true)
    @Mapping(target = "lichsudongtiens", ignore = true)
    @Mapping(target = "ghinos", ignore = true)
    @Mapping(target = "hopdongs", ignore = true)
    @Mapping(target = "lichsuthaotacnvs", ignore = true)
    NhanVien toEntity(NhanVienDTO nhanVienDTO);

    default NhanVien fromId(Long id) {
        if (id == null) {
            return null;
        }
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(id);
        return nhanVien;
    }
}
