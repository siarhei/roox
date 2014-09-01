package ru.roox.service.impl;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.service.dao.CustomerDao;
import ru.roox.service.dao.PartnerMappingDao;
import ru.roox.test.utils.CustomerBuilder;
import ru.roox.test.utils.PartnerMappingBuilder;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link CustomerServiceImpl}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class CustomerServiceImplTest {
    @InjectMocks
    private CustomerServiceImpl testedInstance;

    @Mock
    private CustomerDao customerDao;
    @Mock
    private PartnerMappingDao partnerMappingDao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsExist() throws Exception {
        Long customerId = 1L;

        when(customerDao.findById(eq(customerId))).thenReturn(new Customer());

        assertTrue(testedInstance.isExist(customerId));
    }

    @Test
    public void testFindBy() throws Exception {
        PartnerMapping partnerMapping = PartnerMappingBuilder.newBuilder().id(2L).build();
        Customer customer = CustomerBuilder.newBuilder().id(1L).partners(Lists.newArrayList(partnerMapping)).build();

        when(customerDao.findById(eq(customer.getId()))).thenReturn(customer);

        PartnerMapping found = testedInstance.findBy(customer.getId(), partnerMapping.getId());

        assertNotNull(found);
        assertSame(partnerMapping, found);
    }
}
