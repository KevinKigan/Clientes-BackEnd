package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface UploadFileServiceInterface {

    Resource load(String nameImage) throws MalformedURLException;

    String copy(MultipartFile file) throws IOException;

    boolean delete(String nameImage);

    Path getPath(String nameImage);
}
