package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.domain.enumeration.DONGTIEN;
import com.tindung.jhip.domain.enumeration.LOAIHOPDONG;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
     * POST /lich-su-dong-tiens : Create a new lichSuDongTien.
     *
     * @param lichSuDongTienDTO the lichSuDongTienDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new lichSuDongTienDTO, or with status 400 (Bad Request) if the
     * lichSuDongTien has already an ID
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
     * PUT /lich-su-dong-tiens : Updates an existing lichSuDongTien.
     *
     * @param lichSuDongTienDTO the lichSuDongTienDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * lichSuDongTienDTO, or with status 400 (Bad Request) if the
     * lichSuDongTienDTO is not valid, or with status 500 (Internal Server
     * Error) if the lichSuDongTienDTO couldn't be updated
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

    @GetMapping("/lich-su-dong-tiens/dongtien/{id}/{dongtien}")
    @Timed
    public ResponseEntity<Void> setDongTien(@PathVariable Long id, @PathVariable(name = "dongtien") String dongtien) throws URISyntaxException {
        log.debug("REST request to setDongtien LichSuDongTien : {}");
        DONGTIEN loaidongtien = DONGTIEN.TRAGOC;
        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        lichSuDongTienService.setDongTien(id, loaidongtien);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /lich-su-dong-tiens : get all the lichSuDongTiens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * lichSuDongTiens in body
     */
    @GetMapping("/lich-su-dong-tiens")
    @Timed
    public List<LichSuDongTienDTO> getAllLichSuDongTiens() {
        log.debug("REST request to get all LichSuDongTiens");
        return lichSuDongTienService.findAll();
    }

    /**
     * GET /lich-su-dong-tiens/:id : get the "id" lichSuDongTien.
     *
     * @param id the id of the lichSuDongTienDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * lichSuDongTienDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lich-su-dong-tiens/{id}")
    @Timed
    public ResponseEntity<LichSuDongTienDTO> getLichSuDongTien(@PathVariable Long id) {
        log.debug("REST request to get LichSuDongTien : {}", id);
        LichSuDongTienDTO lichSuDongTienDTO = lichSuDongTienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lichSuDongTienDTO));
    }

    /**
     * DELETE /lich-su-dong-tiens/:id : delete the "id" lichSuDongTien.
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

    @GetMapping("/dong-hop-dong/{id}")
    @Timed
    public ResponseEntity<Void> dongHopDong(@PathVariable Long id) {
        log.debug("REST request to delete LichSuDongTien : {}", id);
        lichSuDongTienService.dongHopDong(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/lich-su-dong-tiens/lichsudongtien/{id}")
    @Timed
    public List<LichSuDongTienDTO> getLichSuDongTienByHopDong(@PathVariable Long id) {
        log.debug("REST request to get LichSuDongTien by HopDong: {}", id);
        return lichSuDongTienService.findByHopDong(id);
    }

    @GetMapping("/bao-cao-lich-su-dong-tiens/{dongtien}/{loaihopdong}/{start}/{end}")
    @Timed
    public List<LichSuDongTienDTO> baoCao(@PathVariable(name = "dongtien") String dongtien, @PathVariable(name = "loaihopdong") String loaihopdong, @PathVariable(name = "start") String start, @PathVariable(name = "end") String end) {
        log.debug("REST request to get baoCao LichSuDongTien: {}");
        LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;
        DONGTIEN loaidongtien = DONGTIEN.TRAGOC;
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }

        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.baoCao(loaidongtien, loai, timeStart, timeEnd);
    }

    @GetMapping("/bao-cao-lich-su-dong-tiens-nhanvien/{dongtien}/{loaihopdong}/{start}/{end}/{id}")
    @Timed
    public List<LichSuDongTienDTO> baoCaoNV(@PathVariable(name = "dongtien") String dongtien, @PathVariable(name = "loaihopdong") String loaihopdong, @PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get baoCao LichSuDongTien: {}");
        LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;
        DONGTIEN loaidongtien = DONGTIEN.DADONG;
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }

        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.baoCaoNV(loaidongtien, loai, timeStart, timeEnd, id);
    }

    @GetMapping("/lich-su-dong-tiens-tracham/{dongtien}/{loaihopdong}")
    @Timed
    public List<LichSuDongTienDTO> lichSuTraCham(@PathVariable(name = "dongtien") String dongtien, @PathVariable(name = "loaihopdong") String loaihopdong) {
        log.debug("REST request to get baoCao LichSuDongTien: {}");
        LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;

        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }
        DONGTIEN loaidongtien = DONGTIEN.DADONG;
        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.lichSuTraCham(loaidongtien, loai);
    }

    @GetMapping("/lich-su-dong-tiens-homnay/{dongtien}/{loaihopdong}")
    @Timed
    public List<LichSuDongTienDTO> lichSuTraHomNay(@PathVariable(name = "dongtien") String dongtien, @PathVariable(name = "loaihopdong") String loaihopdong) {
        log.debug("REST request to get baoCao LichSuDongTien: {}");
        LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;
        DONGTIEN loaidongtien = DONGTIEN.DADONG;
        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }

        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.lichSuTraHomNay(loaidongtien, loai);
    }

    @GetMapping("/lich-su-dong-tiens-trangthai/{trangthai}/{id}")
    @Timed
    public List<LichSuDongTienDTO> getAllLichSuDongTiensByTrangThai(@PathVariable(name = "trangthai") String trangthai, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get all LichSuDongTiens");
        DONGTIEN loaidongtien = DONGTIEN.DADONG;
        switch (trangthai) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.findByTrangThai(loaidongtien, id);
    }

    @GetMapping("/bao-cao-lich-su-dong-tiens/{dongtien}/{loaihopdong}/{start}/{end}/{id}")
    @Timed
    public List<LichSuDongTienDTO> baoCaoKeToan(@PathVariable(name = "dongtien") String dongtien, @PathVariable(name = "loaihopdong") String loaihopdong, @PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get baoCao LichSuDongTien: {}");
        LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;
        DONGTIEN loaidongtien = DONGTIEN.TRAGOC;
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }

        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.baoCaoKeToan(loaidongtien, loai, timeStart, timeEnd, id);
    }

    @GetMapping("/lich-su-dong-tiens-tra-cham-by-hop-dong/{dongtien}/{loaihopdong}/{idhopdong}")
    @Timed
    public List<LichSuDongTienDTO> findLSTraChamByHopDong(@PathVariable(name = "dongtien") String dongtien, @PathVariable(name = "loaihopdong") String loaihopdong,@PathVariable (name="idhopdong")Long id) {
        log.debug("REST request to get LichSuDongTien : {}", id);
                LOAIHOPDONG loai = LOAIHOPDONG.VAYLAI;

        switch (loaihopdong) {
            case "0":
                loai = LOAIHOPDONG.VAYLAI;
                break;
            case "1":
                loai = LOAIHOPDONG.BATHO;
                break;
        }
        DONGTIEN loaidongtien = DONGTIEN.DADONG;
        switch (dongtien) {
            case "0":
                loaidongtien = DONGTIEN.CHUADONG;
                break;
            case "1":
                loaidongtien = DONGTIEN.DADONG;
                break;
            case "2":
                loaidongtien = DONGTIEN.TRAGOC;
                break;

        }
        return lichSuDongTienService.findLichSuTraChamByHopDong(loaidongtien, loai, id);
    }

}
