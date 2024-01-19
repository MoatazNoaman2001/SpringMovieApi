package com.movieapp.movieApi.Controllers;


import com.movieapp.movieApi.server.FileServices;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileServices fileServices;

    public FileController(FileServices fileServices) {
        this.fileServices = fileServices;
    }

    @Value("${project.poster}")
    String path;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile multipartFile) throws IOException {
        String uploadFileName= fileServices.uploadFile(path , multipartFile);
        return ResponseEntity.ok("file uploaded: " + uploadFileName );
    }

    @GetMapping("/{filename}")
    public void getFileHandler(@PathVariable String filename , HttpServletResponse response) throws IOException {
        InputStream stream = fileServices.getResourceFile(path , filename);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(stream , response.getOutputStream());
    }

}
