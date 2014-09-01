package ru.roox.web.ws.controller;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.service.CustomerService;
import ru.roox.test.utils.CustomerBuilder;
import ru.roox.web.assembler.impl.CustomerAssemblerImpl;
import ru.roox.web.assembler.impl.PartnerMappingAssemblerImpl;

import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static ru.roox.test.utils.PartnerMappingBuilder.newBuilder;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/spring/*-beans.xml"})
@WebAppConfiguration
@ActiveProfiles(profiles = {"test"})
public class AbstractControllerTest {
    protected final Map<Long, Customer> customersMap = Maps.newHashMap();
    protected final Multimap<Long, PartnerMapping> partnerMappingMultimap = LinkedHashMultimap.create(1, 3);

    {
        /*
         * customer (id = 1) contains only one partnerMapping (id = 3)
         * customer (id = 2) contains two partnerMappings (id = 4,5)
         */

        //partners
        partnerMappingMultimap.put(1L, newBuilder().id(3L).firstName("pm3_fn").lastName("pm3_ln").partnerId("pid3").customerPartnerId("cpid3").build());
        partnerMappingMultimap.put(2L, newBuilder().id(4L).firstName("pm4_fn").lastName("pm4_ln").partnerId("pid4").customerPartnerId("cpid4").build());
        partnerMappingMultimap.put(2L, newBuilder().id(5L).firstName("pm5_fn").lastName("pm5_ln").partnerId("pid5").customerPartnerId("cpid5").build());

        //customers
        customersMap.put(1L, CustomerBuilder.newBuilder().id(1L).firstName("fName1").partners(partnerMappingMultimap.get(1L)).build());
        customersMap.put(2L, CustomerBuilder.newBuilder().id(2L).firstName("fName2").partners(partnerMappingMultimap.get(2L)).build());
    }

    protected MockMvc mockMvc;
    @Mock
    protected CustomerService customerService;
    @Spy
    protected CustomerAssemblerImpl customerAssembler;
    @Spy
    protected PartnerMappingAssemblerImpl partnerMappingAssembler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(customerService.findAll()).thenReturn(Lists.newArrayList(customersMap.values()));
        for (Long customerId : customersMap.keySet()) {
            when(customerService.findById(eq(customerId))).thenReturn(customersMap.get(customerId));

            for (PartnerMapping partnerMapping : partnerMappingMultimap.get(customerId)) {
                when(customerService.findBy(eq(customerId), eq(partnerMapping.getId()))).thenReturn(partnerMapping);
            }
        }
    }

    @Test
    public void testFake() throws Exception {
        assertTrue(true);
    }
}
