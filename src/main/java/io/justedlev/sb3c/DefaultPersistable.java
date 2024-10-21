package io.justedlev.sb3c;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract base class for persistable entities. This class provides a base implementation for
 * entities with an ID and includes methods for checking if an entity is new, as well as
 * {@code equals(Object o)} and {@code hashCode()} implementations.
 *
 * @param <K> the type of the primary key, which must be {@link Serializable}.
 * @author Justedlev
 * @see Persistable
 * @see org.springframework.data.jpa.domain.AbstractPersistable
 */
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
@MappedSuperclass
public abstract class DefaultPersistable<K extends Serializable> implements Persistable<K> {

    /**
     * @see Persistable#getId()
     */
    @Id
    @GeneratedValue
    @Setter(AccessLevel.PROTECTED)
    private K id;

    /**
     * @see Persistable#getId()
     */
    @Override
    public K getId() {
        return this.id();
    }

    /**
     * @see Persistable#isNew()
     */
    @Transient
    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), this.id());
    }

    @Override
    public final boolean equals(Object o) { // NOSONAR

        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        var oEffectiveClass = o instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass() :
                o.getClass();

        var thisEffectiveClass = this instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass() :
                this.getClass();

        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }

        DefaultPersistable<?> that = (DefaultPersistable<?>) o;

        return id() != null && Objects.equals(id(), that.id());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ?
                proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() :
                Objects.hashCode(this.id());
    }
}
