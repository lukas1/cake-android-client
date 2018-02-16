package com.waracle.androidtest.features.cakelist.dataaccess;

import android.support.annotation.NonNull;

import com.waracle.androidtest.features.cakelist.viewmodel.CakeListLoadingContext;
import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.features.cakelist.dataclasses.Cake;

import java.util.ArrayList;

public class CakeListLoader {
    public @NonNull IO<Failable<ArrayList<Cake>>> loadCakes(boolean useCaches, @NonNull CakeListLoadingContext context) {
        return context.jsonHttpDataLoader.loadJsonData(
                context.cakeListUrl,
                context.cakeListConverter,
                useCaches
        );
    }
}
