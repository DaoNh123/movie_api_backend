package com.example.backend_sem2.mapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.lang.NonNull;

@Component
//@AllArgsConstructor
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ReferenceMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @ObjectFactory
    public <T> T map(@NonNull final Long id, @TargetType Class<T> type) {
        return entityManager.find(type, id);
    }

}