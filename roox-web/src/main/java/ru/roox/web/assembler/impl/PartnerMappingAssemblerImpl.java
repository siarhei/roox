package ru.roox.web.assembler.impl;

import com.google.common.base.Function;
import org.springframework.util.Assert;
import ru.roox.domain.NameInfo;
import ru.roox.domain.PartnerMapping;
import ru.roox.web.assembler.AbstractAssembler;
import ru.roox.web.ws.model.PartnerMappingDto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public class PartnerMappingAssemblerImpl extends AbstractAssembler<PartnerMapping, PartnerMappingDto> {

    private static final Function<PartnerMapping, PartnerMappingDto> ENTITY_TO_DTO = new Function<PartnerMapping, PartnerMappingDto>() {
        @SuppressWarnings("ConstantConditions")
        @Nullable
        @Override
        public PartnerMappingDto apply(@Nullable PartnerMapping entity) {
            Assert.notNull(entity);

            PartnerMappingDto dto = new PartnerMappingDto();
            dto.setId(entity.getId());
            dto.setAvatarName(entity.getAvatar().getName());
            dto.setCustomerId(entity.getCustomer().getId());
            dto.setPartnerId(entity.getPartnerId());
            dto.setCustomerPartnerId(entity.getCustomerPartnerId());
            dto.setCustomerPartnerFirstName(entity.getNameInfo().getFirstName());
            dto.setCustomerPartnerLastName(entity.getNameInfo().getLastName());
            dto.setCustomerPartnerMidName(entity.getNameInfo().getMidName());

            return dto;
        }
    };

    @Override
    protected PartnerMapping convert(@Nonnull PartnerMappingDto dto) {
        Assert.notNull(dto);

        PartnerMapping entity = new PartnerMapping();
        updatePartnerMapping(dto, entity);

        return entity;
    }

    private void updatePartnerMapping(PartnerMappingDto dto, PartnerMapping entity) {
        entity.setPartnerId(dto.getPartnerId());
        entity.setCustomerPartnerId(dto.getCustomerPartnerId());

        NameInfo nameInfo = entity.getNameInfo();
        nameInfo.setFirstName(dto.getCustomerPartnerFirstName());
        nameInfo.setMidName(dto.getCustomerPartnerMidName());
        nameInfo.setLastName(dto.getCustomerPartnerLastName());
    }

    @Nonnull
    @Override
    protected Function<? super PartnerMapping, ? extends PartnerMappingDto> getEntityDtoConverter() {
        return ENTITY_TO_DTO;
    }

    @Override
    public void updateEntity(PartnerMapping entity, PartnerMappingDto dto) {
        Assert.notNull(entity);
        Assert.notNull(dto);

        updatePartnerMapping(dto, entity);
    }
}
