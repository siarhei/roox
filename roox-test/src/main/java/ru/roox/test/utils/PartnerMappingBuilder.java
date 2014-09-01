package ru.roox.test.utils;

import org.apache.commons.lang3.builder.Builder;
import ru.roox.domain.PartnerMapping;

/**
 * Builder for PartnerMapping
 *
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 01.09.2014
 */
public class PartnerMappingBuilder implements Builder<PartnerMapping> {

    private final PartnerMapping pm;

    private PartnerMappingBuilder() {
        this.pm = new PartnerMapping();
    }

    public static PartnerMappingBuilder newBuilder() {
        return new PartnerMappingBuilder();
    }

    public PartnerMappingBuilder id(Long id) {
        pm.setId(id);
        return this;
    }

    public PartnerMappingBuilder partnerId(String partnerId) {
        pm.setPartnerId(partnerId);
        return this;
    }

    public PartnerMappingBuilder customerPartnerId(String customerPartnerId) {
        pm.setCustomerPartnerId(customerPartnerId);
        return this;
    }

    public PartnerMappingBuilder firstName(String firstName) {
        pm.getNameInfo().setFirstName(firstName);
        return this;
    }

    public PartnerMappingBuilder midName(String midName) {
        pm.getNameInfo().setMidName(midName);
        return this;
    }

    public PartnerMappingBuilder lastName(String lastName) {
        pm.getNameInfo().setLastName(lastName);
        return this;
    }

    @Override
    public PartnerMapping build() {
        return pm;
    }
}
