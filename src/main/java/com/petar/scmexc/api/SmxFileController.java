package com.marco.scmexc.api;

import com.marco.scmexc.models.domain.SmxFile;
import com.marco.scmexc.models.response.SmxFileResponse;
import com.marco.scmexc.services.SmxFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/files")
public class SmxFileController {


    private final SmxFileService service;

    public SmxFileController(SmxFileService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SmxFileResponse>> getFiles() {
        List<SmxFileResponse> files = service.getAllFiles().map(smxFile -> {
            String url = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/getFile/")
                    .path(smxFile.getId().toString())
                    .toUriString();

            return  new SmxFileResponse(smxFile.getFileName(),url,smxFile.getItem().getType(), smxFile.getData().length,smxFile.getDateUploaded());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("getFile/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        SmxFile file = service.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getData());
    }


}
