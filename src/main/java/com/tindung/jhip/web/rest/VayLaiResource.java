package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.service.LichSuThaoTacHopDongService;
import com.tindung.jhip.service.NhanVienService;
import com.tindung.jhip.service.VayLaiService;
import com.tindung.jhip.service.dto.LichSuDongTienDTO;
import com.tindung.jhip.service.dto.LichSuThaoTacHopDongDTO;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.VayLaiDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VayLai.
 */
@RestController
@RequestMapping("/api")
public class VayLaiResource {

    private final Logger log = LoggerFactory.getLogger(VayLaiResource.class);

    private static final String ENTITY_NAME = "vayLai";

    private final VayLaiService vayLaiService;
    private final NhanVienService nhanVienService;
    private final LichSuThaoTacHopDongService lichSuThaoTacHopDongService;

    public VayLaiResource(VayLaiService vayLaiService, NhanVienService nhanVienService, LichSuThaoTacHopDongService lichSuThaoTacHopDongService) {
        this.vayLaiService = vayLaiService;
        this.nhanVienService = nhanVienService;
        this.lichSuThaoTacHopDongService = lichSuThaoTacHopDongService;
    }

    /**
     * POST /vay-lais : Create a new vayLai.
     *
     * @param vayLaiDTO the vayLaiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new vayLaiDTO, or with status 400 (Bad Request) if the vayLai has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vay-lais")
    @Timed
    public ResponseEntity<VayLaiDTO> createVayLai(@RequestBody VayLaiDTO vayLaiDTO) throws URISyntaxException {
        log.debug("REST request to save VayLai : {}", vayLaiDTO);
        if (vayLaiDTO.getId() != null) {
            throw new BadRequestAlertException("A new vayLai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VayLaiDTO result = vayLaiService.save(vayLaiDTO);

        //
        //
        return ResponseEntity.created(new URI("/api/vay-lais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PostMapping("/them-bot-vay-lais/{id}/{mahopdong}")
    @Timed
    public ResponseEntity<VayLaiDTO> vay(@RequestBody VayLaiDTO vayLaiDTO, @PathVariable(name="id") Long id,@PathVariable(name = "mahopdong") String mahopdong) throws URISyntaxException {
        log.debug("REST request to save VayLai : {}", vayLaiDTO);
        if (vayLaiDTO.getId() != null) {
            throw new BadRequestAlertException("A new vayLai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VayLaiDTO result = vayLaiService.vay(vayLaiDTO, id,mahopdong);

        //
        return ResponseEntity.created(new URI("/api/them-bot-vay-lais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /vay-lais : Updates an existing vayLai.
     *
     * @param vayLaiDTO the vayLaiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * vayLaiDTO, or with status 400 (Bad Request) if the vayLaiDTO is not
     * valid, or with status 500 (Internal Server Error) if the vayLaiDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vay-lais")
    @Timed
    public ResponseEntity<VayLaiDTO> updateVayLai(@Valid @RequestBody VayLaiDTO vayLaiDTO) throws URISyntaxException {
        log.debug("REST request to update VayLai : {}", vayLaiDTO);
        if (vayLaiDTO.getId() == null) {
            return createVayLai(vayLaiDTO);
        }
        VayLaiDTO result = vayLaiService.save(vayLaiDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vayLaiDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /vay-lais : get all the vayLais.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vayLais
     * in body
     */
    @GetMapping("/vay-lais")
    @Timed
    public List<VayLaiDTO> getAllVayLais() {
        log.debug("REST request to get all VayLais");
        return vayLaiService.findAll();
    }

    @GetMapping("/vay-lais-by-cuaHang/{id}")
    @Timed
    public List<VayLaiDTO> getAllVayLaiByCuaHang(@PathVariable(name = "id") Long id) {
        log.debug("REST request to get all VayLais");
        return vayLaiService.findAllByCuaHang(id);
    }

    @GetMapping("/vay-lais-by-nhanvien/{id}")
    @Timed
    public List<VayLaiDTO> getAllVayLaiByNhanVien(@PathVariable Long id) {
        log.debug("REST request to get all VayLais");
        return vayLaiService.findByNhanVien(id);
    }

    @GetMapping("/tim-vay-lais-by-ten-cmnd/{key}")
    @Timed
    public List<VayLaiDTO> timVayLaisByTenCMND(@PathVariable(name = "key") String key) {
        log.debug("REST request to get all KhachHangs");
        return vayLaiService.findByNameOrCMND(key);
    }

    /**
     * GET /vay-lais/:id : get the "id" vayLai.
     *
     * @param id the id of the vayLaiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * vayLaiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vay-lais/{id}")
    @Timed
    public ResponseEntity<VayLaiDTO> getVayLai(@PathVariable Long id) {
        log.debug("REST request to get VayLai : {}", id);
        VayLaiDTO vayLaiDTO = vayLaiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vayLaiDTO));
    }

    /**
     * DELETE /vay-lais/:id : delete the "id" vayLai.
     *
     * @param id the id of the vayLaiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vay-lais/{id}")
    @Timed
    public ResponseEntity<Void> deleteVayLai(@PathVariable Long id) {
        log.debug("REST request to delete VayLai : {}", id);
        vayLaiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/bao-cao-vay-lais/{start}/{end}/{chon}")
    @Timed
    public List<VayLaiDTO> baoCao(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "chon") Integer vayThemTraGoc) {
        log.debug("REST request to get all BatHos");
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        return vayLaiService.baoCao(timeStart, timeEnd, vayThemTraGoc);
    }

    @GetMapping("/bao-cao-vay-lais-nhanvien/{start}/{end}/{id}/{chon}")
    @Timed
    public List<VayLaiDTO> baoCao(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "id") Long id, @PathVariable(name = "chon") Integer vayThemTraGoc) {
        log.debug("REST request to get all BatHos");
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        return vayLaiService.baoCao(timeStart, timeEnd, id, vayThemTraGoc);
    }

    @GetMapping("/vay-lais-by-hopdong/{id}")
    @Timed
    public ResponseEntity<VayLaiDTO> getVayLaiByHopDong(@PathVariable Long id) {
        log.debug("REST request to get VayLai : {}", id);
        VayLaiDTO vayLaiDTO = vayLaiService.findByHopDong(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vayLaiDTO));
    }

    @GetMapping("/vay-them-tra-bot/{id}")
    @Timed
    public ResponseEntity<String> quanLyVon(@PathVariable Long id) {
        log.debug("REST request to get all BatHos");
        String result = vayLaiService.tienVayThemTraBot(id).toString();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(result));
    }
}


