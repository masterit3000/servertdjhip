package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.ImagesService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.ImagesDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Images.
 */
@RestController
@RequestMapping("/api")
public class ImagesResource {

    private final Logger log = LoggerFactory.getLogger(ImagesResource.class);

    private static final String ENTITY_NAME = "images";

    private final ImagesService imagesService;

    public ImagesResource(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    /**
     * POST /images : Create a new images.
     *
     * @param imagesDTO the imagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new imagesDTO, or with status 400 (Bad Request) if the images has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/images")
    @Timed
    public ResponseEntity<ImagesDTO> createImages(@RequestBody ImagesDTO imagesDTO) throws URISyntaxException {
        log.debug("REST request to save Images : {}", imagesDTO);
        if (imagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new images cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImagesDTO result = imagesService.save(imagesDTO);
        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/image-upload/"
    )
    public ResponseEntity<String> handleFileUpload(@RequestParam("myfile") MultipartFile[] file) {
        StringBuilder message = new StringBuilder();
        if (file != null) {
            for (MultipartFile multipartFile : file) {
                try {
                    byte[] readData;//= new byte[(int) multipartFile.getSize()];
//                    storageService.store(multipartFile, id);
                    readData = multipartFile.getBytes();
                    String name = multipartFile.getName();

                    if (readData.length < 10000 || name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) {
                        ImagesDTO imagesDTO = new ImagesDTO();
                        imagesDTO.setFile(readData);
                        imagesDTO.setName(name);
                        ImagesDTO save = imagesService.save(imagesDTO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    message.append("FAIL: ").append(multipartFile.getOriginalFilename()).append("|");
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(message.toString());
        }
        message.append("FAIL to upload ").append(file.length);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message.toString());

    }

    /**
     * PUT /images : Updates an existing images.
     *
     * @param imagesDTO the imagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * imagesDTO, or with status 400 (Bad Request) if the imagesDTO is not
     * valid, or with status 500 (Internal Server Error) if the imagesDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/images")
    @Timed
    public ResponseEntity<ImagesDTO> updateImages(@RequestBody ImagesDTO imagesDTO) throws URISyntaxException {
        log.debug("REST request to update Images : {}", imagesDTO);
        if (imagesDTO.getId() == null) {
            return createImages(imagesDTO);
        }
        ImagesDTO result = imagesService.save(imagesDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imagesDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /images : get all the images.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of images in
     * body
     */
    @GetMapping("/images")
    @Timed
    public List<ImagesDTO> getAllImages() {
        log.debug("REST request to get all Images");
        return imagesService.findAll();
    }

    /**
     * GET /images/:id : get the "id" images.
     *
     * @param id the id of the imagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * imagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}")
    @Timed
    public ResponseEntity<ImagesDTO> getImages(@PathVariable Long id) {
        log.debug("REST request to get Images : {}", id);
        ImagesDTO imagesDTO = imagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imagesDTO));
    }

    /**
     * DELETE /images/:id : delete the "id" images.
     *
     * @param id the id of the imagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/images/{id}")
    @Timed
    public ResponseEntity<Void> deleteImages(@PathVariable Long id) {
        log.debug("REST request to delete Images : {}", id);
        imagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
