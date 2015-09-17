package com.microsoft.o365_android_unified_api_snippets;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.microsoft.o365_auth.AuthenticationManager;

public class SnippetDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet_detail);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(SnippetDetailFragment.ARG_ITEM_ID,
                    getIntent().getIntExtra(SnippetDetailFragment.ARG_ITEM_ID, 0));
            SnippetDetailFragment fragment = new SnippetDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.snippet_detail_container, fragment)
                    .commit();
        }}


    protected AuthenticationManager getAuthenticationManager(){
        return mAuthenticationManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_snippet_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, SnippetListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
