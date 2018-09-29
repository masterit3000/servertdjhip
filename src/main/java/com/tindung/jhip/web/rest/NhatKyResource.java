package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.NhatKyService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.NhatKyDTO;
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
 * REST controller for managing NhatKy.
 */
@RestController
@RequestMapping("/api")
public class NhatKyResource {

    private final Logger log = LoggerFactory.getLogger(NhatKyResource.class);

    private static final String ENTITY_NAME = "nhatKy";

    private final NhatKyService nhatKyService;

    public NhatKyResource(NhatKyService nhatKyService) {
        this.nhatKyService = nhatKyService;
    }

    /**
     * POST /nhat-kies : Create a new nhatKy.
     *
     * @param nhatKyDTO the nhatKyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new nhatKyDTO, or with status 400 (Bad Request) if the nhatKy has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhat-kies")
    @Timed
    public ResponseEntity<NhatKyDTO> createNhatKy(@Valid @RequestBody NhatKyDTO nhatKyDTO) throws URISyntaxException {
        log.debug("REST request to save NhatKy : {}", nhatKyDTO);
        if (nhatKyDTO.getId() != null) {
            throw new BadRequestAlertException("A new nhatKy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhatKyDTO result = nhatKyService.save(nhatKyDTO);
        return ResponseEntity.created(new URI("/api/nhat-kies/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /nhat-kies : Updates an existing nhatKy.
     *
     * @param nhatKyDTO the nhatKyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * nhatKyDTO, or with status 400 (Bad Request) if the nhatKyDTO is not
     * valid, or with status 500 (Internal Server Error) if the nhatKyDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhat-kies")
    @Timed
    public ResponseEntity<NhatKyDTO> updateNhatKy(@Valid @RequestBody NhatKyDTO nhatKyDTO) throws URISyntaxException {
        log.debug("REST request to update NhatKy : {}", nhatKyDTO);
        if (nhatKyDTO.getId() == null) {
            return createNhatKy(nhatKyDTO);
        }
        NhatKyDTO result = nhatKyService.save(nhatKyDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhatKyDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /nhat-kies : get all the nhatKies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhatKies
     * in body
     */
    @GetMapping("/nhat-kies")
    @Timed
    public List<NhatKyDTO> getAllNhatKies() {
        log.debug("REST request to get all NhatKies");
        return nhatKyService.findAll();
    }

    @GetMapping("/nhat-kies-by-cua-hang/{id}")
    @Timed
    public List<NhatKyDTO> getAllByCuaHang(@PathVariable Long id) {
        log.debug("REST request to get all NhatKies");
        return nhatKyService.findAllByCuaHangId(id);
    }

    @GetMapping("/find-nhat-kies/{key}")
    @Timed
    public List<NhatKyDTO> getAllNhatKiesByNoiDungorNhanVien(@PathVariable String key) {
        log.debug("REST request to get all NhatKies");
        return nhatKyService.findAllByNoiDungorNhanVien(key);
    }

    @GetMapping("/find-nhat-kies/{key}/{id}")
    @Timed
    public List<NhatKyDTO> getAllNhatKiesByCuaHang(@PathVariable(name = "key") String key, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get all NhatKies");
        return nhatKyService.findNhatKyTheoCuaHang(key, id);
    }

    /**
     * GET /nhat-kies/:id : get the "id" nhatKy.
     *
     * @param id the id of the nhatKyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * nhatKyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/nhat-kies/{id}")
    @Timed
    public ResponseEntity<NhatKyDTO> getNhatKy(@PathVariable Long id) {
        log.debug("REST request to get NhatKy : {}", id);
        NhatKyDTO nhatKyDTO = nhatKyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(nhatKyDTO));
    }

    /**
     * DELETE /nhat-kies/:id : delete the "id" nhatKy.
     *
     * @param id the id of the nhatKyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhat-kies/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhatKy(@PathVariable Long id) {
        log.debug("REST request to delete NhatKy : {}", id);
        nhatKyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
