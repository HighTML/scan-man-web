package com.hightml.scanman.cannibals.converters.pdf;


import com.hightml.scanman.cannibals.converters.exception.ConversionException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nk70yw on 1/26/15.
 */
public class PdfBoxConverter implements PdfToImageConverter {

    private static final int DPI_USED_FOR_PDF_BOX = 225;

    @Override
    public List<BufferedImage> convertPdfFileToImages(final byte[] uploadedData, final int maxWidthOrHeight) {
        try {
            final PDDocument document = PDDocument.load(new ByteArrayInputStream(uploadedData));

            List<BufferedImage> images;
            try {
                final List<PDPage> pages = document.getDocumentCatalog().getAllPages();
                images = new ArrayList<BufferedImage>();
                for (PDPage page : pages) {
                    performSanityCheckOnMaxWidthOrHeightForPdfBox(page, maxWidthOrHeight);
                    final BufferedImage image = page.convertToImage(BufferedImage.TYPE_INT_RGB, DPI_USED_FOR_PDF_BOX);
                    images.add(image);
                }
            } finally {
                document.close();
            }
            return images;
        } catch (IOException e) {
            throw new ConversionException("Something has gone wrong while converting a PDF-file to PNG-files.", e);
        }
    }

    /**
     * To prevent OutOfMemory by uploading abnormally sized PDF-pages.
     */
    private void performSanityCheckOnMaxWidthOrHeightForPdfBox(final PDPage page, final int maxWidthOrHeight) {
        if (page.getTrimBox().getHeight() > maxWidthOrHeight || page.getTrimBox().getWidth() > maxWidthOrHeight) {
            throw new ConversionException("Input PDF has an abnormal size, aborting conversion");
        }
    }
}
