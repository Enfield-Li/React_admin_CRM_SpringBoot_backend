package com.example.demo.util;

public class Constants {

  public static final String TEST = "/test";
  public static final String LOGIN = "/login";
  public static final String LOGOUT = "/logout";
  public static final String REGISTER = "/register";
  public static final String UPDATE_ROLE = "/update_role";

  public static final String TAGS_ENDPOINT = "/tags";
  public static final String TASKS_ENDPOINT = "/tasks";
  public static final String DEALS_ENDPOINT = "/deals";
  public static final String SALES_ENDPOINT = "/sales";
  public static final String CONTACTS_ENDPOINT = "/contacts";
  public static final String DEALNOTES_ENDPOINT = "/dealNotes";
  public static final String COMPANIES_ENDPOINT = "/companies";
  public static final String CONTACTNOTES_ENDPOINT = "/contactNotes";

  public static final String TEST_ENDPOINT = SALES_ENDPOINT + TEST;
  public static final String LOGIN_ENDPOINT = SALES_ENDPOINT + LOGIN;
  public static final String LOGOUT_ENDPOINT = SALES_ENDPOINT + LOGOUT;
  public static final String REGISTER_ENDPOINT = SALES_ENDPOINT + REGISTER;
  public static final String UPDATE_SALE_ENDPOINT =
    SALES_ENDPOINT + UPDATE_ROLE;

  public static final String SWAGGER_UI_PATH_1 = "/swagger-ui/**";
  public static final String SWAGGER_UI_PATH_2 = "/v3/api-docs/**";
  public static final String ApplicationUserInSession =
    "application_user_in_session";
}
