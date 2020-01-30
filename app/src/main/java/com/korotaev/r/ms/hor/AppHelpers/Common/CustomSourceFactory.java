package com.korotaev.r.ms.hor.AppHelpers.Common;


import androidx.paging.DataSource;

import com.korotaev.r.ms.hor.AppHelpers.Common.CustomPositionalDataSource;
import com.korotaev.r.ms.hor.AppHelpers.Common.CustomStorage;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

public class CustomSourceFactory<T> extends DataSource.Factory<Integer, T> {

    private final CustomStorage customStorage;
    private final User currentUser;
    public CustomSourceFactory(CustomStorage customStorage, User currentUser) {
        this.customStorage = customStorage;
        this.currentUser = currentUser;
    }

    @Override
    public DataSource create() {
        return new CustomPositionalDataSource<T>(customStorage,currentUser);
    }
}
