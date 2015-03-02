package com.hightml.scanman.processing;

import com.google.common.hash.Hashing;
import com.hightml.scanman.Utils;
import com.hightml.scanman.jpa.ScanRepository;
import com.hightml.scanman.value.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

import static com.hightml.scanman.Application.*;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 28/02/15
 * Time: 14:18
 * <p>
 * Copyright by HighTML.
 */
@Component
public class ScanProcessor {

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private ScanToImageConverter scanToImageConverter;

    @Autowired
    ScanToTextConverter scanToTextConverter;

    public void start() throws IOException, URISyntaxException {
        saveNewScans();

        List<Scan> scans = scanRepository.findAllNewScans();
        for (Scan scan : scans) {
            scanToTextConverter.addOcrText(scan);
            scanRepository.save(scan);
        }
    }


    public void saveNewScans() throws IOException {
        Path startingDir = FileSystems.getDefault().getPath(SCAN_DIRECTORY, "src/test/resources");
        SaveNewScanFiles pf = new SaveNewScanFiles("*.pdf");
        Files.walkFileTree(startingDir, pf);
    }


    public class SaveNewScanFiles extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;

        SaveNewScanFiles(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {  // ?? en attr.isRegularFile()
                String hash = com.google.common.io.Files.hash(file.toFile(), Hashing.md5()).toString();
                Scan scan = scanRepository.findByDigest(hash);
                if (scan == null) {
                    scan = new Scan(file);
                    scan.setDigest(hash);
                    scan.setScanProcessedDate(LocalDateTime.now());
                    scan.setScanCreatedDate(Utils.getCreationTime(file));
                    scan.setFlattenedToImageFile(false);

                    scanRepository.save(scan);
                }
            }
            return CONTINUE;
        }


    }

}


