package ru.roox.web.assembler;

import ru.roox.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Assemblies en entity T to dto U and vise versa
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public interface EntityAssembler<T extends BaseEntity, U extends Serializable> {
    /**
     * Transforms DTO to entity
     *
     * @param dto DTO
     * @return entity
     */
    T assembly(U dto);

    /**
     * Transforms entity to DTO
     *
     * @param entity an entity
     * @return DTO
     */
    U assembly(T entity);

    /**
     * Transforms list of entities to list of DTOs
     *
     * @param entities list of entities
     * @return  list of DTOs
     */
    List<U> assembly(List<T> entities);

    /**
     * Updates entity by DTO
     *
     * @param entity entity to update
     * @param dto   dto with data to update
     */
    void updateEntity(T entity, U dto);
}
