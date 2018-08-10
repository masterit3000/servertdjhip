package com.tindung.jhip.web.rest;

import com.tindung.jhip.service.AnhKhachHangService;
import com.tindung.jhip.service.StorageService;
import com.tindung.jhip.service.dto.AnhKhachHangDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("download")
@Controller
public class DownloadResource {

    private final StorageService storageService;
    private final AnhKhachHangService anhKhachHangService;

    public DownloadResource(StorageService storageService, AnhKhachHangService anhKhachHangService) {
        this.storageService = storageService;
        this.anhKhachHangService = anhKhachHangService;
    }

    @GetMapping(value = {"/anh-khach-hang/{id}"}, produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        AnhKhachHangDTO findOne = anhKhachHangService.findOne(id);
        System.out.println(findOne.getFile().length);
        Resource file = new ByteArrayResource(findOne.getFile());
        return ResponseEntity.ok().body(file);
        //                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + findOne.getUrl() + "\"")
    }
}
