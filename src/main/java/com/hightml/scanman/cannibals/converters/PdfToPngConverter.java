package com.hightml.scanman.cannibals.converters;

import com.hightml.scanman.cannibals.converters.exception.ConversionException;
import com.hightml.scanman.cannibals.converters.pdf.PdfBoxConverter;
import com.hightml.scanman.cannibals.converters.pdf.PdfRendererConverter;
import com.hightml.scanman.cannibals.Document;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a PDF-file into multiple PNG-files.
 */
public class PdfToPngConverter {

    private static final Logger LOG = LoggerFactory.getLogger(PdfToPngConverter.class);
    private static final int MAX_WIDTH_OR_HEIGHT = 5999;

    /**
     * Converts an uploaded PDF-file to a list of PNG-files.
     *
     * @param uploadedFile The uploaded PDF-file.
     * @return A list of PNG-files.
     */
    public List<Document> convert(final Document uploadedFile) {
        try {
            logConversionStart(uploadedFile);

            List<Document> convertedFiles = convertPdfFileIntoPngFiles(uploadedFile.getData());

            logConversionEnd(uploadedFile, convertedFiles);
            return convertedFiles;
        } catch (ConversionException e) {
            LOG.error("Something has gone wrong while converting a PDF-file to PNG-files.", e);
            throw e;
        } catch (IOException e) {
            LOG.error("Something has gone wrong while converting a PDF-file to PNG-files.", e);
            throw new ConversionException("Something has gone wrong while converting a PDF-file to PNG-files.", e);
        }
    }

    private List<Document> convertPdfFileIntoPngFiles(final byte[] uploadedData) throws IOException {
        final List<BufferedImage> images = convertPdfFileIntoImages(uploadedData);
        return convertImagesToDocumentWithPngFile(images);
    }

    private List<BufferedImage> convertPdfFileIntoImages(final byte[] uploadedData) throws IOException {
        try {
            return new PdfRendererConverter().convertPdfFileToImages(uploadedData, MAX_WIDTH_OR_HEIGHT);
        } catch (Exception e) {
            //back-up conversion;
            // when uploadedFile cannot be converted by PdfRenderer try with PdfBox.
            LOG.info("Conversion with PdfRenderer failed, starting conversion with PdfBox.", e);
            return new PdfBoxConverter().convertPdfFileToImages(uploadedData, MAX_WIDTH_OR_HEIGHT);
        }
    }

    private List<Document> convertImagesToDocumentWithPngFile(final List<BufferedImage> images) throws IOException {

        final List<Document> documents = new ArrayList<Document>();
        for (BufferedImage image : images) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outStream);
            Document document = new Document("ConvertedPngFile.png", 0, outStream.toByteArray());
            documents.add(document);
        }

        return documents;
    }

    private void logConversionStart(final Document uploadedFile) {
        LOG.info("Starting conversion of file with extension \"{}\" and size \"{}\".",
                getFileExtensionInLowerCase(uploadedFile.getFilename()),
                uploadedFile.getData().length);
    }

    private void logConversionEnd(final Document uploadedFile, final List<Document> convertedFiles) {
        LOG.info("Successfully converted PDF-file with original size \"{}\" into {} PNG-files.",
                uploadedFile.getData().length,
                convertedFiles.size());
    }

    private String getFileExtensionInLowerCase(final String fileName) {
        return FilenameUtils.getExtension(fileName).toLowerCase();
    }
}
