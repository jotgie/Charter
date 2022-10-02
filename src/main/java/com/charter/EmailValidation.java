package com.charter;

import java.util.regex.Pattern;

public final class EmailValidation {

    public static boolean validateEmail(String emailAddress) {
        return Pattern.compile("^(.+)@(\\S+)$")
                .matcher(emailAddress)
                .matches();
    }

}
