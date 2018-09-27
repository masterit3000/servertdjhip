package com.tindung.jhip.service;

import com.tindung.jhip.service.dto.ImagesDTO;
import java.util.List;

/**
 * Service Interface for managing Images.
 */
public interface ImagesService {

    /**
     * Save a images.
     *
     * @param imagesDTO the entity to save
     * @return the persisted entity
     */
    ImagesDTO save(ImagesDTO imagesDTO);

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    List<ImagesDTO> findAll();

    /**
     * Get the "id" images.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ImagesDTO findOne(Long id);

    /**
     * Delete the "id" images.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
