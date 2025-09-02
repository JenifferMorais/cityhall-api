package com.foo.cityhall.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

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