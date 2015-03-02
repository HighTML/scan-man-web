package com.hightml.scanman.cannibals.converters.source;

import java.util.*;

/**
 * Builds {@link SourceConfiguration source configurations} using a simple, compact builder interface.
 *
 * @author Teun van Vegchel <teun.van.vegchel@ing.nl>
 */
public final class SourceConfigurationBuilder {

    private String name;
    private Set<String> allowedFileExtensions;
    private Map<String, List<String>> allowedFileMIMETypesByExtension;
    private int maxFileSize;
    private int maxAmountOfPixelsInFile;
    private int maxNumberOfFiles;

    /**
     * Builds a new source configuration.
     *
     * @param name The source's name.
     * @return The builder instance.
     */
    public static SourceConfigurationBuilder source(final String name) {
        return new SourceConfigurationBuilder()
                .withName(name);
    }

    /**
     * Private constructor to force the use of the static initializer.
     */
    private SourceConfigurationBuilder() {

    }

    /**
     * Set the name for this source.
     *
     * @param name The source's name.
     * @return The builder instance.
     */
    public SourceConfigurationBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the allowed file extensions for this source.
     *
     * @param allowedFileExtensions the file extensions.
     * @return The builder instance.
     */
    public SourceConfigurationBuilder withAllowedFileExtensions(final String... allowedFileExtensions) {
        this.allowedFileExtensions = new HashSet<String>(Arrays.asList(allowedFileExtensions));
        return this;
    }

    /**
     * Set the allowed file MIME-types for the source.
     *
     * @param allowedFileMIMETypesByExtension the file MIME-types.
     * @return The builder instance.
     */
    public SourceConfigurationBuilder withAllowedFileMIMETypesByExtension(
            final Map<String, List<String>> allowedFileMIMETypesByExtension) {
        this.allowedFileMIMETypesByExtension = allowedFileMIMETypesByExtension;
        return this;
    }

    /**
     * Set the allowed maximum file size.
     *
     * @param maxFileSize The file size limit.
     * @return The builder instance.
     */
    public SourceConfigurationBuilder withMaxFileSize(final int maxFileSize) {
        this.maxFileSize = maxFileSize;
        return this;
    }

    /**
     * Set the allowed maximum amount of pixels per file.
     *
     * @param maxAmountOfPixelsInFile The maximum amount of pixels per file.
     * @return The builder instance.
     */
    public SourceConfigurationBuilder withMaxAmountOfPixelsInFile(final int maxAmountOfPixelsInFile) {
        this.maxAmountOfPixelsInFile = maxAmountOfPixelsInFile;
        return this;
    }

    /**
     * Set the maximum number of files that are allowed to be uploaded in a single request.
     *
     * @param maxNumberOfFiles the maximum number of files.
     * @return The builder instance.
     */
    public SourceConfigurationBuilder withMaxNumberOfFiles(final int maxNumberOfFiles) {
        this.maxNumberOfFiles = maxNumberOfFiles;
        return this;
    }

    /**
     * Build the source configuration using the provided attributes.
     *
     * @return The resulting source configuration.
     */
    public SourceConfiguration build() {
        return new SourceConfiguration(name, maxNumberOfFiles, maxFileSize, maxAmountOfPixelsInFile, allowedFileExtensions, allowedFileMIMETypesByExtension);
    }

}
