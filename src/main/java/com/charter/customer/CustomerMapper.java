package com.charter.customer;

import com.charter.CustomerApi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    Customer apiToDomain(CustomerApi customerApi);
    CustomerApi domainToApi(Customer customer);

}
