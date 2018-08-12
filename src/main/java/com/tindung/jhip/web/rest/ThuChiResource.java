package com.tindung.jhip.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tindung.jhip.domain.enumeration.THUCHI;
import com.tindung.jhip.service.ThuChiService;
import com.tindung.jhip.web.rest.errors.BadRequestAlertException;
import com.tindung.jhip.web.rest.util.HeaderUtil;
import com.tindung.jhip.service.dto.ThuChiDTO;
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
 * REST controller for managing ThuChi.
 */
@RestController
@RequestMapping("/api")
public class ThuChiResource {

    private final Logger log = LoggerFactory.getLogger(ThuChiResource.class);

    private static final String ENTITY_NAME = "thuChi";

    private final ThuChiService thuChiService;

    public ThuChiResource(ThuChiService thuChiService) {
        this.thuChiService = thuChiService;
    }

    /**
     * POST /thu-chis : Create a new thuChi.
     *
     * @param thuChiDTO the thuChiDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new thuChiDTO, or with status 400 (Bad Request) if the thuChi has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/thu-chis")
    @Timed
    public ResponseEntity<ThuChiDTO> createThuChi(@Valid @RequestBody ThuChiDTO thuChiDTO) throws URISyntaxException {
        //   thuChiDTO.setThoiGian();
        log.debug("REST request to save ThuChi : {}", thuChiDTO);
        if (thuChiDTO.getId() != null) {
            throw new BadRequestAlertException("A new thuChi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThuChiDTO result = thuChiService.save(thuChiDTO);
        return ResponseEntity.created(new URI("/api/thu-chis/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /thu-chis : Updates an existing thuChi.
     *
     * @param thuChiDTO the thuChiDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * thuChiDTO, or with status 400 (Bad Request) if the thuChiDTO is not
     * valid, or with status 500 (Internal Server Error) if the thuChiDTO
     * couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thu-chis")
    @Timed
    public ResponseEntity<ThuChiDTO> updateThuChi(@Valid @RequestBody ThuChiDTO thuChiDTO) throws URISyntaxException {
        log.debug("REST request to update ThuChi : {}", thuChiDTO);
        if (thuChiDTO.getId() == null) {
            return createThuChi(thuChiDTO);
        }
        ThuChiDTO result = thuChiService.save(thuChiDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thuChiDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET /thu-chis : get all the thuChis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of thuChis
     * in body
     */
    @GetMapping("/thu-chis")
    @Timed
    public List<ThuChiDTO> getAllThuChis() {
        log.debug("REST request to get all ThuChis");
        return thuChiService.findAll();
    }

    //
    @GetMapping("/thu-chis/loai/{thuchi}")
    @Timed
    public List<ThuChiDTO> getAllThuTheoLoai(@PathVariable String thuchi) {
        log.debug("REST request to get all ThuChis");

        THUCHI loai = THUCHI.THU;
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
        switch (thuchi) {
            case "0":
                loai = THUCHI.THU;
                break;
            case "1":
                loai = THUCHI.CHI;
                break;
            case "2":
                loai = THUCHI.GOPVON;
                break;
            case "3":
                loai = THUCHI.RUTVON;
                break;

        }
        return thuChiService.findAllThuTheoLoai(loai);
    }

    /**
     * GET /thu-chis : get all the thuChis.
     *
     * @param start
     * @param end
     * @return Hàm sư dung để tìm kiếm thu chi theo thời gian và loại thu chi
     * CÁc hàm tìm kiếm (lấy dữ liệu) cố gắng để kiểu get, tham số truyefn lên
     * dưới dạng url =>@GetMapping("/thu-chis/{start}/{end}/{loai}") link client
     * gui
     */
    @GetMapping("/thu-chis/{start}/{end}/{loai}")
    @Timed
    public List<ThuChiDTO> getAllThuChisByTime(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "loai") String thuchi) {
        log.debug("REST request to get all ThuChis");//ghi log thôi
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
//2 dòng trên để convert thời gian từ client gửi lên thành dạng ZoneDateTime, client gửi lên dạng 2018 07 15. plusSeconds(86399);-> đê tạo tahfh thời diểm 23h59m59s của ngày đó

        THUCHI loai = THUCHI.THU;
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
        switch (thuchi) {
            case "0":
                loai = THUCHI.THU;
                break;
            case "1":
                loai = THUCHI.CHI;
                break;
            case "2":
                loai = THUCHI.GOPVON;
                break;
            case "3":
                loai = THUCHI.RUTVON;
                break;

        }
        return thuChiService.findByTime(timeStart, timeEnd, loai);//goi service chạy
    }

    @GetMapping("/thu-chis-bao-cao-nhanvien/{start}/{end}/{id}")
    @Timed
    public List<ThuChiDTO> baoCaoNV(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end, @PathVariable(name = "id") Long id) {
        log.debug("REST request to get all ThuChis");//ghi log thôi
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
//2 dòng trên để convert thời gian từ client gửi lên thành dạng ZoneDateTime, client gửi lên dạng 2018 07 15. plusSeconds(86399);-> đê tạo tahfh thời diểm 23h59m59s của ngày đó

        THUCHI loai = THUCHI.THU;
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
//        switch (thuchi) {
//            case "0":
//                loai = THUCHI.THU;
//                break;
//            case "1":
//                loai = THUCHI.CHI;
//                break;
//            case "2":
//                loai = THUCHI.GOPVON;
//                break;
//            case "3":
//                loai = THUCHI.RUTVON;
//                break;
//
//        }
        return thuChiService.baoCao(timeStart, timeEnd, id);//goi service chạy
    }

    @GetMapping("/thu-chis-bao-cao/{start}/{end}")
    @Timed
    public List<ThuChiDTO> baoCao(@PathVariable(name = "start") String start, @PathVariable(name = "end") String end) {
        log.debug("REST request to get all ThuChis");//ghi log thôi
        ZonedDateTime timeStart = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime timeEnd = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy MM dd")).atStartOfDay(ZoneId.systemDefault()).plusSeconds(86399);
//2 dòng trên để convert thời gian từ client gửi lên thành dạng ZoneDateTime, client gửi lên dạng 2018 07 15. plusSeconds(86399);-> đê tạo tahfh thời diểm 23h59m59s của ngày đó

        THUCHI loai = THUCHI.THU;
        //doạn này convert loai thu chi dạng text sang dạng enum THUCHI
//        switch (thuchi) {
//            case "0":
//                loai = THUCHI.THU;
//                break;
//            case "1":
//                loai = THUCHI.CHI;
//                break;
//            case "2":
//                loai = THUCHI.GOPVON;
//                break;
//            case "3":
//                loai = THUCHI.RUTVON;
//                break;
//
//        }
        return thuChiService.findByTime(timeStart, timeEnd);//goi service chạy
    }

    /**
     * GET /thu-chis/:id : get the "id" thuChi.
     *
     * @param id the id of the thuChiDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * thuChiDTO, or with status 404 (Not Found)
     */
    @GetMapping("/thu-chis/{id}")
    @Timed
    public ResponseEntity<ThuChiDTO> getThuChi(@PathVariable Long id) {
        log.debug("REST request to get ThuChi : {}", id);
        ThuChiDTO thuChiDTO = thuChiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(thuChiDTO));
    }

    /**
     * DELETE /thu-chis/:id : delete the "id" thuChi.
     *
     * @param id the id of the thuChiDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/thu-chis/{id}")
    @Timed
    public ResponseEntity<Void> deleteThuChi(@PathVariable Long id) {
        log.debug("REST request to delete ThuChi : {}", id);
        thuChiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
