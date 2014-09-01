package ru.roox.service.dao.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.roox.domain.BaseEntity;
import ru.roox.service.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@Transactional
public abstract class GenericDaoImpl<T extends BaseEntity> implements GenericDao<T> {
    private Class<T> persistentClass;

    private EntityManager entityManager;

    public GenericDaoImpl(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    protected final EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @Transactional(readOnly=true)
    @Override
    public T findById(Long id) {
        return getEntityManager().find(getPersistentClass(), id);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery(
                "select x from " + getPersistentClass().getSimpleName() + " x").getResultList();

    }

    @Override
    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public void flush() {
        getEntityManager().flush();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
