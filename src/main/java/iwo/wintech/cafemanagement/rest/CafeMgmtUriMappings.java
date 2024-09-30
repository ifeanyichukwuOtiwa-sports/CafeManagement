package iwo.wintech.cafemanagement.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CafeMgmtUriMappings {
    public static final String BASE_URI = "/api/cafe/v1";
    public static final String USER_AUTH_URI = BASE_URI + "/user/auth";
    public static final String USER_URI = BASE_URI + "/user";
    public static final String USER_MANAGEMENT_URI = BASE_URI + "/admin/user/management";

    public static final String ADMIN_AUTH_URI = BASE_URI + "/admin/auth";


    public static final String CATEGORY_URI = BASE_URI + "/category";
}
