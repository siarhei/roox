package ru.roox.web.assembler;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import ru.roox.domain.BaseEntity;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 31.08.2014
 */
public abstract class AbstractAssembler<T extends BaseEntity, U extends Serializable> implements EntityAssembler<T,U> {
    @Override
    public T assembly(U dto) {
        Assert.notNull(dto);
        return convert(dto);
    }

    protected abstract T convert(@Nonnull U dto);

    @Override
    public U assembly(T entity) {
        Assert.notNull(entity);
        return getEntityDtoConverter().apply(entity);
    }

    @Override
    public List<U> assembly(List<T> entities) {
        return Lists.transform(entities, getEntityDtoConverter());
    }

    protected abstract @Nonnull Function<? super T,? extends U> getEntityDtoConverter();
}
