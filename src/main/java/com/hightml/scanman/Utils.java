package com.hightml.scanman;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 01/03/15
 * Time: 11:33
 * <p>
 * Copyright by HighTML.
 */
public class Utils {
    public static LocalDateTime getCreationTime(Path path) throws IOException {
        BasicFileAttributes attr = java.nio.file.Files.readAttributes(path, BasicFileAttributes.class);
        return LocalDateTime.ofInstant(attr.creationTime().toInstant(), ZoneId.of("Europe/Amsterdam"));
    }
}
