/**
 * 
 */
package com.hightml.scanman.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * @author Marcel
 *
 */
@RestController
@RequestMapping(value="/files", produces=APPLICATION_JSON_VALUE)
@Slf4j
public class FileController {


    @Value("${scan.image.files.root}")
    String IMAGEROOT;

    @ResponseBody
    @RequestMapping(value="/{fileName:.+}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFile(@PathVariable("fileName") String fileName) {
        Path path = FileSystems.getDefault().getPath(IMAGEROOT, fileName);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new ResourceNotFoundException(fileName);
        }
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String fileName) {
            super(fileName + " not found...");
        }
    }



}
