package com.compscieddy.didwhat.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.compscieddy.didwhat.R;

/**
 * Created by elee on 1/25/16.
 */
public class NewButtonsView extends FrameLayout {

  private LayoutInflater mInflater;

  public NewButtonsView(Context context) {
    super(context);
    init(context, null);
  }

  public NewButtonsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public NewButtonsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, @Nullable AttributeSet attrs) {
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = mInflater.inflate(R.layout.new_buttons, null, false);
    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    addView(view, layoutParams);
  }

}
