package com.example.cryptocron.model.mapper;

public interface MapperToDto<U,V> {
    U toDto(V v);
}
