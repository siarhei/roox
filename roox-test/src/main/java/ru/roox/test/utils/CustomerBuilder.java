package ru.roox.test.utils;

import org.apache.commons.lang3.builder.Builder;
import ru.roox.domain.Customer;
import ru.roox.domain.CustomerStatus;
import ru.roox.domain.PartnerMapping;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Builder for Customer
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class CustomerBuilder implements Builder<Customer> {

    private final Customer customer;

    private CustomerBuilder() {
        this.customer = new Customer();
    }

    public static CustomerBuilder newBuilder() {
        return new CustomerBuilder();
    }

    public CustomerBuilder id(Long id) {
        customer.setId(id);
        return this;
    }

    public CustomerBuilder firstName(String firstName) {
        customer.getNameInfo().setFirstName(firstName);
        return this;
    }

    public CustomerBuilder midName(String midName) {
        customer.getNameInfo().setMidName(midName);
        return this;
    }

    public CustomerBuilder lastName(String lastName) {
        customer.getNameInfo().setLastName(lastName);
        return this;
    }

    public CustomerBuilder balance(BigDecimal balance) {
        customer.setBalance(balance);
        return this;
    }

    public CustomerBuilder status(CustomerStatus status) {
        customer.setStatus(status);
        return this;
    }

    public CustomerBuilder partners(Collection<PartnerMapping> partnerMappingCollection) {
        for (PartnerMapping partnerMapping : partnerMappingCollection) {
            customer.getPartnerMappings().put(partnerMapping.getId(), partnerMapping);
            partnerMapping.setCustomer(customer);
        }
        return this;
    }

    @Override
    public Customer build() {
        return customer;
    }
}
