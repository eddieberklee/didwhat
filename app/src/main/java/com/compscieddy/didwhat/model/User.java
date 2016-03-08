package com.compscieddy.didwhat.model;

import android.content.Context;

import com.compscieddy.didwhat.Constants;
import com.compscieddy.eddie_utils.Lawg;
import com.firebase.client.Firebase;

import java.util.HashMap;

/**
 * Created by elee on 2/8/16.
 */
public class User extends FirebaseObject {

  private static final Lawg lawg = Lawg.newInstance(User.class.getSimpleName());
  private HashMap<String, Boolean> doSkillsMapping = new HashMap<>();
  public static final String DOSKILLS_MAPPING = "doSkillsMapping";

  @Override
  protected String getObjectUrl() {
    return Constants.FIREBASE_URL_USERS;
  }

  @Override
  public void deleteFirebase(final Context context) {
    new Firebase(Constants.FIREBASE_URL_USERS).child(key).removeValue();
  }

  public User() {}

  public User(String encodedEmail) {
    super(encodedEmail); // encodedEmail is the key for users
  }

  public String getKey() {
    return key;
  }

  /** Methods */

  /** Getters */

  public HashMap<String, Boolean> getDoSkillsMapping() {
    return doSkillsMapping;
  }

  /** Setters */

  // NEVER actually use this - it's just for Firebase's deserializer
  public void setDoSkillsMapping(HashMap<String, Boolean> doSkillsMapping) {
    this.doSkillsMapping = doSkillsMapping;
  }

  public void addDoSkillsMapping(String doSkillsKey) {
    doSkillsMapping.put(doSkillsKey, true);
    updateFirebase(DOSKILLS_MAPPING, doSkillsMapping, doSkillsKey);
  }

  public void removeDoSkillsMapping(String doSkillsKey) {
    doSkillsMapping.remove(doSkillsKey);
    updateFirebase(DOSKILLS_MAPPING, doSkillsMapping, doSkillsKey);
  }


}
