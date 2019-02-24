package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.arch.paging.DataSource;

import com.korotaev.r.ms.hor.AppHelpers.Request.CustomStorage;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

public class MessageSourceFactory<T> extends DataSource.Factory<Integer, T> {

    private final CustomStorage storage;
    private final User currentUser;
    public MessageSourceFactory(CustomStorage messageStorage, User currentUser) {
        this.storage = messageStorage;
        this.currentUser = currentUser;
    }

    @Override
    public DataSource create() {
        return new MessagePositionalDataSource(storage,currentUser);
    }
}
