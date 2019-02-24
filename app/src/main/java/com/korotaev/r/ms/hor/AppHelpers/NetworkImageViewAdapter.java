package com.korotaev.r.ms.hor.AppHelpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.FILE_SERVER_IP;
import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.F_WEB_FILES_COMMON;
import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.F_WEB_FILES_MESSAGE_PHOTO;
import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.F_WEB_FILES_REQUEST_PHOTO;
import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.F_WEB_FILES_USER_AVATAR_PHOTO;

public class NetworkImageViewAdapter
{
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public NetworkImageViewAdapter(Context context) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }

        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
                private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
                public void putBitmap(String url, Bitmap bitmap) {
                    mCache.put(url, bitmap);
                }
                public Bitmap getBitmap(String url) {
                    return mCache.get(url);
                }
            });
        }
    }

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public void setmImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }


    public void setImageFromServicePath(String PathFromService, NetworkImageView imageView)
    {
        if (PathFromService!=null && !PathFromService.isEmpty() &&
                (
                    PathFromService.contains(F_WEB_FILES_USER_AVATAR_PHOTO)  ||
                    PathFromService.contains(F_WEB_FILES_MESSAGE_PHOTO)      ||
                    PathFromService.contains(F_WEB_FILES_REQUEST_PHOTO)
                )
                ) {
            PathFromService =  FILE_SERVER_IP + PathFromService.replace(F_WEB_FILES_COMMON,"");
            imageView.setImageUrl(PathFromService, this.getmImageLoader());
        }
    }

}
