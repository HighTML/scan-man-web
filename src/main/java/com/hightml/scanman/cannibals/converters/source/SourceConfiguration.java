package com.hightml.scanman.cannibals.converters.source;

import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Contains source specific configuration properties.
 *
 * @author Teun van Vegchel <teun.van.vegchel@ing.nl>
 */
@Value
@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField" })
public final class SourceConfiguration {

    /**
     * The identifying name for the source.
     */
    @NonNull
    private final String name;

    /**
     * The maximum number of files that are allowed to be uploaded in a single request.
     */
    private final int maxNumberOfFiles;

    /**
     * The maximum file size per file in a single request.
     */
    private final int maxFileSize;

    /**
     * The maximum amount of pixels per file in a single request.
     */
    private final int maxAmountOfPixelsInFile;

    /**
     * The whitelist of file extensions that are allowed to be uploaded.
     */
    @NonNull
    private final Set<String> allowedFileExtensions;

    /**
     * The whitelist of file MIME-types that are allowed to be uploaded.
     */
    @NonNull
    private final Map<String, List<String>> allowedFileMIMETypesByExtension;

}
