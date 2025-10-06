package com.api.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckUniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckUnique {

    String message() default "Valor já existe no banco";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Model with field to validate
     */
    Class<?> entityClass();

    /**
     * Field name to validate
     */
    String fieldName();
}