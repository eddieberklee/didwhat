package com.compscieddy.didwhat.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.compscieddy.didwhat.R;
import com.compscieddy.didwhat.model.DoSkill;
import com.compscieddy.didwhat.ui.NewButtonsView;
import com.compscieddy.eddie_utils.Lawg;

import java.util.List;

/**
 * Created by elee on 3/8/16.
 */
public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.SkillViewHolder> {

  private static final Lawg lawg = Lawg.newInstance(SkillsAdapter.class.getSimpleName());

  private List<DoSkill> mDoSkills;

  public SkillsAdapter(List<DoSkill> doSkills) {
    mDoSkills = doSkills;
  }

  @Override
  public int getItemCount() {
    return mDoSkills.size();
  }

  @Override
  public SkillViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_skill_layout, parent, false);
    SkillViewHolder vh = new SkillViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(SkillViewHolder holder, int position) {
    DoSkill doSkill = mDoSkills.get(position);
    holder.vSkillTitle.setText(doSkill.getTitle());
    final NewButtonsView newButtonsView = holder.vNewButtons;
    holder.vTodayButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        lawg.d("onClick");
        newButtonsView.setVisibility(View.VISIBLE);
        newButtonsView.startAnimation();
      }
    });
  }

  /** Public Classes */

  public static class SkillViewHolder extends RecyclerView.ViewHolder {
    public TextView vSkillTitle;
    public View vTodayButton;
    public NewButtonsView vNewButtons;

    public SkillViewHolder(View v) {
      super(v);
      vSkillTitle = (TextView) v.findViewById(R.id.skill_title);
      vTodayButton = v.findViewById(R.id.today_button);
      vNewButtons = (NewButtonsView) v.findViewById(R.id.new_buttons);
    }
  }

}
