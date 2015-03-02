package com.hightml.scanman.cannibals.converters;

import com.hightml.scanman.cannibals.converters.exception.ConversionException;
import com.hightml.scanman.cannibals.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.PngImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Converts PNG-files to a PDF-file.
 */
public class PngToPdfConverter {

    /**
     * Converts multiple PNG-files to one PDF-file.
     *
     * @param convertedFiles The input file or files.
     * @return
     */
    public Document convert(final List<Document> convertedFiles) {
        com.lowagie.text.Document pdfDocument = new com.lowagie.text.Document(PageSize.A4, 0f, 0f, 0f, 0f);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(pdfDocument, outputStream);
            pdfDocument.open();
            for (Document file : convertedFiles) {
                addPngImageToPdfDocument(file, pdfDocument);
            }
            pdfDocument.close();
        } catch (DocumentException e) {
            throw new ConversionException("Something went wrong while loading the PNG-image during png to pdf conversion.", e);
        } catch (IOException e) {
            throw new ConversionException("Something went wrong while converting PNG-images to a PDF-file.", e);
        }

        return new Document("document.pdf", 0, outputStream.toByteArray());
    }

    private void addPngImageToPdfDocument(final Document file,
                                          final com.lowagie.text.Document document) throws IOException, DocumentException {
        Image pngImage = PngImage.getImage(file.getData());
        Rectangle pageSize = new Rectangle(pngImage.getWidth(), pngImage.getHeight());
        document.setPageSize(pageSize);
        document.newPage();
        document.add(pngImage);
        document.newPage();
    }

}
