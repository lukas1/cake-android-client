package com.waracle.androidtest.features.cakelist.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.features.cakelist.dataclasses.Cake;
import com.waracle.androidtest.features.cakelist.viewmodel.CakeListViewModel;
import com.waracle.androidtest.features.cakelist.viewmodel.CakeListViewModelFactory;
import com.waracle.androidtest.shared.core.LiveData;

import java.util.ArrayList;

public class CakeListActivity extends AppCompatActivity {
    private static final String TAG = CakeListActivity.class.getSimpleName();

    private CakeListViewModelFactory viewModelFactory = new CakeListViewModelFactory();
    private CakeListViewModel viewModel;
    private final CakeAdapter adapter = new CakeAdapter(this);
    private final LiveData.Observer<ArrayList<Cake>> cakesObserver = new LiveData.Observer<ArrayList<Cake>>() {
        @Override
        public void onNext(@Nullable ArrayList<Cake> value) {
            if (value == null) {
                return;
            }

            adapter.setItems(value);
        }

        @Override
        public void onError(@NonNull String error) {
            Log.e(TAG, error);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_list_activity);

        initializeViewModel();
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
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

    @Override
    public void onStart() {
        super.onStart();

        subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();

        unsubscribe();
    }

    private void subscribe() {
        viewModel.getCakes().subscribe(cakesObserver);
    }

    private void unsubscribe() {
        viewModel.getCakes().unsubscribe(cakesObserver);
    }
}
