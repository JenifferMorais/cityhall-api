package com.foo.cityhall.utils;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeBetweenValidator implements ConstraintValidator<AgeBetween, LocalDate> {

    private int min;
    private int max;

    @Override
    public void initialize(AgeBetween ann) {
        this.min = ann.min();
        this.max = ann.max();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext ctx) {
        if (value == null) return true; 
        int age = Period.between(value, LocalDate.now()).getYears();
        return age >= min && age <= max;
    }
}