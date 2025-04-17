package com.aitutor.util;

public class PasswordValidator {
    public static boolean isValidPasswordFormat(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*\\d.*") && // Contains number
                password.matches(".*[A-Z].*") && // Contains uppercase
                password.matches(".*[a-z].*"); // Contains lowercase
    }

    public static boolean isPasswordMatch(String inputPassword, String storedPassword) {
        return inputPassword != null && inputPassword.equals(storedPassword);
    }
}
