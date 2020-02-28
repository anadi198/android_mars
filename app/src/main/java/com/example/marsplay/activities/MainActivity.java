package com.example.marsplay.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marsplay.R;
import com.example.marsplay.adapters.MainAdapter;
import com.example.marsplay.models.ArticleViewModel;
import com.example.marsplay.pojo.Doc;
import com.example.marsplay.pojo.MultipleResource;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Doc> docArrayList;
    private MainAdapter mainAdapter;
    private ArticleViewModel avm;
    private List<Doc> data;
    private ShimmerFrameLayout mShimmerViewContainer;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        setContentView(R.layout.activity_main);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));
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
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        docArrayList = new ArrayList<>();
        data = new ArrayList<>();
        mainAdapter = new MainAdapter(this, docArrayList);
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        mRecyclerView.addItemDecoration(horizontalDecoration);
        avm = new ViewModelProvider(this).get(ArticleViewModel.class);
        avm.init();
        avm.getDnaRepository().observe(this, new Observer<MultipleResource>() {
            @Override
            public void onChanged(@Nullable MultipleResource multipleResource) {
                if (multipleResource != null) {
                    data = multipleResource.getResponse().getDocs();
                    if (data != null) {
                        docArrayList.addAll(data);
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mainAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    protected void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
