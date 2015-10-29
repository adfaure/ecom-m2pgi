package fr.ujf.m2pgi.database.Mappers;

/**
 * Created by dadou on 29/10/15.
 */
public interface IGeneriqueMapper<DTOType, EntityType> {

    DTOType getDTO(EntityType entity);

    EntityType getentity(DTOType dto);

}
