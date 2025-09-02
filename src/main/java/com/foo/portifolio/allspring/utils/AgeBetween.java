package com.foo.portifolio.allspring.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeBetweenValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeBetween {
	String message() default "A idade deve estar entre {min} e {max} anos";

	int min() default 18;

	int max() default 75;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}