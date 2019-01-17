package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.arch.paging.DataSource;

import com.korotaev.r.ms.testormlite.data.Entity.Message;

public class MessageSourceFactory extends DataSource.Factory<Integer, Message> {

    private final MessageStorage messageStorage;

    public MessageSourceFactory(MessageStorage messageStorage) {
        this.messageStorage = messageStorage;
    }

    @Override
    public DataSource create() {
        return new MessagePositionalDataSource(messageStorage);
    }
}
