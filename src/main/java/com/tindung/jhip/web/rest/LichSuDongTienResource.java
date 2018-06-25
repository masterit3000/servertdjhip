package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.LichSuDongTienService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LichSuDongTien.
 */
@RestController
@RequestMapping("/api")
public class LichSuDongTienResource {

    private final Logger log = LoggerFactory.getLogger(LichSuDongTienResource.class);

    private static final String ENTITY_NAME = "lichSuDongTien";

    private final LichSuDongTienService lichSuDongTienService;

    public LichSuDongTienResource(LichSuDongTienService lichSuDongTienService) {
        this.lichSuDongTienService = lichSuDongTienService;
    }

    /**
     * POST  /lich-su-dong-tiens : Create a new lichSuDongTien.
     *
     * @param lichSuDongTienDTO the lichSuDongTienDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lichSuDongTienDTO, or with status 400 (Bad Request) if the lichSuDongTien has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lich-su-dong-tiens")
    @Timed
    public ResponseEntity<LichSuDongTienDTO> createLichSuDongTien(@Valid @RequestBody LichSuDongTienDTO lichSuDongTienDTO) throws URISyntaxException {
        log.debug("REST request to save LichSuDongTien : {}", lichSuDongTienDTO);
        if (lichSuDongTienDTO.getId() != null) {
            throw new BadRequestAlertException("A new lichSuDongTien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LichSuDongTienDTO result = lichSuDongTienService.save(lichSuDongTienDTO);
        return ResponseEntity.created(new URI("/api/lich-su-dong-tiens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lich-su-dong-tiens : Updates an existing lichSuDongTien.
     *
     * @param lichSuDongTienDTO the lichSuDongTienDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lichSuDongTienDTO,
     * or with status 400 (Bad Request) if the lichSuDongTienDTO is not valid,
     * or with status 500 (Internal Server Error) if the lichSuDongTienDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lich-su-dong-tiens")
    @Timed
    public ResponseEntity<LichSuDongTienDTO> updateLichSuDongTien(@Valid @RequestBody LichSuDongTienDTO lichSuDongTienDTO) throws URISyntaxException {
        log.debug("REST request to update LichSuDongTien : {}", lichSuDongTienDTO);
        if (lichSuDongTienDTO.getId() == null) {
            return createLichSuDongTien(lichSuDongTienDTO);
        }
        LichSuDongTienDTO result = lichSuDongTienService.save(lichSuDongTienDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lichSuDongTienDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lich-su-dong-tiens : get all the lichSuDongTiens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lichSuDongTiens in body
     */
    @GetMapping("/lich-su-dong-tiens")
    @Timed
    public List<LichSuDongTienDTO> getAllLichSuDongTiens() {
        log.debug("REST request to get all LichSuDongTiens");
        return lichSuDongTienService.findAll();
        }

    /**
     * GET  /lich-su-dong-tiens/:id : get the "id" lichSuDongTien.
     *
     * @param id the id of the lichSuDongTienDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lichSuDongTienDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lich-su-dong-tiens/{id}")
    @Timed
    public ResponseEntity<LichSuDongTienDTO> getLichSuDongTien(@PathVariable Long id) {
        log.debug("REST request to get LichSuDongTien : {}", id);
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lichSuDongTienDTO));
    }

    /**
     * DELETE  /lich-su-dong-tiens/:id : delete the "id" lichSuDongTien.
     *
     * @param id the id of the lichSuDongTienDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lich-su-dong-tiens/{id}")
    @Timed
    public ResponseEntity<Void> deleteLichSuDongTien(@PathVariable Long id) {
        log.debug("REST request to delete LichSuDongTien : {}", id);
        lichSuDongTienService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
