package com.hightml.scanman.cannibals.converters;

import com.hightml.scanman.cannibals.converters.exception.ConversionException;
import com.hightml.scanman.cannibals.converters.source.SourceConfiguration;
import com.hightml.scanman.cannibals.Document;
import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Factory class that provides image converters
 */
public class ImageToPngConverter {

    /**
     * Slf4j Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImageToPngConverter.class);

    /**
     * Constructor.
     */
    public ImageToPngConverter() {
    }

    /**
     * Converts an uploaded image-file to a PNG-file.
     *
     * @param document The uploaded file.
     * @param source The concerning SourceConfiguration.
     * @return A converted PNG-file.
     */
    public Document convert(final Document document, final SourceConfiguration source) {
        try {
            return convertUploadedFile(document, source);
        } catch (ConversionException e) {
            throw e;
        } catch (Exception ex) {
            //If anything goes wrong, we rethrow this as being an ConversionException, with stacktrace details
            throw new ConversionException("ConversionException", ex);
        }
    }

    private Document convertUploadedFile(final Document file, final SourceConfiguration sourceConfiguration) throws IOException {
        final byte[] originalData = file.getData();
        if (originalData == null) {
            throw new ConversionException("No data in Document.");
        }

        byte[] convertedData = null;
        final String fileExtension = getFileExtensionInLowerCase(file.getFilename());
        if ("jpg".equals(fileExtension) || "jpeg".equals(fileExtension) || "gif".equals(fileExtension)) {
            convertedData = convertGeneralFileToPngByteArray(file, sourceConfiguration);
        } else if ("png".equals(fileExtension)) {
            convertedData = convertPngFileToJpgAndBackToPngByteArray(file, sourceConfiguration);
        } else if ("tif".equals(fileExtension) || "tiff".equals(fileExtension)) {
            convertedData = convertTiffFileToPngByteArray(file, sourceConfiguration);
        } else {
            /* no default behaviour (convertedData already null)*/
            throw new ConversionException("Unspecified format for conversion");
        }

        if (convertedData == null) {
            throw new ConversionException("No data after conversion");
        }

        LOG.info("Successfully converted file with original size \"{}\" and converted size \"{}\".", originalData.length, convertedData.length);

        return new Document(file.getFilename().concat(".png"), 0,
                convertedData);
    }

    private byte[] convertTiffFileToPngByteArray(final Document uploadedFile, final SourceConfiguration sourceConfiguration) throws IOException {
        logConversionStart(uploadedFile);
        ByteArraySeekableStream stream = new ByteArraySeekableStream(uploadedFile.getData());
        TIFFDecodeParam decodeParam = new TIFFDecodeParam();
        decodeParam.setDecodePaletteAsShorts(true);
        ParameterBlock params = new ParameterBlock();
        params.add(stream);
        RenderedOp renderedOp = JAI.create("tiff", params);
        BufferedImage imageFromTiff = renderedOp.getAsBufferedImage();
        logTheSuccesfulLoadingIntoBufferedImage(uploadedFile, imageFromTiff);
        validateMaxAmountOfPixelsOnImage(imageFromTiff, sourceConfiguration);
        return writeImageToFormat(imageFromTiff, "PNG");
    }

    private byte[] convertPngFileToJpgAndBackToPngByteArray(final Document uploadedFile, final SourceConfiguration sourceConfiguration) throws IOException {
        logConversionStart(uploadedFile);
        BufferedImage imageFromPNG = readAsImage(getAsStream(uploadedFile.getData()));
        logTheSuccesfulLoadingIntoBufferedImage(uploadedFile, imageFromPNG);
        validateMaxAmountOfPixelsOnImage(imageFromPNG, sourceConfiguration);
        byte[] jpgData = writeImageToFormat(imageFromPNG, "JPG");
        BufferedImage imageFromJPG = readAsImage(getAsStream(jpgData));
        return writeImageToFormat(imageFromJPG, "PNG");
    }

    private byte[] convertGeneralFileToPngByteArray(final Document uploadedFile, final SourceConfiguration sourceConfiguration) throws IOException {
        logConversionStart(uploadedFile);
        BufferedImage image = readAsImage(getAsStream(uploadedFile.getData()));
        logTheSuccesfulLoadingIntoBufferedImage(uploadedFile, image);
        validateMaxAmountOfPixelsOnImage(image, sourceConfiguration);
        return writeImageToFormat(image, "PNG");
    }

    private InputStream getAsStream(final byte[] fileData) {
        return new ByteArrayInputStream(fileData);
    }

    /**
     * @param stream the stream to be read
     * @return a BufferedImage, when the stream contains a supported ImageFormat
     * @throws java.io.IOException
     */
    private BufferedImage readAsImage(final InputStream stream) throws IOException {
        ImageIO.setUseCache(false);
        return ImageIO.read(stream);
    }

    private byte[] writeImageToFormat(final BufferedImage image, final String formatName) throws IOException {
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, outStream);
        return outStream.toByteArray();
    }


    /**
     * @param fileName the filename
     * @return the file extension without the leading dot
     */
    private String getFileExtensionInLowerCase(final String fileName) {
        return FilenameUtils.getExtension(fileName).toLowerCase();
    }

    private void validateMaxAmountOfPixelsOnImage(final BufferedImage image, final SourceConfiguration sourceConfiguration) {
        final int maxAmountOfPixelsAllowed = sourceConfiguration.getMaxAmountOfPixelsInFile();
        if (maxAmountOfPixelsAllowed > 0) {
            final int actualAmountOfPixels = image.getWidth() * image.getHeight();
            if (actualAmountOfPixels > maxAmountOfPixelsAllowed) {
                throw new ConversionException("Too many pixels in Document.");
            }
        }
    }

    private void logConversionStart(final Document uploadedFile) {
        LOG.info("Starting conversion of file with extension \"{}\" and size \"{}\".",
                getFileExtensionInLowerCase(uploadedFile.getFilename()),
                uploadedFile.getData().length);
    }

    private void logTheSuccesfulLoadingIntoBufferedImage(final Document uploadedFile, final BufferedImage image) {
        LOG.info("Succesfully loaded file with extension \"{}\", width \"{}\", height \"{}\", size \"{}\" and bitdepth \"{}\".", new Object[]{
                getFileExtensionInLowerCase(uploadedFile.getFilename()),
                image.getWidth(),
                image.getHeight(),
                uploadedFile.getData().length,
                image.getColorModel().getPixelSize()});
    }

}
