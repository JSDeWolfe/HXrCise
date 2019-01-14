package com.type12clarity.hxrcise.hxrcise.Services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.media.MediaPlayer;
//https://stackoverflow.com/questions/6832522/playing-audio-from-mediastore-on-a-media-player-android

public class MediaService extends Service {

    /** indicates how to behave if the service is killed */
    int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    MediaPlayer mp;

    @Override
    public void onCreate() {
        mp = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    @Override
    public void onRebind(Intent intent) {

    }

    @Override
    public void onDestroy() {

    }

    public void getAllTracks(Context context) {
        return;
    }

}
