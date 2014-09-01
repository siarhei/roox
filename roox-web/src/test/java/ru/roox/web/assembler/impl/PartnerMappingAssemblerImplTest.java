package ru.roox.web.assembler.impl;

import com.google.common.collect.Lists;
import org.junit.Test;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.web.ws.model.PartnerMappingDto;
import ru.roox.test.utils.CustomerBuilder;
import ru.roox.test.utils.PartnerMappingBuilder;

import static junit.framework.Assert.*;

/**
 * Tests for {@link PartnerMappingAssemblerImpl}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class PartnerMappingAssemblerImplTest {
    private final PartnerMappingAssemblerImpl assembler = new PartnerMappingAssemblerImpl();

    @Test
    public void testConverterEntityToDto() throws Exception {
        PartnerMapping partnerMapping = PartnerMappingBuilder.newBuilder().
                id(2L).
                partnerId("partnerId").
                customerPartnerId("customerPartnerId").
                firstName("firstName").
                midName("midName").
                lastName("lastName").build();

        Customer customer = CustomerBuilder.newBuilder().
                id(1L).
                partners(Lists.newArrayList(partnerMapping)).build();

        PartnerMappingDto dto = assembler.getEntityDtoConverter().apply(partnerMapping);

        assertNotNull(dto);
        assertEquals(partnerMapping.getId(), dto.getId());
        assertEquals(partnerMapping.getCustomer().getId(), dto.getCustomerId());
        assertSame(partnerMapping.getPartnerId(), dto.getPartnerId());
        assertSame(partnerMapping.getCustomerPartnerId(), dto.getCustomerPartnerId());
        assertSame(partnerMapping.getNameInfo().getFirstName(), dto.getCustomerPartnerFirstName());
        assertSame(partnerMapping.getNameInfo().getMidName(), dto.getCustomerPartnerMidName());
        assertSame(partnerMapping.getNameInfo().getLastName(), dto.getCustomerPartnerLastName());
    }
}
