package com.example.projectfx;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]");

    public static boolean hasLowerCase(String password) {
        return LOWERCASE_PATTERN.matcher(password).find();
    }

    public static boolean hasUpperCase(String password) {
        return UPPERCASE_PATTERN.matcher(password).find();
    }

    public static boolean hasNumber(String password) {
        return NUMBER_PATTERN.matcher(password).find();
    }

    public static boolean hasSpecialChar(String password) {
        return SPECIAL_CHAR_PATTERN.matcher(password).find();
    }

    public static boolean validatePassword(String password) {
        return hasLowerCase(password) && 
               hasUpperCase(password) && 
               hasNumber(password) && 
               hasSpecialChar(password);
    }
}