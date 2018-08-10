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
@RequestMapping("/api/upload")
@Controller
public class UploadResource {

    private final StorageService storageService;
    private final AnhKhachHangService anhKhachHangService;

    public UploadResource(StorageService storageService, AnhKhachHangService anhKhachHangService) {
        this.storageService = storageService;
        this.anhKhachHangService = anhKhachHangService;
    }

    List<String> files = new ArrayList<String>();

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/khachhang/{id}"
    )
    public ResponseEntity<String> handleFileUpload(@RequestParam("myfile") MultipartFile[] file, @PathVariable(name = "id") long id) {
//        System.out.println("uploadddddddddddddddd" + file.getOriginalFilename());
        if (file != null) {

            StringBuilder message = new StringBuilder();
            System.out.println("filelleght: " + file.length);
            for (MultipartFile multipartFile : file) {
                try {
                    byte[] readData;//= new byte[(int) multipartFile.getSize()];
//                    storageService.store(multipartFile, id);
                    readData = multipartFile.getBytes();
                    System.out.println("magnbyte" + readData.length);
                    message.append(multipartFile.getOriginalFilename()).append("|");
                    AnhKhachHangDTO anhKhachHangDTO = new AnhKhachHangDTO();
                    anhKhachHangDTO.setKhachHangId(id);
                    anhKhachHangDTO.setUrl(multipartFile.getOriginalFilename());
                    anhKhachHangDTO.setFile(readData);
                    AnhKhachHangDTO save = anhKhachHangService.save(anhKhachHangDTO);

                } catch (Exception e) {
                    e.printStackTrace();
                    message.append("FAIL: ").append(multipartFile.getOriginalFilename()).append("|");
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(message.toString());
        }
        String message = "FAIL to upload " + file.length + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    }

//    @GetMapping("/getallfiles")
//    public ResponseEntity<List<String>> getListFiles(Model model) {
//        List<String> fileNames = files
//                .stream().map(fileName -> MvcUriComponentsBuilder
//                .fromMethodName(UploadResource.class, "getFile", fileName).build().toString())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok().body(fileNames);
//    }
    @GetMapping("/anh-khach-hang/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Long id) {
        AnhKhachHangDTO findOne = anhKhachHangService.findOne(id);
        Resource file = new ByteArrayResource(findOne.getFile());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + findOne.getUrl() + "\"").body(file);
    }
}
