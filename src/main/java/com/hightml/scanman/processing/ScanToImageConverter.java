package com.hightml.scanman.processing;

import com.hightml.scanman.Application;
import com.hightml.scanman.value.Scan;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Please enter description here.
 * <p>
 * User: marcel
 * Date: 01/03/15
 * Time: 11:42
 * <p>
 * Copyright by HighTML.
 */
@Component
@Slf4j
public class ScanToImageConverter {
    private static final int SCALED_IMAGE_MAX = 800;

    private static final int DPI_USED_FOR_PDF_BOX = 225;


    public void generatePngs(Scan scan) {
        try {
            generatePngs1(scan);
        } catch (ProcessingException e) {
            generatePngs2(scan);
        }
    }


    public void generatePngs1(Scan scan) {

        Path pdf = scan.getFile();

        log.debug("Parsing {}", pdf);


        try {
            FileChannel channel = FileChannel.open(pdf, StandardOpenOption.READ);
            scan.setByteSize(channel.size());
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdfFile = new PDFFile(buf);
            scan.setNrPages(pdfFile.getNumPages());

            for (int i = 0; i < pdfFile.getNumPages(); i++) {
                final PDFPage pdfPage = pdfFile.getPage(i);

                final BufferedImage image = getScaledImageFromPdfPage(pdfPage);
                writeImage(scan, i, image);

            }
        } catch (IOException e) {
            throw new ProcessingException("During writing PNGs", e);
        }
    }

    private void writeImage(Scan scan, int i, BufferedImage image) throws IOException {
        Path imagePath = FileSystems.getDefault().getPath(Application.IMAGEROOT, scan.getDigest() + (i > 0 ? "-" + i : "") + ".png");
        log.debug("Writing to  {}", imagePath);
        ImageIO.write(image, "png", Files.newOutputStream(imagePath, StandardOpenOption.CREATE_NEW));
    }


    private BufferedImage getScaledImageFromPdfPage(final PDFPage pdfPage) {
        final Rectangle2D r2d = pdfPage.getBBox();
        int width = (int) r2d.getWidth();
        int height = (int) r2d.getHeight();

        //scale (up OR down) to allowed maximum, using current aspect ratio
        final float aspectRatio = pdfPage.getAspectRatio(); // =width/height
        if (width > height) {
            width = SCALED_IMAGE_MAX;
            height = (int) (width / aspectRatio);
        } else {
            height = SCALED_IMAGE_MAX;
            width = (int) (height * aspectRatio);
        }

        return (BufferedImage) pdfPage.getImage(width, height, r2d, null, true, true);
    }


    private void generatePngs2(Scan scan) {
        try {

            final PDDocument document = PDDocument.load(Files.newInputStream(scan.getFile(), StandardOpenOption.READ));

            scan.setNrPages(document.getNumberOfPages());
            try {
                final List<PDPage> pages = document.getDocumentCatalog().getAllPages();

                for (int i = 0; i < pages.size(); i++) {
                    final BufferedImage image = pages.get(i).convertToImage(BufferedImage.TYPE_INT_RGB, DPI_USED_FOR_PDF_BOX);
                    writeImage(scan, i, image);
                }
            } finally {
                document.close();
            }

        } catch (IOException e) {
            log.error("png2",e);
        }


    }
}
