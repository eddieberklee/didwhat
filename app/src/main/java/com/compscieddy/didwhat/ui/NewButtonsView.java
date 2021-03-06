package com.compscieddy.didwhat.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.compscieddy.didwhat.R;
import com.compscieddy.eddie_utils.Lawg;

/**
 * Created by elee on 1/25/16.
 */
public class NewButtonsView extends FrameLayout {

  private static final Lawg lawg = Lawg.newInstance(NewButtonsView.class.getSimpleName());

  private final boolean DEBUG_CLICK = false;

  private View vBackground;
  private final Interpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();

  private LayoutInflater mInflater;
  private Context mContext;

  public NewButtonsView(Context context) {
    super(context);
    mContext = context;
    init(null);
  }

  public NewButtonsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    init(attrs);
  }

  public NewButtonsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mContext = context;
    init(attrs);
  }

  private void init(@Nullable AttributeSet attrs) {
    mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = mInflater.inflate(R.layout.new_buttons, null, false);
    vBackground = view.findViewById(R.id.background);
    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    addView(view, layoutParams);
  }

  public void startAnimation() {
    NewButtonsView.this.setVisibility(VISIBLE);
    vBackground.setVisibility(VISIBLE);
    vBackground.setScaleX(1f);
    vBackground.setScaleY(1f);
    vBackground.requestLayout();
    vBackground.animate()
        .setDuration(800)
        .scaleX(getWidth())
        .scaleY(getHeight())
        .setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    int[] rejectedViewIds = new int[] { R.id.new_numbers_section, R.id.new_success_section, R.id.new_fail_section };
    boolean inRejectedBounds = false;
    if (DEBUG_CLICK) lawg.d(" ev.getRawX(): " + ev.getRawX() + " ev.getRawY(): " + ev.getRawY());
    for (int rejectedViewId : rejectedViewIds) {
      if (clickWithinBounds(ev.getRawX(), ev.getRawY(), rejectedViewId)) {
        if (DEBUG_CLICK) lawg.d("Clicked within bounds!");
        inRejectedBounds = true;
      }
    }
    if (!inRejectedBounds) {
      this.animate().alpha(0).withEndAction(new Runnable() {
        @Override
        public void run() {
          NewButtonsView.this.setVisibility(INVISIBLE);
        }
      });
      return true;
    }
    return super.onInterceptTouchEvent(ev);
  }

  private boolean clickWithinBounds(float x, float y, int viewId) {
    View view = findViewById(viewId);
    float boundLeft = view.getX();
    float boundRight = boundLeft + view.getWidth();
    float boundTop = view.getY();
    float boundBottom = boundTop + view.getHeight();

    if (DEBUG_CLICK) lawg.d(" boundTop: " + boundTop + " boundRight: " + boundRight + " boundBottom: " + boundBottom + " boundLeft: " + boundLeft);
    return x >= boundLeft && x <= boundRight && y >= boundTop && y <= boundBottom;
  }

}















