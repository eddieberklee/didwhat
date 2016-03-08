package com.compscieddy.didwhat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.compscieddy.didwhat.adapter.SkillsAdapter;
import com.compscieddy.didwhat.fragment.SkillTitleInputFragment;
import com.compscieddy.didwhat.model.DoDay;
import com.compscieddy.didwhat.model.DoSkill;
import com.compscieddy.didwhat.model.User;
import com.compscieddy.eddie_utils.Etils;
import com.compscieddy.eddie_utils.Lawg;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

  private static final Lawg lawg = Lawg.newInstance(MainActivity.class.getSimpleName());

  @Bind(R.id.start_skill_button) View mStartSkillButton;
  @Bind(R.id.skills_recyclerview) RecyclerView mSkillsRecyclerView;

  private RecyclerView.Adapter mSkillsAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  private DoDay mDoDay;
  private List<DoSkill> mDoSkills = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    findOrCreateDoDay();
  }

  private void findOrCreateDoDay() {
    String encodedEmail = getEncodedEmail();
    new Firebase(Constants.FIREBASE_URL_USERS).child(encodedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (!TemplateApplication.isFirebaseLoggedIn()) {
          return;
        }

        mUser = dataSnapshot.getValue(User.class);
        if (mUser == null) {
          // This can happen when the server data is manually deleted via the dashboard
          lawg.e("User is null dataSnapshot:" + dataSnapshot);
          mUser = populateUserFirebaseData(lawg);
        }

        HashMap<String, Boolean> userDoSkillsMapping = mUser.getDoSkillsMapping();
        if (userDoSkillsMapping == null || userDoSkillsMapping.size() == 0) {
          createNewDoDay(mUser);
          init();
        }

        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        String todayDayKey = DoDay.createKey(getEncodedEmail(), today);

        Set<String> doSkillKeys = userDoSkillsMapping.keySet();

        if (doSkillKeys.contains(todayDayKey)) {
          new Firebase(Constants.FIREBASE_URL_DODAYS).child(todayDayKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              mDoDay = dataSnapshot.getValue(DoDay.class);
              if (mDoDay == null) {
                Etils.logAndToast(MainActivity.this, lawg, "Uh wtf man why is mDoDay null dataSnapshot:" + dataSnapshot);
              }
              init();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
              lawg.e("onCancelled() while getting a ");
            }
          });
        } else {
          createNewDoDay(mUser);
          init();
        }

      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        lawg.e("onCancelled() while trying to get user");
      }
    });
  }

  private void createNewDoDay(User user) {
    Date today = new Date();
    String doDayKey = DoDay.createKey(getEncodedEmail(), today);
    mDoDay = new DoDay(doDayKey, today);
    Firebase newDoDayRef = new Firebase(Constants.FIREBASE_URL_DODAYS).child(doDayKey);
    newDoDayRef.setValue(mDoDay);
  }

  /**
   * Called after a DoDay has been fetched or newly created
   */
  private void init() {

    mLayoutManager = new LinearLayoutManager(this);
    mSkillsRecyclerView.setLayoutManager(mLayoutManager);

    mSkillsAdapter = new SkillsAdapter(mDoSkills);
    mSkillsRecyclerView.setAdapter(mSkillsAdapter);

    new Firebase(Constants.FIREBASE_URL_USER_DOSKILL_MAPPING).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        lawg.d("onChildAdded dataSnapshot:" + dataSnapshot);
        String doSkillKey = dataSnapshot.getKey();
        new Firebase(Constants.FIREBASE_URL_DOSKILLS).child(doSkillKey).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
            DoSkill doSkill = dataSnapshot.getValue(DoSkill.class);
            mDoSkills.add(doSkill);
            mSkillsAdapter.notifyDataSetChanged();
          }
          @Override
          public void onCancelled(FirebaseError firebaseError) {
            lawg.e("Error onCancelled() trying to get individual DoSkill");
          }
        });
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
        lawg.d("onChildRemoved dataSnapshot:" + dataSnapshot);
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        lawg.e("Error onCancelled() trying to get DoSkill mapping");
      }
    });


        /*
        .addChildEventListener(new ChildEventListener() {
    @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        lawg.d("onChildAdded() s: " + s + " dataSnapshot: " + dataSnapshot);
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {
        lawg.d("onChildRemoved() dataSnapshot: " + dataSnapshot);
      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

      @Override
      public void onCancelled(FirebaseError firebaseError) {
        lawg.e("onCancelled while trying to traverse skills");
      }
    });
  */

    setListeners();

  }

  private void setListeners() {
    mStartSkillButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SkillTitleInputFragment fragment = new SkillTitleInputFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main_activity, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    switch(id) {
      case R.id.logout:
        logout();
        Intent intent = new Intent(MainActivity.this, AuthenticationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
