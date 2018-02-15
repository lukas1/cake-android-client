package com.waracle.androidtest.features.imagelist.viewmodel;

public class ImageListViewModelFactory {
    public ImageListViewModel createViewModel() {
        return new ImageListViewModel(new ImageListLoadingContext());
    }
}
