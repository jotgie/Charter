package com.charter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationTest {

    @Test
    void shouldValidateCorrectEmail() {
        assertTrue(EmailValidation.validateEmail("john@doe.com"));
    }

    @Test
    void shouldValidateWrongEmail() {
        assertFalse(EmailValidation.validateEmail("john.com"));
    }

}