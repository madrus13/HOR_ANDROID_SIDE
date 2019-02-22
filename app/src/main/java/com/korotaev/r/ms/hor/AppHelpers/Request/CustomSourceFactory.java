package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.arch.paging.DataSource;

import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

public class CustomSourceFactory extends DataSource.Factory<Integer, Message> {

    private final CustomStorage customStorage;
    private final User currentUser;
    public CustomSourceFactory(CustomStorage customStorage, User currentUser) {
        this.customStorage = customStorage;
        this.currentUser = currentUser;
    }

    @Override
    public DataSource create() {
        return new CustomPositionalDataSource(customStorage,currentUser);
    }
}
