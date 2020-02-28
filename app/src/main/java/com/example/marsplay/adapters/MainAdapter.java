package com.example.marsplay.adapters;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marsplay.R;
import com.example.marsplay.activities.DetailActivity;
import com.example.marsplay.pojo.Doc;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private ArrayList<Doc> mArticleData;
    private Context mContext;
    private int previousExpandedPosition = -1, mExpandedPosition = -1;

    public MainAdapter(Context context, ArrayList<Doc> mArticleData) {
        this.mArticleData = mArticleData;
        this.mContext = context;
    }

    @Override
    public MainViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.menu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder,
                                 int position) {
        Doc currentResponse = mArticleData.get(position);
        holder.bindTo(currentResponse);
        if (position == previousExpandedPosition) {
            ObjectAnimator animation = ObjectAnimator.ofInt(
                    holder.description,
                    "maxLines",
                    1);
            holder.card.setBackgroundColor(mContext.getColor(R.color.colorPrimary));
            animation.setDuration(150);
            animation.start();
            ValueAnimator animator = ObjectAnimator.ofFloat(holder.mTitleText, "textSize", 20, 18);
            animator.setDuration(150);
            animator.start();
            holder.card.setMaxCardElevation(0f);
            holder.card.setCardElevation(0f);
            holder.read_more.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mArticleData.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView date, score, description, read_more;
        private RelativeLayout container;
        private CardView card;

        MainViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            score = itemView.findViewById(R.id.score);
            description = itemView.findViewById(R.id.small_description);
            read_more = itemView.findViewById(R.id.read_more_button);
            card = itemView.findViewById(R.id.card);
            container = itemView.findViewById(R.id.container);
            container.setOnClickListener(this);
            read_more.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Doc currentResource = mArticleData.get(getAdapterPosition());
            int position = getAdapterPosition();
            Intent intent;
            if (view.getId() == R.id.read_more_button) {
                intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("docs", mArticleData.get(position));
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) mContext, new Pair<>(mTitleText,
                                        "title"),
                                new Pair<>(date,
                                        "date"),
                                new Pair<>(score,
                                        "score"),
                                new Pair<>(description,
                                        "desc")
                        );
                // start the new activity
                view.getContext().startActivity(intent, options.toBundle());
            } else {
                if (mExpandedPosition == position) {
                    //TODO: collapse
                    ObjectAnimator animation = ObjectAnimator.ofInt(
                            description,
                            "maxLines",
                            1);
                    ValueAnimator animator = ObjectAnimator.ofFloat(mTitleText, "textSize", 20, 18);
                    animator.setDuration(150);
                    animator.start();
                    animation.setDuration(150);
                    card.setBackgroundColor(mContext.getColor(R.color.colorPrimary));
                    animation.start();
                    card.setMaxCardElevation(0f);
                    card.setCardElevation(0f);
                    read_more.setVisibility(View.GONE);
                    previousExpandedPosition = -1;
                    mExpandedPosition = -1;
                } else {
                    previousExpandedPosition = mExpandedPosition;
                    notifyItemChanged(previousExpandedPosition);
                    mExpandedPosition = position;
                    card.setBackgroundColor(mContext.getColor(R.color.colorLightAccent));
                    read_more.setBackgroundColor(mContext.getColor(R.color.colorLightAccent));
                    read_more.setAlpha(0f);
                    read_more.setVisibility(View.VISIBLE);
                    read_more.animate()
                            .alpha(1f)
                            .setDuration(800)
                            .setListener(null);

                    ValueAnimator animator = ObjectAnimator.ofFloat(mTitleText, "textSize", 18, 20);
                    animator.setDuration(150);

                    animator.start();
                    //TODO: expand
                    ObjectAnimator animation = ObjectAnimator.ofInt(
                            description,
                            "maxLines",
                            6);
                    animation.setDuration(150);
                    animation.start();
                    card.setMaxCardElevation(8f);
                    card.setCardElevation(8f);
                }
            }
        }

        void bindTo(Doc currentResponse) {
            read_more.setVisibility(View.GONE);
            mTitleText.setText(currentResponse.getTitleDisplay());
            date.setText(currentResponse.getPublicationDate().substring(0, 10));
            Double score_num = currentResponse.getScore();
            score.setText(String.valueOf(score_num));
            String abs = currentResponse.getAbstract().get(0);
            if (abs.equals("")) {
                abs = "No abstract available.";
            } else if (abs.charAt(0) == '\n') {
                abs = abs.substring(1);
            }
            while (abs.charAt(0) == ' ') {
                abs = abs.substring(1);
            }
            description.setText(abs);
            description.setMaxLines(1);
            card.setCardElevation(0f);
            card.setMaxCardElevation(0f);
        }
    }
}
