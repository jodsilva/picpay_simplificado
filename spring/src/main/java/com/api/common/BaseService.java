package com.api.common;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T> {
    
    protected final JpaRepository<T, Long> repository;

    protected BaseService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    /**
     * Find a register
     * 
     * @param id
     * @return UserModel
     */
    public T findOrFail(Long id){
        return this.repository.findById(id).orElseThrow(() -> new RuntimeException("Registro não encontrado"));
    }
}
