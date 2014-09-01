package ru.roox.web.filter;

import ru.roox.web.constants.RooxHttpConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Holds additional request param (customer ID).
 * Has to be used instead of original Http Servlet Request
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class RooxHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Long customerId;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    private RooxHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customerId = null;
    }

    private RooxHttpServletRequestWrapper(HttpServletRequest request, Long customerId) {
        super(request);
        this.customerId = customerId;
    }

    public static RooxHttpServletRequestWrapper of(HttpServletRequest request, Long customerId) {
        return new RooxHttpServletRequestWrapper(request, customerId);
    }

    /**
     * It's used in servlet Filters
     */
    @Override
    public String getParameter(String name) {
        if (RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID.equals(name)) {
            return customerId == null ? null:customerId.toString();
        }
        return super.getParameter(name);
    }

    /**
     * It's used by Spring MVC for resolving request param marked by @RequestParam
     */
    @Override
    public String[] getParameterValues(String name) {
        if (RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID.equals(name)) {
            if (customerId != null) {
                return new String[]{customerId.toString()};
            }
        }
        return super.getParameterValues(name);
    }

    public Long getCustomerId() {
        return customerId;
    }
}
