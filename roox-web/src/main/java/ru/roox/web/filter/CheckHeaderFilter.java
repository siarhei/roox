package ru.roox.web.filter;

import ru.roox.web.constants.RooxHttpConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks and parses Http Header, puts customer ID (if found) into request params
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class CheckHeaderFilter implements Filter {

    public static final Pattern PATTERN = Pattern.compile("^Bearer ([\\d]+)$");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;

            String authHeader = httpRequest.getHeader(RooxHttpConstants.HEADER_ATTR_AUTH);
            if (authHeader == null) {
                //Authorization header is absent
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                Matcher matcher = PATTERN.matcher(authHeader);
                if (matcher.matches()) {
                    String customerIdStr = matcher.group(1);
                    Long customerId = Long.parseLong(customerIdStr);
                    chain.doFilter(RooxHttpServletRequestWrapper.of(httpRequest, customerId), response);
                } else {
                    //incorrect format of header
                    httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }
}
