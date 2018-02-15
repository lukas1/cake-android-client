package com.waracle.androidtest.features.imagelist.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.waracle.androidtest.R;
import com.waracle.androidtest.features.imagelist.viewmodel.ImageListViewModel;
import com.waracle.androidtest.features.imagelist.viewmodel.ImageListViewModelFactory;

public class MainActivity extends AppCompatActivity {

    private ImageListViewModelFactory viewModelFactory = new ImageListViewModelFactory();
    private ImageListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViewModel();

        // Ideally Fragment should not be used at all, but since this is ListFragment that handles
        // lot of logic that I don't want to deal with and I'm not allowed to use RecyclerView
        // I'll keep using this hack. Proper solution is to get rid of this fragment completely
        // and use RecyclerView
        PlaceholderFragment fragment = (PlaceholderFragment) getSupportFragmentManager().findFragmentById(R.id.placeholder_fragment);
        fragment.setImagesLiveData(viewModel.getImages());
    }

    private void initializeViewModel() {
        viewModel = (ImageListViewModel) getLastCustomNonConfigurationInstance();
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
