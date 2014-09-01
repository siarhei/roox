package ru.roox.service.dao.impl;

import ru.roox.domain.PartnerMapping;
import ru.roox.service.dao.PartnerMappingDao;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
public class PartnerMappingDaoImpl extends GenericDaoImpl<PartnerMapping> implements PartnerMappingDao {
    public PartnerMappingDaoImpl(Class<PartnerMapping> persistentClass) {
        super(persistentClass);
    }
}
