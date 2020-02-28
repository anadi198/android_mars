package com.example.marsplay.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.example.marsplay.R;
import com.example.marsplay.pojo.Doc;


public class DetailActivity extends AppCompatActivity {
    private TextView article1;
    private String article;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        getWindow().setAllowEnterTransitionOverlap(true);
        setContentView(R.layout.activity_detail);
        View view = getLayoutInflater().inflate(R.layout.action_bar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView Title = (TextView) view.findViewById(R.id.title_action);
        Title.setText("Marsplay");

        getSupportActionBar().setCustomView(view, params);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowCustomEnabled(true); //show custom title
        getSupportActionBar().setDisplayShowTitleEnabled(false); //hide the default title
        Intent intent = getIntent();
        Doc mDocData = (Doc) intent.getSerializableExtra("docs");
        TextView topic = findViewById(R.id.article_heading);
        TextView date = findViewById(R.id.date_detail);
        TextView journal = findViewById(R.id.journal);
        TextView authors = findViewById(R.id.authors_list);
        TextView type = findViewById(R.id.article_type);
        TextView score = findViewById(R.id.score_detail);
        article1 = findViewById(R.id.article);
        ViewCompat.setTransitionName(topic, "title");
        ViewCompat.setTransitionName(date, "date");
        ViewCompat.setTransitionName(score, "score");
        ViewCompat.setTransitionName(article1, "desc");
        if (mDocData != null) {
            topic.setText(mDocData.getTitleDisplay());
            date.setText("Date: " + mDocData.getPublicationDate().substring(0, 9));
            journal.setText("Journal: " + mDocData.getJournal());
            type.setText("Type: " + mDocData.getArticleType());
            score.setText("Score: " + mDocData.getScore());
            article = mDocData.getAbstract().get(0);
            if (article == null)
                article = "";
            if (article.equals("")) {
                article = "No abstract found for this article.";
            } else if (article.charAt(0) == '\n') {
                article = article.substring(1);
            }
            String authorsJoined = "Authors: \n" + TextUtils.join("\n", mDocData.getAuthorDisplay());
            authors.setText(authorsJoined);
            article1.setText(article);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAfterTransition();
    }
}
