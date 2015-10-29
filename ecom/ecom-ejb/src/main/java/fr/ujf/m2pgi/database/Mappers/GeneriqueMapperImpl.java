package fr.ujf.m2pgi.database.Mappers;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by FAURE Adrien on 29/10/15.
 */
public abstract class GeneriqueMapperImpl<DTOType, EntityType> implements IGeneriqueMapper<DTOType, EntityType> {

    /**
     *
     */
    protected Class<EntityType> entityClass;

    /**
     *
     */
    protected Class<DTOType> dtoClass;

    /**
     *
     */
    protected ModelMapper modelMapper;


    /**
     *
     */
    public GeneriqueMapperImpl() {
        modelMapper = new ModelMapper();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = ((Class<EntityType>) pt.getActualTypeArguments()[0]);
        dtoClass = ((Class<DTOType>) pt.getActualTypeArguments()[0]);
    }

    @Override
    public DTOType getDTO(EntityType entity) {
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public EntityType getentity(DTOType dto) {
        return modelMapper.map(dto, entityClass);
    }

}
