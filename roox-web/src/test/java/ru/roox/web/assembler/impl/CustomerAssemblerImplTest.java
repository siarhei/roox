package ru.roox.web.assembler.impl;

import org.junit.Test;
import ru.roox.domain.Customer;
import ru.roox.domain.CustomerStatus;
import ru.roox.web.ws.model.CustomerDto;
import ru.roox.test.utils.CustomerBuilder;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;

/**
 * Tests for {@link CustomerAssemblerImpl}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class CustomerAssemblerImplTest {
    private final CustomerAssemblerImpl assembler = new CustomerAssemblerImpl();

    @Test
    public void testConverterEntityToDto() throws Exception {
        Customer customer = CustomerBuilder.newBuilder().
                id(1L).
                balance(BigDecimal.TEN).
                status(CustomerStatus.active).
                firstName("firstName").
                midName("midName").
                lastName("lastName").build();

        CustomerDto dto = assembler.getEntityDtoConverter().apply(customer);

        assertNotNull(dto);
        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getBalance(), dto.getBalance());
        assertEquals(customer.getStatus().name(), dto.getStatus());
        assertSame(customer.getNameInfo().getFirstName(), dto.getFirstName());
        assertSame(customer.getNameInfo().getMidName(), dto.getMidName());
        assertSame(customer.getNameInfo().getLastName(), dto.getLastName());
    }
}
