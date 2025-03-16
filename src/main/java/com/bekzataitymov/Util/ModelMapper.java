package com.bekzataitymov.Util;

public class ModelMapper {
    private static final org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    public static <D, T>D convertDtoToEntity(T dto, Class<D> entityClass){
        return modelMapper.map(dto, entityClass);
    }

    public static <D, T>D convertEntityToDto(T entity, Class<D> dtoClass){
        return modelMapper.map(entity, dtoClass);
    }
}
