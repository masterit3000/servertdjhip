package com.tindung.jhip.service.mapper;

import com.tindung.jhip.domain.*;
import com.tindung.jhip.service.dto.ImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Images and its DTO ImagesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {

   

    default Images fromId(Long id) {
        if (id == null) {
            return null;
        }
        Images images = new Images();
        images.setId(id);
        return images;
    }
}
