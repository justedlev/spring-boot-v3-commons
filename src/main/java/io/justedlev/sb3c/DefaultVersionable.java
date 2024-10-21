package io.justedlev.sb3c;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * An abstract class that represents an entity with versioning capability.
 * This class extends the {@link DefaultAuditable} class and adds a version field.
 *
 * @param <K> the type of the primary key, which must be {@link Serializable}.
 * @author Justedlev
 * @see Version
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class DefaultVersionable<K extends Serializable>
        extends DefaultAuditable<K>
        implements Versionable<Long> {

    /**
     * The version number of the entity. This field is used for optimistic locking.
     *
     * @see Version
     */
    @Version
    @NotNull
    private Long version;

}
