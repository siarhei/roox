package ru.roox.web.ws.model;

import java.io.Serializable;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class PartnerMappingDto implements Serializable {
    private static final long serialVersionUID = 1365977354294666632L;

    private Long id;
    private String partnerId;
    private String customerPartnerId;
    private String customerPartnerFirstName;
    private String customerPartnerMidName;
    private String customerPartnerLastName;
    private String avatarName;
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getCustomerPartnerId() {
        return customerPartnerId;
    }

    public void setCustomerPartnerId(String customerPartnerId) {
        this.customerPartnerId = customerPartnerId;
    }

    public String getCustomerPartnerFirstName() {
        return customerPartnerFirstName;
    }

    public void setCustomerPartnerFirstName(String customerPartnerFirstName) {
        this.customerPartnerFirstName = customerPartnerFirstName;
    }

    public String getCustomerPartnerMidName() {
        return customerPartnerMidName;
    }

    public void setCustomerPartnerMidName(String customerPartnerMidName) {
        this.customerPartnerMidName = customerPartnerMidName;
    }

    public String getCustomerPartnerLastName() {
        return customerPartnerLastName;
    }

    public void setCustomerPartnerLastName(String customerPartnerLastName) {
        this.customerPartnerLastName = customerPartnerLastName;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
