package ru.roox.service.impl;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.roox.domain.Customer;
import ru.roox.domain.PartnerMapping;
import ru.roox.service.CustomerService;
import ru.roox.service.dao.CustomerDao;
import ru.roox.service.dao.PartnerMappingDao;

import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link CustomerService}
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao;
    private PartnerMappingDao partnerMappingDao;

    @Transactional(readOnly = true)
    @Override
    public boolean isExist(Long customerId) {
        return customerId != null && customerDao.findById(customerId) != null;
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findById(Long customerId) {
        if (customerId == null) {
            return null;
        }
        return customerDao.findById(customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public PartnerMapping findPartnerById(Long partnerId) {
        if (partnerId == null) {
            return null;
        }
        return partnerMappingDao.findById(partnerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public PartnerMapping findBy(Long customerId, Long partnerId) {
        if (customerId == null || partnerId == null) {
            return null;
        }

        Customer customer = this.findById(customerId);
        if (customer == null) {
            return null;
        }

        return customer.getPartnerMappings().get(partnerId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PartnerMapping> findAll(Long customerId) {
        Customer customer = this.findById(customerId);
        if (customer == null) {
            return Collections.emptyList();
        }
        return customer.getPartnerMappingsAsList();
    }

    @Transactional
    @Override
    public void deleteAllPartners(Long customerId) {
        Customer customer = this.findById(customerId);
        if (customer == null) {
            return;
        }
        customer.getPartnerMappings().clear();
        customerDao.update(customer);
    }

    @Transactional
    @Override
    public void deletePartner(Long customerId, Long partnerId) {
        PartnerMapping partnerMapping = this.findPartnerById(partnerId);
        if (partnerMapping == null || customerId == null) {
            return;
        }

        Customer customer = this.findById(customerId);
        if (customer == null) {
            return;
        }
        customer.getPartnerMappings().remove(partnerId);
        customerDao.update(customer);
    }

    @Override
    public PartnerMapping createFor(Long customerId, PartnerMapping partner) {
        Assert.notNull(customerId);
        Assert.notNull(partner);
        Assert.isNull(partner.getId());

        Customer customer = this.findById(customerId);
        Assert.notNull(customer);

        partner.setCustomer(customer);

        return partnerMappingDao.save(partner);
    }

    @Override
    public PartnerMapping update(PartnerMapping partner) {
        Assert.notNull(partner);
        return partnerMappingDao.update(partner);
    }

    @Required
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Required
    public void setPartnerMappingDao(PartnerMappingDao partnerMappingDao) {
        this.partnerMappingDao = partnerMappingDao;
    }
}
