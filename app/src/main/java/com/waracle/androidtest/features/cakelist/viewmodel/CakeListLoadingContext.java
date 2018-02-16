package com.waracle.androidtest.features.cakelist.viewmodel;

import com.waracle.androidtest.features.cakelist.dataaccess.CakeConvertor;
import com.waracle.androidtest.features.cakelist.dataaccess.CakeListLoader;
import com.waracle.androidtest.features.cakelist.dataclasses.Cake;
import com.waracle.androidtest.shared.networking.JsonArrayConvertor;
import com.waracle.androidtest.shared.networking.JsonHttpDataLoader;

public class CakeListLoadingContext {
    public final String cakeListUrl = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    public final CakeListLoader cakeListLoader = new CakeListLoader();
    public final JsonHttpDataLoader jsonHttpDataLoader = new JsonHttpDataLoader();
    public final JsonArrayConvertor<Cake> cakeListConverter = new JsonArrayConvertor<>(
            new CakeConvertor()
    );
}
