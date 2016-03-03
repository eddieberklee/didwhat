package com.compscieddy.didwhat.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.compscieddy.didwhat.R;

/**
 * Created by elee on 1/25/16.
 */
public class NewButtonsView extends FrameLayout {

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
    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    addView(view, layoutParams);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    int[] rejectedViewIds = new int[] { R.id.new_numbers_section, R.id.new_success_section, R.id.new_fail_section };
    boolean inRejectedBounds = false;
    for (int rejectedViewId : rejectedViewIds) {
      if (clickWithinBounds(ev.getRawX(), ev.getRawY(), rejectedViewId)) {
        inRejectedBounds = true;
      }
    }
    if (inRejectedBounds) {
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

    return x >= boundLeft && x <= boundRight && y >= boundTop && y <= boundBottom;
  }

}















