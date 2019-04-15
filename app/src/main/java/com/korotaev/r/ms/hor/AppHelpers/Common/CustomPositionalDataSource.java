package com.korotaev.r.ms.hor.AppHelpers.Common;

import android.arch.paging.PositionalDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.korotaev.r.ms.hor.AppHelpers.Common.CustomStorage;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class CustomPositionalDataSource<T> extends PositionalDataSource<T> {

    User currentUser;
    private final CustomStorage customStorage;

    public CustomPositionalDataSource(CustomStorage customStorage, User currentUser) {
        this.customStorage = customStorage;
        this.currentUser = currentUser;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<T> callback) {
        Log.d(TAG, "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
                ", requestedLoadSize = " + params.requestedLoadSize);

        ArrayList<T> result = customStorage.getData(currentUser.getRegion(), params.requestedStartPosition , params.requestedLoadSize);

        if (params.placeholdersEnabled && result!=null) {
            callback.onResult(result, params.requestedStartPosition, result.size());
        } else {
            //callback.onResult(result);
        }
    }


    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<T> callback) {
        Log.d(TAG, "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        ArrayList<T> result = customStorage.getData(currentUser.getRegion(), params.startPosition, params.loadSize);
        callback.onResult(result);
    }
}