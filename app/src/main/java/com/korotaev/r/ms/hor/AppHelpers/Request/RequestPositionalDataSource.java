package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class RequestPositionalDataSource extends PositionalDataSource<Request> {

    User currentUser;
    private final RequestStorage storage;

    public RequestPositionalDataSource(RequestStorage storage, User currentUser) {
        this.storage = storage;
        this.currentUser = currentUser;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Request> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);

        ArrayList<Request> result = storage.getData(currentUser.getRegion(), params.requestedStartPosition , params.requestedLoadSize);

        if (params.placeholdersEnabled && result!=null) {
            callback.onResult(result, params.requestedStartPosition, result.size());
        } else {
            //callback.onResult(result);
        }
    }


    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Request> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        ArrayList<Request> result = storage.getData(currentUser.getRegion(), params.startPosition, params.loadSize);
        callback.onResult(result);
    }

}