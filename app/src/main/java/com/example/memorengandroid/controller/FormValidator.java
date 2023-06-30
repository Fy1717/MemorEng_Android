package com.example.memorengandroid.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator {
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static String onlyChars(String str, Integer lengthControl) {
        String resultStr = "";

        int n = str.length();

        for (int i = 0; i < n; i++) {
            if (Character.isAlphabetic(str.charAt(i)) || str.charAt(i) == ' ') {
                resultStr += str.charAt(i);
            }
        }

        if(resultStr.length() < lengthControl) {
            resultStr = "";
        }

        return resultStr;
    }

    public static Boolean validateName(String nameFromForm) {
        Boolean status = false;
        String reqResult = onlyChars(nameFromForm, 2);

        if (!reqResult.equals("") && reqResult.equals(nameFromForm)) {
            status = true;
        }

        return status;
    }


    public static Boolean validateEmail(String emailFromForm) {
        Boolean status = false;

        if (emailFromForm.indexOf("@") > 0 &&
                emailFromForm.length() > 6 &&
                emailFromForm.indexOf(".") > 0) {
            status = true;
        }

        return status;
    }

    public static Boolean validatePassword(String passwordFromForm) {
        Matcher matcher = pattern.matcher(passwordFromForm);

        return matcher.matches();
    }

    public boolean validatePasswordMatch(String password, String passwordRepeat) {
        Boolean status = false;

        if (password.equals(passwordRepeat)) {
            status = true;
        }

        return status;
    }
}

