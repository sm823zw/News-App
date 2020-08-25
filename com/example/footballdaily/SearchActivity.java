package com.example.footballdaily;

import android.app.SearchManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

public class SearchActivity extends BaseActivity {
    private SearchView mSearchView;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        activateToolbar(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService("search");
        this.mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        this.mSearchView.setQueryHint("Enter Country name");
        this.mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        this.mSearchView.setIconified(false);
        this.mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                PreferenceManager.getDefaultSharedPreferences(SearchActivity.this.getApplicationContext()).edit().putString("COUNTRY_QUERY", query).apply();
                SearchActivity.this.finish();
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        this.mSearchView.setOnCloseListener(new OnCloseListener() {
            public boolean onClose() {
                SearchActivity.this.finish();
                return false;
            }
        });
        return true;
    }
}
