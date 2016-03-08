package com.compscieddy.didwhat.fragment;


import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.compscieddy.didwhat.BaseActivity;
import com.compscieddy.didwhat.R;
import com.compscieddy.eddie_utils.Etils;
import com.compscieddy.eddie_utils.Lawg;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by elee on 1/18/16.
 */
@SuppressLint("ValidFragment")
public class SkillTitleInputFragment extends FloatingBaseFragment {

  private static final Lawg lawg = Lawg.newInstance(SkillTitleInputFragment.class.getSimpleName());
  private View mRootView;
  private BaseActivity mActivity;
  private FragmentManager mSupportFragmentManager;

  @Bind(R.id.title_input) EditText mTitleInput;
  @Bind(R.id.main_container) ViewGroup mMainContainer;
  @Bind(R.id.create_button) TextView mCreateButton;

  public SkillTitleInputFragment() {
    super();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    mRootView = super.createRootView(inflater, R.layout.fragment_skill_title);
    ButterKnife.bind(this, mRootView);
    mActivity = (BaseActivity) getActivity();
    mSupportFragmentManager = mActivity.getSupportFragmentManager();

    mTitleInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
          mCreateButton.performClick();
        }
        return false;
      }
    });

    GradientDrawable mTitleInputBackground = (GradientDrawable) mTitleInput.getBackground();
    
    return mRootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    // todo: doesn't work - find that util method for bringing up the keyboard
    mTitleInput.requestFocus();
  }

  @OnClick(R.id.create_button)
  public void createButtonClick() {
    /** todo: create the DaySkill here */
//    mActivity.mAdapter.notifyDataSetChanged();
    mSupportFragmentManager.beginTransaction().remove(SkillTitleInputFragment.this).commit();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Etils.hideKeyboard(mActivity, mRootView);
  }

}
