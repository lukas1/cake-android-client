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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    private void initializeViewModel() {
        viewModel = (ImageListViewModel) getLastCustomNonConfigurationInstance();
        if (viewModel == null) {
            viewModel = viewModelFactory.createViewModel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return viewModel;
    }
}
