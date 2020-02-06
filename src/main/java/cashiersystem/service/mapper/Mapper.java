package cashiersystem.service.mapper;

public interface Mapper<E, D> {

    E mapDomainToEntity(D domain);

    D mapEntityToDomain(E entity);
}
