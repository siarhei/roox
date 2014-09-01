package ru.roox.service.dao.impl;

import ru.roox.domain.Customer;
import ru.roox.service.dao.CustomerDao;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
public class CustomerDaoImpl extends GenericDaoImpl<Customer> implements CustomerDao {
    public CustomerDaoImpl(Class<Customer> persistentClass) {
        super(persistentClass);
    }
}
