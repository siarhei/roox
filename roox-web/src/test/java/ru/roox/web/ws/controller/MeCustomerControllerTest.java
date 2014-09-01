package ru.roox.web.ws.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.roox.domain.AvatarInfo;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.web.constants.RooxHttpConstants;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link ru.roox.web.ws.controller.MeCustomerController}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class MeCustomerControllerTest extends AbstractControllerTest {

    @InjectMocks
    private MeCustomerController customerController;

    @Captor
    private ArgumentCaptor<PartnerMapping> partnerCaptor;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testUploadPartnerAvatarForAuthCustomer() throws Exception {
        Long partnerId = 3L;
        Long customerId = 1L;
        Resource avatar = new ClassPathResource("avatar.png");

        PartnerMapping pm = new PartnerMapping();
        pm.setCustomer(new Customer());
        when(customerService.update(Mockito.any(PartnerMapping.class))).thenReturn(pm);

        MockMultipartFile file = new MockMultipartFile("file", "origName", null, avatar.getInputStream());
        mockMvc.perform(fileUpload("/ws/customers/@me/partners/{pid}/avatar", partnerId).
                    file(file).
                    param(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID, customerId.toString())).
                andExpect(status().isOk());

        verify(customerService).update(partnerCaptor.capture());

        AvatarInfo avatarInfo = partnerCaptor.getValue().getAvatar();

        assertNotNull(avatarInfo);
        assertEquals("origName", avatarInfo.getName());
        assertEquals(avatar.contentLength(), avatarInfo.getData().length);
    }
}
