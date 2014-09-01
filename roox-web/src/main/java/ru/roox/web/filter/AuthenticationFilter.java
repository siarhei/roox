package ru.roox.web.filter;

import org.springframework.beans.factory.annotation.Required;
import ru.roox.service.CustomerService;
import ru.roox.web.constants.RooxHttpConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Checks existence of a customer by defined id (as request param)
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class AuthenticationFilter implements Filter {
    private CustomerService customerService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            String customerIdStr = request.getParameter(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID);
            Long customerId = Long.parseLong(customerIdStr);
            if (customerService.isExist(customerId)) {
                chain.doFilter(request, response);
            } else {
                //not found customer
                ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Required
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
