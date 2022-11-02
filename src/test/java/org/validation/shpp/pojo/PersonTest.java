package org.validation.shpp.pojo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNonValidName() {
        Person person = new Person().setName("Kloustrof").setCount(20).setDateOfCreated(LocalDateTime.now());

        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertEquals(1,constraintViolations.size());
        assertEquals("must contains \"a\"",constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testNonValidData() {
        Person person = new Person().setName("America").setCount(20).setDateOfCreated(null);

        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertEquals(1,constraintViolations.size());
        assertEquals("must be not null",constraintViolations.iterator().next().getMessage());
    }
    @Test
    void testNonValidCount() {
        Person person = new Person().setName("America").setCount(5).setDateOfCreated(LocalDateTime.now());

        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertEquals(1,constraintViolations.size());
        assertEquals("value should be more than 10",constraintViolations.iterator().next().getMessage());
    }
    @Test
    void testValidPerson() {
        Person person = new Person().setName("America").setCount(20).setDateOfCreated(LocalDateTime.now());

        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertEquals(0,constraintViolations.size());
    }

}