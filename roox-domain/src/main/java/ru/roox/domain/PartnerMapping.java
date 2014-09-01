package ru.roox.domain;

import javax.persistence.*;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@Entity
public class PartnerMapping extends BaseEntity {
    @Column(length = ConstraintConstants.REFERENCE_LENGTH)
    private String partnerId;

    @Column(length = ConstraintConstants.REFERENCE_LENGTH)
    private String customerPartnerId;

    @Embedded
    private NameInfo nameInfo;

    @Embedded
    private AvatarInfo avatar;

    @ManyToOne(optional = false)
    @JoinColumn(name = Customer.FK_NAME)
    private Customer customer;

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

    public NameInfo getNameInfo() {
        if (nameInfo == null) {
            nameInfo = new NameInfo();
        }
        return nameInfo;
    }

    public void setNameInfo(NameInfo nameInfo) {
        this.nameInfo = nameInfo;
    }

    public AvatarInfo getAvatar() {
        if (avatar == null) {
            avatar = new AvatarInfo();
        }
        return avatar;
    }

    public void setAvatar(AvatarInfo avatar) {
        this.avatar = avatar;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
