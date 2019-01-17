package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.korotaev.r.ms.testormlite.data.Entity.Message;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class MessagePositionalDataSource extends PositionalDataSource<Message> {

    private final MessageStorage messageStorage;

    public MessagePositionalDataSource(MessageStorage messageStorage) {
        this.messageStorage = messageStorage;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Message> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);
        List<Message> result = messageStorage.getData(params.requestedStartPosition, params.requestedLoadSize);
        callback.onResult(result, params.requestedStartPosition, result.size());
    }


    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Message> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        List<Message> result = messageStorage.getData(params.startPosition, params.loadSize);
        callback.onResult(result);
    }

}