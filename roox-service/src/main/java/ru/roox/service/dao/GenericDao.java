package ru.roox.service.dao;

import ru.roox.domain.BaseEntity;

import java.util.List;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
public interface GenericDao <T extends BaseEntity> {
    T save(T entity);
    T update(T entity);
    void delete(T entity);

    T findById(Long id);
    List<T> findAll();

    void flush();
}
