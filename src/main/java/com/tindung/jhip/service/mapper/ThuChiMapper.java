package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.ThuChiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ThuChi and its DTO ThuChiDTO.
 */
@Mapper(componentModel = "spring", uses = {CuaHangMapper.class, NhanVienMapper.class})
public interface ThuChiMapper extends EntityMapper<ThuChiDTO, ThuChi> {

    @Mapping(source = "cuaHang.id", target = "cuaHangId")
    @Mapping(source = "nhanVien.id", target = "nhanVienId")
    ThuChiDTO toDto(ThuChi thuChi);

    @Mapping(source = "cuaHangId", target = "cuaHang")
    @Mapping(source = "nhanVienId", target = "nhanVien")
    ThuChi toEntity(ThuChiDTO thuChiDTO);

    default ThuChi fromId(Long id) {
        if (id == null) {
            return null;
        }
        ThuChi thuChi = new ThuChi();
        thuChi.setId(id);
        return thuChi;
    }
}
