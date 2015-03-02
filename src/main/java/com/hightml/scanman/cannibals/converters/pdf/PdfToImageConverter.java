package com.hightml.scanman.cannibals.converters.pdf;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by nk70yw on 1/26/15.
 */
public interface PdfToImageConverter {

    public List<BufferedImage> convertPdfFileToImages(final byte[] uploadedData, final int maxWidthOrHeight);
}
