package ru.roox.web.ws.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.web.constants.RooxHttpConstants;
import ru.roox.web.ws.model.PartnerMappingDto;

import java.nio.charset.Charset;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests for {@link CustomerController}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class CustomerControllerTest extends AbstractControllerTest {
    private static final MediaType APP_JSON_UTF8 = new MediaType("application", "JSON", Charset.forName("UTF-8"));

    @InjectMocks
    private CustomerController customerController;
    @Captor
    private ArgumentCaptor<Long> customerIdCaptor;
    @Captor
    private ArgumentCaptor<Long> partnerIdCaptor;
    @Captor
    private ArgumentCaptor<PartnerMappingDto> partnerDtoCaptor;
    @Captor
    private ArgumentCaptor<PartnerMapping> partnerCaptor;

    @Override
    public AbstractController getTestedInstance() {
        return customerController;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetCustomers() throws Exception {
        mockMvc.perform(get("/ws/customers").
                    contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(content().contentType(APP_JSON_UTF8)).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$[0].id", is(1))).
                andExpect(jsonPath("$[0].firstName", is("fName1"))).
                andExpect(jsonPath("$[1].id", is(2))).
                andExpect(jsonPath("$[1].firstName", is("fName2")));

    }

    @Test
    public void testGetCustomer() throws Exception {
        mockMvc.perform(get("/ws/customers/{id}", 1L).
                    contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(content().contentType(APP_JSON_UTF8)).
                andExpect(jsonPath("id", is(1))).
                andExpect(jsonPath("firstName", is("fName1")));

    }

    @Test
    public void testGetPartners() throws Exception {
        mockMvc.perform(get("/ws/customers/{id}/partners", 1L).
                    contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(content().contentType(APP_JSON_UTF8)).
                andExpect(jsonPath("$", hasSize(1))).
                andExpect(jsonPath("$[0].id", is(3))).
                andExpect(jsonPath("$[0].partnerId", is("pid3"))).
                andExpect(jsonPath("$[0].customerPartnerFirstName", is("pm3_fn"))).
                andExpect(jsonPath("$[0].customerPartnerLastName", is("pm3_ln"))).
                andExpect(jsonPath("$[0].customerId", is(1)));
    }

    @Test
    public void testGetPartner() throws Exception {
        mockMvc.perform(get("/ws/customers/{id}/partners/{pid}", 2L, 4L).
                    contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(content().contentType(APP_JSON_UTF8)).
                andExpect(jsonPath("id", is(4))).
                andExpect(jsonPath("partnerId", is("pid4"))).
                andExpect(jsonPath("customerPartnerFirstName", is("pm4_fn"))).
                andExpect(jsonPath("customerPartnerLastName", is("pm4_ln"))).
                andExpect(jsonPath("customerId", is(2)));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/ws/customers/{id}", 2L).
                    contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testDeletePartners() throws Exception {
        Long customerId = 2L;

        mockMvc.perform(delete("/ws/customers/{id}/partners", customerId).
                    contentType(MediaType.APPLICATION_JSON).
                    param(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID, customerId.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(jsonPath("result", is(RooxHttpConstants.SUCCESS_RESULT)));

        verify(customerService).deleteAllPartners(customerIdCaptor.capture());
        assertEquals(customerId, customerIdCaptor.getValue());
    }

    @Test
    public void testDeletePartner() throws Exception {
        Long customerId = 2L;
        Long partnerId = 4L;

        mockMvc.perform(delete("/ws/customers/{id}/partners/{pid}", customerId, partnerId).
                    contentType(MediaType.APPLICATION_JSON).
                    param(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID, customerId.toString())).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(jsonPath("result", is(RooxHttpConstants.SUCCESS_RESULT)));

        verify(customerService).deletePartner(customerIdCaptor.capture(), partnerIdCaptor.capture());
        assertEquals(customerId, customerIdCaptor.getValue());
        assertEquals(partnerId, partnerIdCaptor.getValue());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        mockMvc.perform(post("/ws/customers").
                    contentType(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testCreatePartnerForCustomer() throws Exception {
        Long customerId = 1L;

        //avoid IAE in abstractAssembler
        PartnerMapping pm = new PartnerMapping();
        pm.setCustomer(new Customer());
        when(customerService.createFor(eq(customerId), Mockito.any(PartnerMapping.class))).thenReturn(pm);

        mockMvc.perform(post("/ws/customers/{id}/partners", customerId).
                    contentType(MediaType.APPLICATION_JSON).
                    param(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID, customerId.toString()).
                    content("{ \"partnerId\": \"pid\", \"customerPartnerId\": \"cpid\" }".getBytes())).
                andExpect(MockMvcResultMatchers.status().isOk());

        verify(partnerMappingAssembler).assembly(partnerDtoCaptor.capture());
        verify(customerService).createFor(customerIdCaptor.capture(), partnerCaptor.capture());

        assertEquals("pid", partnerDtoCaptor.getValue().getPartnerId());
        assertEquals("cpid", partnerDtoCaptor.getValue().getCustomerPartnerId());
        assertEquals(customerId, customerIdCaptor.getValue());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        mockMvc.perform(put("/ws/customers/{id}", 1L)).
                andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void testUpdatePartnerForCustomer() throws Exception {
        Long customerId = 1L;
        Long partnerId = 3L;

        PartnerMapping pm = new PartnerMapping();
        pm.setCustomer(new Customer());
        when(customerService.update(Mockito.any(PartnerMapping.class))).thenReturn(pm);

        mockMvc.perform(put("/ws/customers/{id}/partners/{pid}", customerId, partnerId).
                    contentType(MediaType.APPLICATION_JSON).
                    param(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID, customerId.toString()).
                    content("{ \"partnerId\": \"pid\", \"customerPartnerId\": \"cpid\" }".getBytes())).
                andExpect(MockMvcResultMatchers.status().isOk());

        verify(partnerMappingAssembler).updateEntity(partnerCaptor.capture(), partnerDtoCaptor.capture());
        assertEquals("pid", partnerDtoCaptor.getValue().getPartnerId());
        assertEquals("cpid", partnerDtoCaptor.getValue().getCustomerPartnerId());
    }
}
