package com.compscieddy.didwhat.fragment;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.compscieddy.didwhat.BaseActivity;
import com.compscieddy.didwhat.R;
import com.compscieddy.eddie_utils.Etils;
import com.compscieddy.eddie_utils.Lawg;

/**
 * Created by elee on 1/18/16.
 * Base Fragment class for floating windows with a transparently black background.
 */

public class FloatingBaseFragment extends Fragment implements View.OnTouchListener {

  private static final Lawg lawg = Lawg.newInstance(FloatingBaseFragment.class.getSimpleName());
  public static final int EXIT_ANIMATION_LENGTH = 300;
  private View mRootView;
  private ViewGroup mFragmentContainer;

  private final boolean DEBUG_HIT_TARGET_DISMISSING = false;

  public View createRootView(LayoutInflater inflater, int layoutId) {
    mRootView = inflater.inflate(R.layout.fragment_floating_base, null);
    mFragmentContainer = (ViewGroup) mRootView.findViewById(R.id.your_fragment_layout);
    View innerFragmentView = inflater.inflate(layoutId, null);
    mFragmentContainer.addView(innerFragmentView);

    mRootView.setOnTouchListener(this);
    return mRootView;
  }

  private float startingX = -1;
  private float startingY = -1;

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    float currentX = event.getRawX();
    float currentY = event.getRawY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        startingX = currentX;
        startingY = currentY;
        break;
      case MotionEvent.ACTION_UP:
        boolean isSingleTap = (currentX == startingX) && (currentY == startingY);
        if (DEBUG_HIT_TARGET_DISMISSING) lawg.d("isSingleTap: " + isSingleTap + " x:" + currentX + " y:" + currentY);
        Rect mainContainerHitRect = new Rect();
        mFragmentContainer.getGlobalVisibleRect(mainContainerHitRect);
        if (DEBUG_HIT_TARGET_DISMISSING) lawg.d("top: " + mainContainerHitRect.top + " left: " + mainContainerHitRect.left + " right: " + mainContainerHitRect.right + " .bottom: " + mainContainerHitRect.bottom);
        boolean isSingleTapNotOnMainContainer = isSingleTap &&
            !mainContainerHitRect.contains((int) currentX, (int) currentY);
        if (isSingleTapNotOnMainContainer) {
          finishFragment();
        }
        break;
      case MotionEvent.ACTION_MOVE:
        break;
    }
    return true;
  }

  /*public void hideAnimation() {
    float overshootAdjustment = Util.dpToPx(50);
    float distanceToEdgeOfScreen = mFragmentContainer.getY() + mFragmentContainer.getHeight() + overshootAdjustment;
    mFragmentContainer.animate()
        .translationX(mFragmentContainer.getWidth())
        .rotationBy(Util.betterMod(360/4, 360))
        .translationY(-distanceToEdgeOfScreen);
  }*/

  protected void finishFragment() {
    float overshootAdjustment = Etils.dpToPx(50);
    float distanceToEdgeOfScreen = mFragmentContainer.getY() + mFragmentContainer.getHeight() + overshootAdjustment;
    mFragmentContainer.animate()
        .translationX(mFragmentContainer.getWidth())
        .setDuration(EXIT_ANIMATION_LENGTH)
        .rotationBy(Etils.betterMod(360 / 4, 360))
        .translationY(-distanceToEdgeOfScreen).withEndAction(new Runnable() {
      @Override
      public void run() {
        ((BaseActivity) getActivity()).getSupportFragmentManager().beginTransaction().remove(FloatingBaseFragment.this).commit();
      }
    });
  }

}
