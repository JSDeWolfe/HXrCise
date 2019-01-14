package com.type12clarity.hxrcise.hxrcise.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
//https://www.youtube.com/watch?v=kf2fxYLOiSo
import java.util.ArrayList;

public class MediaMetadata {
    ArrayList<String> arrayList;

    public MediaMetadata(Context context)
    {
        arrayList = new ArrayList<String>(10);
        //arrayList.add("test");
        ContentResolver cr = context.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = cr.query(songUri, null, null,null,null);
        if(songCursor != null && songCursor.moveToFirst()) {
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String songPathString = songCursor.getString(songPath);
                arrayList.add(songPathString);
            } while (songCursor.moveToNext());
        }
    }

    public ArrayList<String> getURI() {
        return this.arrayList;
    }
}
