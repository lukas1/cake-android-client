package com.waracle.androidtest.features.cakelist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.waracle.androidtest.R;
import com.waracle.androidtest.features.cakelist.viewmodel.CakeListViewModel;
import com.waracle.androidtest.features.cakelist.viewmodel.CakeListViewModelFactory;

public class CakeListActivity extends AppCompatActivity {

    private CakeListViewModelFactory viewModelFactory = new CakeListViewModelFactory();
    private CakeListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_list_activity);

        initializeViewModel();

        // Ideally Fragment should not be used at all, but since this is ListFragment that handles
        // lot of logic that I don't want to deal with and I'm not allowed to use RecyclerView
        // I'll keep using this hack. Proper solution is to get rid of this fragment completely
        // and use RecyclerView
        PlaceholderFragment fragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.placeholder_fragment);
        fragment.setCakesLiveData(viewModel.getCakes());
    }

    private void initializeViewModel() {
        viewModel = (CakeListViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            viewModel = viewModelFactory.createViewModel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            viewModel.refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return viewModel;
    }
}
