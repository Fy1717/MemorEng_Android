package com.example.memorengandroid.model;

public class PageNavigator {
    private static String fromPageName = "";

    public static String getFromPageName() {
        return fromPageName;
    }

    public static void setFromPageName(String fromPageName) {
        PageNavigator.fromPageName = fromPageName;
    }

    private static PageNavigator pageNavigator;

    private PageNavigator() {}

    public static PageNavigator getInstance() {
        if (pageNavigator == null) {
            pageNavigator = new PageNavigator();
        }

        return pageNavigator;
    }
}
