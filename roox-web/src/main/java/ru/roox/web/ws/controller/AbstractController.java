package ru.roox.web.ws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.service.CustomerService;
import ru.roox.web.assembler.EntityAssembler;
import ru.roox.web.ws.errors.EntityNotFoundException;
import ru.roox.web.ws.errors.ForbiddenActionException;
import ru.roox.web.ws.model.CustomerDto;
import ru.roox.web.ws.model.PartnerMappingDto;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Base controller
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public abstract class AbstractController {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected CustomerService customerService;
    protected EntityAssembler<Customer, CustomerDto> customerAssembler;
    protected EntityAssembler<PartnerMapping, PartnerMappingDto> partnerMappingAssembler;

    protected Customer getCustomerById(Long customerId) {
        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            throw new EntityNotFoundException(String.format("Customer [id=%s] not found", customerId));
        }
        return customer;
    }

    protected List<PartnerMappingDto> getPartnersForCustomer(Long customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            throw new EntityNotFoundException(String.format("Customer [id=%s] not found", customerId));
        }
        return partnerMappingAssembler.assembly(customer.getPartnerMappingsAsList());
    }

    protected PartnerMappingDto getPartnerByCustomer(Long customerId, Long partnerId) {
        PartnerMapping partnerMapping = customerService.findBy(customerId, partnerId);
        if (partnerMapping == null) {
            throw new EntityNotFoundException(String.format("Partner mapping not found (id=%d) for customer (id=%d)", partnerId, customerId));
        }
        return partnerMappingAssembler.assembly(partnerMapping);
    }

    protected void checkAuthorization(@Nonnull Long customerId, @Nonnull Long authCustomerId) {
        if (!customerId.equals(authCustomerId)) {
            String message = String.format("Authorized customer has no privileges to change another customer (id=%d)", customerId);
            logger.error(message);
            throw new ForbiddenActionException(message);
        }
    }

    @Autowired
    public void setCustomerAssembler(EntityAssembler<Customer, CustomerDto> customerAssembler) {
        this.customerAssembler = customerAssembler;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setPartnerMappingAssembler(EntityAssembler<PartnerMapping, PartnerMappingDto> partnerMappingAssembler) {
        this.partnerMappingAssembler = partnerMappingAssembler;
    }
}
