package com.compscieddy.didwhat.model;

import android.content.Context;

import com.compscieddy.didwhat.Constants;
import com.compscieddy.eddie_utils.Lawg;

/**
 * Created by elee on 3/1/16.
 */
public class Do extends FirebaseObject {

  private static final Lawg lawg = Lawg.newInstance(Do.class.getSimpleName());

  private DoType doType; private static final String DOTYPE = "doType";
  private boolean completed; private static final String COMPLETED = "completed";
  private int count; private static final String COUNT = "count";

  @Override
  public void deleteFirebase(Context context) {

  }

  @Override
  protected String getObjectUrl() {
    return Constants.FIREBASE_URL_DOS;
  }

  /** Getters */

  public DoType getDoType() {
    return doType;
  }
  public boolean getCompleted() {
    return completed;
  }
  public int getCount() {
    return count;
  }

  /** Setters */

  public void setDoType(DoType doType) {
    this.doType = doType;
    updateFirebase(DOTYPE, doType);
  }
  public void setCompleted(boolean completed) {
    this.completed = completed;
    updateFirebase(COMPLETED, completed);
  }
  public void setCount(int count) {
    this.count = count;
    updateFirebase(COUNT, count);
  }


  /** Public Classes */

  public enum DoType {
    NUMBER,
    YESNO,
    NONE
  }

}
