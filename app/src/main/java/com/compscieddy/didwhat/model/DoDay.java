package com.compscieddy.didwhat.model;

import android.content.Context;
import android.text.TextUtils;

import com.compscieddy.didwhat.Constants;
import com.compscieddy.eddie_utils.Lawg;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by elee on 3/1/16.
 */
public class DoDay extends FirebaseObject {

  private static final Lawg lawg = Lawg.newInstance(DoDay.class.getSimpleName());

  private Date date; private static final String DATE = "date";
  private String doDayKey; private static final String DO_DAY_KEY = "doDayKey";

  @Override
  public void deleteFirebase(Context context) {

  }

  @Override
  protected String getObjectUrl() {
    return Constants.FIREBASE_URL_DODAYS;
  }

  public DoDay() {}

  public DoDay(String key, Date date) {
    super(key);
    this.date = date;
  }

  /** Getters */

  public Date getDate() {
    return date;
  }
  public String getDoDayKey() {
    return doDayKey;
  }

  /** Setters */

  public void setDate(Date date) {
    this.date = date;
    updateFirebase(DATE, date);
  }
  public void setDoDayKey(String doDayKey) {
    this.doDayKey = doDayKey;
    updateFirebase(DO_DAY_KEY, doDayKey);
  }

  /** Methods */

  public boolean foundSameDay(String encodedEmail, Date date) {
    return TextUtils.equals(getKey(), createKey(encodedEmail, date));
  }

  /** Static Methods */

  public static boolean isSameDay(DoDay doDay1, DoDay doDay2) {
    Date date1 = doDay1.getDate();
    Date date2 = doDay2.getDate();
    return isSameDay(date1, date2);
  }

  public static boolean isSameDay(Date date1, Date date2) {
    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();
    c1.setTime(date1);
    c2.setTime(date2);Date d = new Date();

    return ((c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
        && (c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
        && (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)));
  }

  public static String convertDateToKey(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    StringBuilder builder = new StringBuilder();
    builder.append(c.get(Calendar.YEAR));
    builder.append("-");
    builder.append(c.get(Calendar.MONTH) + 1); // 0-indexed
    builder.append("-");
    builder.append(c.get(Calendar.DAY_OF_MONTH));
    return builder.toString();
  }

  /** To ensure ClockDays are all unique, they are the concatenation of the user key and date */
  public static String createKey(String encodedEmail, Date d) {
    // Remember: the key for a User is the encoded email
    return encodedEmail + "-" + DoDay.convertDateToKey(d);
  }

}
