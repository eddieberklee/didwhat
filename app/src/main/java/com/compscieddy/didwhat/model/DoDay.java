package com.compscieddy.didwhat.model;

import android.content.Context;

import com.compscieddy.didwhat.Constants;
import com.compscieddy.eddie_utils.Lawg;

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

}
