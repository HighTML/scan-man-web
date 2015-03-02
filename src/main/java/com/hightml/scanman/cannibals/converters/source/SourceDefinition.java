package com.hightml.scanman.cannibals.converters.source;

/**
 * Defines a {@link nl.ing.web.documents.source.SourceConfiguration} using a
 * {@link nl.ing.web.documents.source.SourceConfigurationBuilder builder}.
 *
 * @author Teun van Vegchel <teun.van.vegchel@ing.nl>
 */
public interface SourceDefinition {

    /**
     * Define a {@link nl.ing.web.documents.source.SourceConfiguration} using a
     * {@link nl.ing.web.documents.source.SourceConfigurationBuilder builder}.
     *
     * @return The builder.
     */
    SourceConfigurationBuilder defineConfiguration();

}
