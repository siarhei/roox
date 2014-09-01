package ru.roox.service;

import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;

import java.util.List;

/**
 * Service for working with customers and partners
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public interface CustomerService {
    /**
     * Checks existence of customer by id
     *
     * @param customerId customer id
     * @return true if customer exists; false otherwise
     */
    boolean isExist(Long customerId);

    /**
     * Finds customer by id
     *
     * @param customerId customer id
     * @return customer if found or null if not
     */
    Customer findById(Long customerId);

    /**
     * Finds partner by id
     *
     * @param partnerId partner id
     * @return partner if found or null if not
     */
    PartnerMapping findPartnerById(Long partnerId);

    /**
     * Finds all customers
     *
     * @return list of customers
     */
    List<Customer> findAll();

    /**
     * Finds partner by id and customer id
     *
     * @param customerId customer id
     * @param partnerId partner id
     * @return partner if found or null if not
     */
    PartnerMapping findBy(Long customerId, Long partnerId);

    /**
     * Finds all partners by customer
     *
     * @param customerId customer id
     * @return list of all partners for customer
     */
    List<PartnerMapping> findAll(Long customerId);

    /**
     * Deletes all partners in defined customer
     *
     * @param customerId customer id
     */
    void deleteAllPartners(Long customerId);

    /**
     * Deletes partner by id and customer id
     *
     * @param customerId customer id
     * @param partnerId partner id
     */
    void deletePartner(Long customerId, Long partnerId);

    /**
     * Stores partner for defined customer
     *
     * @param customerId customer id
     * @param partner partner to persist
     * @return persisted partner
     */
    PartnerMapping createFor(Long customerId, PartnerMapping partner);

    /**
     * Persists partner
     *
     * @param partner partner to persist
     * @return persisted partner
     */
    PartnerMapping update(PartnerMapping partner);
}
