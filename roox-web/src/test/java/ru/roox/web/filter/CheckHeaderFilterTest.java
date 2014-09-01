package ru.roox.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.roox.web.constants.RooxHttpConstants;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link CheckHeaderFilter}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class CheckHeaderFilterTest {
    private CheckHeaderFilter testedInstance = new CheckHeaderFilter();

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Captor
    private ArgumentCaptor<Integer> statusCaptor;
    @Captor
    private ArgumentCaptor<RooxHttpServletRequestWrapper> requestCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoFilter_authHeaderIsAbsent() throws Exception {
        when(request.getHeader(eq(RooxHttpConstants.HEADER_ATTR_AUTH))).thenReturn(null);

        testedInstance.doFilter(request, response, chain);

        verify(response).setStatus(statusCaptor.capture());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, statusCaptor.getValue().intValue());
    }

    @Test
    public void testDoFilter_badRequest() throws Exception {
        when(request.getHeader(eq(RooxHttpConstants.HEADER_ATTR_AUTH))).thenReturn("Bearer 1A");

        testedInstance.doFilter(request, response, chain);

        verify(response).setStatus(statusCaptor.capture());
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, statusCaptor.getValue().intValue());
    }

    @Test
    public void testDoFilter() throws Exception {
        when(request.getHeader(eq(RooxHttpConstants.HEADER_ATTR_AUTH))).thenReturn("Bearer 105");

        testedInstance.doFilter(request, response, chain);

        verify(chain).doFilter(requestCaptor.capture(), eq(response));
        assertEquals(105L, requestCaptor.getValue().getCustomerId().longValue());
    }
}
