package com.waracle.androidtest.features.cakelist.viewmodel;

public class CakeListViewModelFactory {
    public CakeListViewModel createViewModel() {
        return new CakeListViewModel(new CakeListLoadingContext());
    }
}
