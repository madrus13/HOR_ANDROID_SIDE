package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.arch.paging.DataSource;

import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

public class MessageSourceFactory extends DataSource.Factory<Integer, Message> {

    private final MessageStorage storage;
    private final User currentUser;
    public MessageSourceFactory(MessageStorage storage, User currentUser) {
        this.storage = storage;
        this.currentUser = currentUser;
    }

    @Override
    public DataSource create() {
        return new MessagePositionalDataSource(storage,currentUser);
    }
}
