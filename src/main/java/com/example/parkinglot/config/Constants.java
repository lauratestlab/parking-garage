package com.example.parkinglot.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";

    public static String SPRING_PROFILE_PRODUCTION = "prod";

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";

    public static final String MEMBER_DISCOUNT_SETTING = "MEMBER_DISCOUNT";

    public static final long DEFAULT_GARAGE_ID = 1L;

    private Constants() {}
}
