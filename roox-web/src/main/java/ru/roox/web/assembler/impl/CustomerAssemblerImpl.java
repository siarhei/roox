package ru.roox.web.assembler.impl;

import com.google.common.base.Function;
import org.springframework.util.Assert;
import ru.roox.domain.Customer;
import ru.roox.domain.CustomerStatus;
import ru.roox.domain.NameInfo;
import ru.roox.web.assembler.AbstractAssembler;
import ru.roox.web.ws.model.CustomerDto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class CustomerAssemblerImpl extends AbstractAssembler<Customer, CustomerDto> {

    private static final Function<Customer, CustomerDto> ENTITY_TO_DTO = new Function<Customer, CustomerDto>() {
        @SuppressWarnings("ConstantConditions")
        @Nullable
        @Override
        public CustomerDto apply(@Nullable Customer entity) {
            CustomerDto dto = new CustomerDto();
            dto.setId(entity.getId());
            dto.setBalance(entity.getBalance());
            dto.setStatus(entity.getStatus() == null ? null:entity.getStatus().name());
            dto.setFirstName(entity.getNameInfo().getFirstName());
            dto.setMidName(entity.getNameInfo().getMidName());
            dto.setLastName(entity.getNameInfo().getLastName());

            return dto;
        }
    };

    @Override
    protected Customer convert(@Nonnull CustomerDto dto) {
        Customer customer = new Customer();
        updateCustomer(dto, customer);
        return customer;
    }

    protected void updateCustomer(CustomerDto dto, Customer customer) {
        customer.setBalance(dto.getBalance());
        customer.setStatus(dto.getStatus() == null ? null : CustomerStatus.valueOf(dto.getStatus()));

        NameInfo nameInfo = customer.getNameInfo();
        nameInfo.setFirstName(dto.getFirstName());
        nameInfo.setMidName(dto.getMidName());
        nameInfo.setLastName(dto.getLastName());
    }

    @Nonnull
    @Override
    protected Function<? super Customer, ? extends CustomerDto> getEntityDtoConverter() {
        return ENTITY_TO_DTO;
    }

    @Override
    public void updateEntity(Customer entity, CustomerDto dto) {
        Assert.notNull(entity);
        Assert.notNull(dto);

        updateCustomer(dto, entity);
    }
}
