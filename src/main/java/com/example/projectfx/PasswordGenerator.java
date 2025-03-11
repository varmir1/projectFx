package com.example.projectfx;

import java.util.Random;
import java.util.stream.Collectors;

public class PasswordGenerator {

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase,
                                   boolean includeDigits, boolean includeSpecialChars) {
        StringBuilder allowedChars = new StringBuilder();
        //se ajusta con las opciones que quiere la contraseña
        if (includeLowercase) allowedChars.append(LOWERCASE);
        if (includeUppercase) allowedChars.append(UPPERCASE);
        if (includeDigits) allowedChars.append(DIGITS);
        if (includeSpecialChars) allowedChars.append(SPECIAL_CHARS);

        if (allowedChars.length() == 0) {
            throw new IllegalArgumentException("You must select at least one type of character.");
        }
        //crear la contraseña random
        Random random = new Random();
        return random.ints(length, 0, allowedChars.length())
                .mapToObj(allowedChars::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}