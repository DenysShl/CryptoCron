package com.example.cryptocron.model.mapper;

public interface MapperToModel<V, T> {
    V toModel(T t);
}
