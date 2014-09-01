package ru.roox.service.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.roox.domain.Customer;
import ru.roox.service.dao.CustomerDao;

import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link ru.roox.service.dao.impl.CustomerDaoImpl}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/*-beans.xml"})
@ActiveProfiles(profiles = {"test"})
public class CustomerDaoImplTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer();

        Customer saved = customerDao.save(customer);
        assertNotNull(saved.getId());
    }
}
