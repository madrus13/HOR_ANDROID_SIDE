package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.arch.paging.DataSource;

import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.User;

public class RequestSourceFactory extends DataSource.Factory<Integer, Request> {

    private final RequestStorage storage;
    private final User currentUser;
    public RequestSourceFactory(RequestStorage storage, User currentUser) {
        this.storage = storage;
        this.currentUser = currentUser;
    }

    @Override
    public DataSource create() {
        return new RequestPositionalDataSource(storage,currentUser);
    }
}
