package com.waracle.androidtest.features.cakelist.viewmodel;

import android.support.annotation.NonNull;

public class CakeListViewModelFactory {
    public @NonNull CakeListViewModel createViewModel() {
        return new CakeListViewModel(new CakeListLoadingContext());
    }
}
