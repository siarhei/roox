package ru.roox.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@Entity
public class Customer extends BaseEntity {
    public static final String FK_NAME = "CUSTOMER_ID";

    @Embedded
    private NameInfo nameInfo;

    private BigDecimal balance;

    @Column(length = ConstraintConstants.CODE_LENGTH, nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Embedded
    private CustomerCredentials credentials;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OrderBy("id")
    @MapKey(name = "id")
    private Map<Long, PartnerMapping> partnerMappings = Maps.newHashMap();

    public Customer() {
        this.balance = BigDecimal.ZERO;
        this.status = CustomerStatus.active;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public CustomerCredentials getCredentials() {
        if (credentials == null) {
            credentials = new CustomerCredentials();
        }
        return credentials;
    }

    public void setCredentials(CustomerCredentials credentials) {
        this.credentials = credentials;
    }

    public Map<Long, PartnerMapping> getPartnerMappings() {
        return partnerMappings;
    }

    public List<PartnerMapping> getPartnerMappingsAsList() {
        return ImmutableList.copyOf(partnerMappings.values());
    }

    public void setPartnerMappings(Map<Long, PartnerMapping> partnerMappings) {
        this.partnerMappings = partnerMappings;
    }
}
