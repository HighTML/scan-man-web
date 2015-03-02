package com.hightml.scanman.cannibals.converters.pdf;

import com.hightml.scanman.cannibals.converters.exception.ConversionException;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nk70yw on 1/26/15.
 */
public class PdfRendererConverter implements PdfToImageConverter {

    private static final int SCALED_IMAGE_MAX = 1500;

    @Override
    public List<BufferedImage> convertPdfFileToImages(final byte[] uploadedData, final int maxWidthOrHeight) {

        try {
            final PDFFile pdfFile = new PDFFile(ByteBuffer.wrap(uploadedData));

            final List<BufferedImage> convertedImages = new ArrayList<BufferedImage>();
            for (int i = 1; i < pdfFile.getNumPages() + 1; i++) {
                final PDFPage pdfPage = pdfFile.getPage(i);

                performSanityCheckOnMaxWidthOrHeightForPdfRenderer(pdfPage, maxWidthOrHeight);
                final BufferedImage image = getScaledImageFromPdfPage(pdfPage);
                convertedImages.add(image);
            }
            return convertedImages;
        } catch (IOException e) {
            throw new ConversionException("Something has gone wrong while converting a PDF-file to PNG-files.", e);
        }
    }

    /**
     * To prevent OutOfMemory by uploading abnormally sized PDF-pages.
     */
    private void performSanityCheckOnMaxWidthOrHeightForPdfRenderer(final PDFPage page, final int maxWidthOrHeight) {
        if (page.getHeight() > maxWidthOrHeight || page.getWidth() > maxWidthOrHeight) {
            throw new ConversionException("Input PDF has an abnormal size, aborting conversion");
        }
    }

    private BufferedImage getScaledImageFromPdfPage(final PDFPage pdfPage) {

        //get current width & height
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
}
