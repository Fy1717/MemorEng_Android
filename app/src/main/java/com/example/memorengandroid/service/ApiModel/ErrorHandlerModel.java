package com.example.memorengandroid.service.ApiModel;


public class ErrorHandlerModel {
    public static ErrorHandlerModel errorHandlerModel;

    public static String loginErrorMessage;
    public static String registerErrorMessage;
    public static String logoutErrorMessage;
    public static String getEnglishWordsErrorMessage;


    public static String getGetEnglishWordsErrorMessage() {
        return getEnglishWordsErrorMessage;
    }

    public static void setGetEnglishWordsErrorMessage(String getEnglishWordsErrorMessage) {
        ErrorHandlerModel.getEnglishWordsErrorMessage = getEnglishWordsErrorMessage;
    }

    public static String getLoginErrorMessage() {
        return loginErrorMessage;
    }

    public static void setLoginErrorMessage(String loginErrorMessage) {
        ErrorHandlerModel.loginErrorMessage = loginErrorMessage;
    }

    public static String getLogoutErrorMessage() {
        return logoutErrorMessage;
    }

    public static void setLogoutErrorMessage(String logoutErrorMessage) {
        ErrorHandlerModel.logoutErrorMessage = logoutErrorMessage;
    }

    public static String getRegisterErrorMessage() {
        return registerErrorMessage;
    }

    public static void setRegisterErrorMessage(String registerErrorMessage) {
        ErrorHandlerModel.registerErrorMessage = registerErrorMessage;
    }

    public static ErrorHandlerModel getInstance() {
        if (errorHandlerModel == null) {
            errorHandlerModel = new ErrorHandlerModel();
        }

        return errorHandlerModel;
    }

}
