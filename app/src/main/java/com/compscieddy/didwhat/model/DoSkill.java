package com.compscieddy.didwhat.model;

import android.content.Context;

import com.compscieddy.didwhat.Constants;
import com.compscieddy.eddie_utils.Lawg;

import java.util.HashMap;

/**
 * Created by elee on 3/1/16.
 */
public class DoSkill extends FirebaseObject {

  private static final Lawg lawg = Lawg.newInstance(DoSkill.class.getSimpleName());

  private String title; private static final String TITLE = "title";
  private HashMap<String, Boolean> doDaysMapping;
  private static final String DO_DAYS_MAPPING = "doDaysMapping";

  @Override
  public void deleteFirebase(Context context) {

  }

  @Override
  protected String getObjectUrl() {
    return Constants.FIREBASE_URL_DOSKILLS;
  }

  /** Getters */

  public String getTitle() {
    return title;
  }
  public HashMap<String, Boolean> getDoDaysMapping() {
    return doDaysMapping;
  }

  /** Setters */

  public void setTitle(String title) {
    this.title = title;
    updateFirebase(TITLE, title);
  }

  // NEVER use this - this is just for Firebase's deserializer
  public void setDoDaysMapping(HashMap<String, Boolean> doDaysMapping) { this.doDaysMapping = doDaysMapping; }

  public void addDoDaysMapping(String doDayKey) {
    doDaysMapping.put(doDayKey, true);
    updateFirebase(DO_DAYS_MAPPING, doDaysMapping, doDayKey);
  }
  public void removeDoDaysMapping(String doDayKey) {
    doDaysMapping.remove(doDayKey);
    updateFirebase(DO_DAYS_MAPPING, doDaysMapping, doDayKey);
  }


}
