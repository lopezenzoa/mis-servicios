package com.group.mis_servicios.service;

import java.util.List;
import java.util.Optional;

public interface I_Service<T> {
    List<?> getAll();
    Optional<T> getById(Long id);
    Optional<?> create(T type);
    Optional<?> update(Long id, T newType);
    boolean delete(Long id);
}
