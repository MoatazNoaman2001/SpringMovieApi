package com.movieapp.movieApi.server;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileServices {

    String uploadFile(String path , MultipartFile file) throws IOException;

    InputStream getResourceFile(String path , String name) throws FileNotFoundException;
}
