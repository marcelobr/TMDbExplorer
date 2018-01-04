package com.challenge.ndrive.tmdbexplorer.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.View;

import com.challenge.ndrive.tmdbexplorer.R;

public class MainActivity extends AppCompatActivity {

    private View mLoadingIndicator;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.loading_indicator);
        mLoadingIndicator.setVisibility(View.GONE);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
    }
}
