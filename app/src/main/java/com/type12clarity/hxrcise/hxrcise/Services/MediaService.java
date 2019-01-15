package com.type12clarity.hxrcise.hxrcise.Services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.media.MediaPlayer;

import com.type12clarity.hxrcise.hxrcise.R;
//https://stackoverflow.com/questions/6832522/playing-audio-from-mediastore-on-a-media-player-android

public class MediaService extends Service {

    /** indicates how to behave if the service is killed */
    int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    MediaPlayer mp;
    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Uri uri;
        mp =  MediaPlayer.create(this, uri);
        mp.setLooping(false); // Set looping
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
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
    mp.stop();
    }

    public void getAllTracks(Context context) {
        return;
    }

}
