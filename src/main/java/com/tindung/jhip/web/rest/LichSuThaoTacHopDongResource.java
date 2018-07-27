package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
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
 * REST controller for managing LichSuThaoTacHopDong.
 */
@RestController
@RequestMapping("/api")
public class LichSuThaoTacHopDongResource {

    private final Logger log = LoggerFactory.getLogger(LichSuThaoTacHopDongResource.class);

    private static final String ENTITY_NAME = "lichSuThaoTacHopDong";

    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;

    public LichSuThaoTacHopDongResource(LichSuThaoTacHopDongService lichSuThaoTacHopDongService) {
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
    }

    /**
     * POST /lich-su-thao-tac-hop-dongs : Create a new lichSuThaoTacHopDong.
     *
     * @param lichSuThaoTacHopDongDTO the lichSuThaoTacHopDongDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new lichSuThaoTacHopDongDTO, or with status 400 (Bad Request) if the
     * lichSuThaoTacHopDong has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lich-su-thao-tac-hop-dongs")
    @Timed
    public ResponseEntity<LichSuThaoTacHopDongDTO> createLichSuThaoTacHopDong(@Valid @RequestBody LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO) throws URISyntaxException {
        log.debug("REST request to save LichSuThaoTacHopDong : {}", lichSuThaoTacHopDongDTO);
        if (lichSuThaoTacHopDongDTO.getId() != null) {
            throw new BadRequestAlertException("A new lichSuThaoTacHopDong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LichSuThaoTacHopDongDTO result = lichSuThaoTacHopDongService.save(lichSuThaoTacHopDongDTO);
        return ResponseEntity.created(new URI("/api/lich-su-thao-tac-hop-dongs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /lich-su-thao-tac-hop-dongs : Updates an existing
     * lichSuThaoTacHopDong.
     *
     * @param lichSuThaoTacHopDongDTO the lichSuThaoTacHopDongDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * lichSuThaoTacHopDongDTO, or with status 400 (Bad Request) if the
     * lichSuThaoTacHopDongDTO is not valid, or with status 500 (Internal Server
     * Error) if the lichSuThaoTacHopDongDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lich-su-thao-tac-hop-dongs")
    @Timed
    public ResponseEntity<LichSuThaoTacHopDongDTO> updateLichSuThaoTacHopDong(@Valid @RequestBody LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO) throws URISyntaxException {
        log.debug("REST request to update LichSuThaoTacHopDong : {}", lichSuThaoTacHopDongDTO);
        if (lichSuThaoTacHopDongDTO.getId() == null) {
            return createLichSuThaoTacHopDong(lichSuThaoTacHopDongDTO);
        }
        LichSuThaoTacHopDongDTO result = lichSuThaoTacHopDongService.save(lichSuThaoTacHopDongDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lichSuThaoTacHopDongDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /lich-su-thao-tac-hop-dongs : get all the lichSuThaoTacHopDongs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * lichSuThaoTacHopDongs in body
     */
    @GetMapping("/lich-su-thao-tac-hop-dongs")
    @Timed
    public List<LichSuThaoTacHopDongDTO> getAllLichSuThaoTacHopDongs() {
        log.debug("REST request to get all LichSuThaoTacHopDongs");
        return lichSuThaoTacHopDongService.findAll();
    }

    /**
     * GET /lich-su-thao-tac-hop-dongs/:id : get the "id" lichSuThaoTacHopDong.
     *
     * @param id the id of the lichSuThaoTacHopDongDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * lichSuThaoTacHopDongDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lich-su-thao-tac-hop-dongs/{id}")
    @Timed
    public ResponseEntity<LichSuThaoTacHopDongDTO> getLichSuThaoTacHopDong(@PathVariable Long id) {
        log.debug("REST request to get LichSuThaoTacHopDong : {}", id);
        LichSuThaoTacHopDongDTO lichSuThaoTacHopDongDTO = lichSuThaoTacHopDongService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lichSuThaoTacHopDongDTO));
    }

    /**
     * DELETE /lich-su-thao-tac-hop-dongs/:id : delete the "id"
     * lichSuThaoTacHopDong.
     *
     * @param id the id of the lichSuThaoTacHopDongDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lich-su-thao-tac-hop-dongs/{id}")
    @Timed
    public ResponseEntity<Void> deleteLichSuThaoTacHopDong(@PathVariable Long id) {
        log.debug("REST request to delete LichSuThaoTacHopDong : {}", id);
        lichSuThaoTacHopDongService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/lich-su-thao-tac-hop-dongs/lichsuthaotac/{id}")
    @Timed
    public List<LichSuThaoTacHopDongDTO> getLichSuThaoTacByHopDong(@PathVariable Long id) {
        log.debug("REST request to get LichSuThaoTac by HopDong: {}", id);
        return lichSuThaoTacHopDongService.findByHopDong(id);
    }

}
