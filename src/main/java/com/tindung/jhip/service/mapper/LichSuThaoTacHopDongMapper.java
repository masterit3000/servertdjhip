package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LichSuThaoTacHopDong and its DTO LichSuThaoTacHopDongDTO.
 */
@Mapper(componentModel = "spring", uses = {NhanVienMapper.class, HopDongMapper.class})
public interface LichSuThaoTacHopDongMapper extends EntityMapper<LichSuThaoTacHopDongDTO, LichSuThaoTacHopDong> {

    @Mapping(source = "nhanVien.id", target = "nhanVienId")
    @Mapping(source = "hopDong.id", target = "hopDongId")
    LichSuThaoTacHopDongDTO toDto(LichSuThaoTacHopDong lichSuThaoTacHopDong);

    @Mapping(source = "nhanVienId", target = "nhanVien")
    @Mapping(source = "hopDongId", target = "hopDong")
    LichSuThaoTacHopDong toEntity(LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO);

    default LichSuThaoTacHopDong fromId(Long id) {
        if (id == null) {
            return null;
        }
        LichSuThaoTacHopDong lichSuThaoTacHopDong = new LichSuThaoTacHopDong();
        lichSuThaoTacHopDong.setId(id);
        return lichSuThaoTacHopDong;
    }
}
