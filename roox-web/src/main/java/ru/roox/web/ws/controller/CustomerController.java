package ru.roox.web.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.roox.domain.PartnerMapping;
import ru.roox.web.constants.RooxHttpConstants;
import ru.roox.web.ws.errors.EntityNotFoundException;
import ru.roox.web.ws.errors.ForbiddenActionException;
import ru.roox.web.ws.model.CustomerDto;
import ru.roox.web.ws.model.PartnerMappingDto;
import ru.roox.web.ws.model.Response;
import ru.roox.web.ws.model.SuccessResponse;

import java.util.List;

/**
 * REST controller for @Customer
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
@Controller
public class CustomerController extends AbstractController {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Gets all customers
     *
     * @return list of all customers in JSON
     */
    @RequestMapping(value = "/ws/customers", method = RequestMethod.GET)
    public @ResponseBody
    List<CustomerDto> getCustomers() {
        logger.info("Tries to get all customers");

        return customerAssembler.assembly(customerService.findAll());
    }

    /**
     * Get customer by defined customer id
     *
     * @param customerId customer id
     * @return customer in JSON
     */
    @RequestMapping(value = "/ws/customers/{id}", method = RequestMethod.GET)
    public @ResponseBody
    CustomerDto getCustomer(@PathVariable("id") Long customerId) {
        logger.info("Tries to find customer by id={}", customerId);

        return customerAssembler.assembly(getCustomerById(customerId));
    }

    @RequestMapping(value = "/ws/customers/{id}/partners", method = RequestMethod.GET)
    public @ResponseBody
    List<PartnerMappingDto> getPartners(@PathVariable("id") Long customerId) {
        logger.info("Tries to get partners for customer = {}", customerId);

        return getPartnersForCustomer(customerId);
    }

    @RequestMapping(value = "/ws/customers/{id}/partners/{pid}", method = RequestMethod.GET)
    public @ResponseBody
    PartnerMappingDto getPartner(@PathVariable("id") Long customerId, @PathVariable("pid") Long partnerId) {
        logger.info("Tries to get partner = {} for customer = {}", partnerId, customerId);

        return getPartnerByCustomer(customerId, partnerId);
    }

    @RequestMapping(value = "/ws/customers/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteCustomer(@PathVariable("id") Long customerId) {
        logger.info("Tries to delete customer = {}", customerId);
        throw new ForbiddenActionException("Impossible to delete customer");
    }

    @RequestMapping(value = "/ws/customers/{id}/partners", method = RequestMethod.DELETE)
    public @ResponseBody
    Response deletePartners(@PathVariable("id") Long customerId,
                       @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId) {
        logger.info("Tries to delete all partners for customer = {}", customerId);
        checkAuthorization(customerId, authCustomerId);
        customerService.deleteAllPartners(customerId);

        return SuccessResponse.getInstance();
    }

    @RequestMapping(value = "/ws/customers/{id}/partners/{pid}", method = RequestMethod.DELETE)
    public @ResponseBody
    Response deletePartner(@PathVariable("id") Long customerId,
                       @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
                       @PathVariable("pid") Long partnerId) {
        logger.info("Tries to delete partner = {} for customer = {}", partnerId, customerId);
        checkAuthorization(customerId, authCustomerId);
        customerService.deletePartner(customerId, partnerId);

        return SuccessResponse.getInstance();
    }

    @RequestMapping(value = "/ws/customers", method = RequestMethod.POST)
    public @ResponseBody
    void createCustomer() {
        logger.error("Impossible to create customer");
        throw new ForbiddenActionException("Impossible to create customer");
    }

    @RequestMapping(value = "/ws/customers/{id}/partners", method = RequestMethod.POST)
    public @ResponseBody
    PartnerMappingDto createPartnerForCustomer(@PathVariable("id") Long customerId,
                                  @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
                                  @RequestBody PartnerMappingDto dto) {
        logger.info("Tries to create new partner for customer = {}", customerId);
        checkAuthorization(customerId, authCustomerId);
        return  partnerMappingAssembler.assembly(
                    customerService.createFor(customerId,
                            partnerMappingAssembler.assembly(dto)));
    }

    @RequestMapping(value = "/ws/customers/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    PartnerMappingDto updateCustomer(@PathVariable("id") Long customerId) {
        String errorMsg = "Impossible to update customer";

        logger.error(errorMsg);
        throw new ForbiddenActionException(errorMsg);
    }

    @RequestMapping(value = "/ws/customers/{id}/partners/{pid}", method = RequestMethod.PUT)
    public @ResponseBody
    PartnerMappingDto updatePartnerForCustomer(@PathVariable("id") Long customerId,
                                               @PathVariable("pid") Long partnerId,
                                               @RequestParam(RooxHttpConstants.REQUEST_PARAM_CUSTOMER_ID) Long authCustomerId,
                                               @RequestBody PartnerMappingDto dto) {
        logger.info("Tries to update partner for customer = {}", customerId);
        checkAuthorization(customerId, authCustomerId);

        PartnerMapping partnerMapping = customerService.findBy(customerId, partnerId);
        if (partnerMapping == null) {
            logger.error("Partner not found");
            throw new EntityNotFoundException(String.format("Partner mapping not found (id=%d) for customer (id=%d)", partnerId, customerId));
        }

        partnerMappingAssembler.updateEntity(partnerMapping, dto);
        return  partnerMappingAssembler.assembly(customerService.update(partnerMapping));
    }
}
