package com.waracle.androidtest.features.cakelist.viewmodel;

import android.support.annotation.NonNull;

import com.waracle.androidtest.features.cakelist.dataclasses.Cake;
import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.shared.core.LiveData;

import java.util.ArrayList;

public class CakeListViewModel {
    private final CakeListLoadingContext context;
    private final LiveData<ArrayList<Cake>> cakes = new LiveData<>();

    public CakeListViewModel(CakeListLoadingContext context) {
        this.context = context;
        loadCakes(true);
    }

    public LiveData<ArrayList<Cake>> getCakes() {
        return cakes;
    }

    public void refresh() {
        loadCakes(false);
    }

    private void loadCakes(boolean useCaches) {
        context.cakeListLoader.loadCakes(useCaches, context).runAsync(new IO.IOCallback<Failable<ArrayList<Cake>>>() {
            @Override
            public void callback(@NonNull Failable<ArrayList<Cake>> value) {
                value.fold(new Failable.FailableFoldCallback<ArrayList<Cake>>() {
                    @Override
                    public void foldValue(@NonNull ArrayList<Cake> value) {
                        cakes.updateValue(value);
                    }

                    @Override
                    public void foldError(@NonNull String error) {
                        cakes.postError(error);
                    }
                });
            }
        });
    }
}
