package com.compscieddy.didwhat;

import com.compscieddy.didwhat.model.User;

/**
 * Created by elee on 2/22/16.
 */
public class Constants {

  public static final String FIREBASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
  public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + "users";
  public static final String FIREBASE_URL_DODAYS = FIREBASE_URL + "/" + "dodays";
  public static final String FIREBASE_URL_DOS = FIREBASE_URL + "/" + "dos";
  public static final String FIREBASE_URL_DOSKILLS = FIREBASE_URL + "/" + "doskills";

  /** Specific Field Location Mappings */
  public static String FIREBASE_URL_USER_DOSKILL_MAPPING; // populated if user logged in

  /** Preference Key */
  public static final String PREF_KEY_EMAIL = "pref_key_email";

  public static void initConstants(String encodedEmail) {
    FIREBASE_URL_USER_DOSKILL_MAPPING = FIREBASE_URL_USERS + "/" + encodedEmail + "/" + User.DOSKILLS_MAPPING;
  }

}
