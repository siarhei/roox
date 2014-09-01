package ru.roox.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import ru.roox.service.CustomerService;
import ru.roox.web.constants.RooxHttpConstants;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AuthenticationFilter}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class AuthenticationFilterTest {
    @InjectMocks
    private AuthenticationFilter testedInstance;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private CustomerService customerService;
    @Captor
    private ArgumentCaptor<Integer> statusCaptor;
    @Captor
    private ArgumentCaptor<Long> customerIdCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilter() throws Exception {
        when(request.getParameter(eq(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID))).thenReturn("10");
        when(customerService.isExist(eq(10L))).thenReturn(true);

        testedInstance.doFilter(request, response, chain);

        verify(chain).doFilter(eq(request), eq(response));
        verify(customerService).isExist(customerIdCaptor.capture());
        assertEquals(10L, customerIdCaptor.getValue().longValue());
    }

    @Test
    public void testDoFilter_customerNotFound() throws Exception {
        when(request.getParameter(eq(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID))).thenReturn("10");
        when(customerService.isExist(eq(10L))).thenReturn(false);

        testedInstance.doFilter(request, response, chain);

        verify(customerService).isExist(customerIdCaptor.capture());
        assertEquals(10L, customerIdCaptor.getValue().longValue());

        verify(response).setStatus(statusCaptor.capture());
        assertEquals(HttpServletResponse.SC_NOT_FOUND, statusCaptor.getValue().intValue());
    }
}
