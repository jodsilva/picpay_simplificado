package com.api.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Component
public class CheckUniqueValidator implements ConstraintValidator<CheckUnique, Object> {

    private final EntityManager em;
    private Class<?> entityClass;
    private String fieldName;

    public CheckUniqueValidator(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(CheckUnique annotation) {
        this.entityClass = annotation.entityClass();
        this.fieldName = annotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String builder = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() +
                        " e WHERE e." + fieldName + " = :value";

        TypedQuery<Long> query = em.createQuery(builder, Long.class);
        query.setParameter("value", value);

        Long count = query.getSingleResult();
        boolean isValid = count == 0;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                context.getDefaultConstraintMessageTemplate()
            ).addConstraintViolation();
        }

        return isValid;
    }
}
