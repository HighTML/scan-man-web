package com.hightml.scanman.cannibals.converters.source;

import lombok.NonNull;

import java.util.*;

/**
 * Keeps a registry of sources and their {@link nl.ing.web.documents.source.SourceConfiguration configurations}.
 *
 * @author Teun van Vegchel <teun.van.vegchel@ing.nl>
 */
public class SourceConfigurationRegistry {

    private final Map<String, SourceConfiguration> sources;

    /**
     * Create a new source registry.
     */
    public SourceConfigurationRegistry() {
        sources = new HashMap<String, SourceConfiguration>();
    }

    /**
     * Register a {@link nl.ing.web.documents.source.SourceConfiguration} in the registry using its
     * {@link nl.ing.web.documents.source.SourceDefinition definition} class.
     *
     * @param sourceDefinition the source to register.
     * @return the registry.
     */
    public SourceConfigurationRegistry register(@NonNull final SourceDefinition sourceDefinition) {
        final SourceConfiguration source = sourceDefinition.defineConfiguration().build();
        sources.put(source.getName(), source);
        return this;
    }

    /**
     * Register a {@link nl.ing.web.documents.source.SourceConfiguration} in the registry.
     *
     * @param source the source to register.
     * @return the registry.
     */
    public SourceConfigurationRegistry register(@NonNull final SourceConfiguration source) {
        sources.put(source.getName(), source);
        return this;
    }

    /**
     * Retrieve a {@link nl.ing.web.documents.source.SourceConfiguration} from the registry using its name.
     *
     * @param name The source's name.
     * @return The source configuration
     * @throws UnknownSourceException When there's no source registered with the given name.
     */
    public SourceConfiguration getByName(@NonNull final String name) {
        if (!sources.containsKey(name)) {
            final String message = String.format("The source '%s' is not known.", name);
            throw new UnknownSourceException(message);
        }
        return sources.get(name);
    }

    /**
     * Get the registered source configurations.
     *
     * @return The source configurations.
     */
    public Set<SourceConfiguration> getSourceConfigurations() {
        return Collections.unmodifiableSet(
                new HashSet<SourceConfiguration>(sources.values())
        );
    }

}
