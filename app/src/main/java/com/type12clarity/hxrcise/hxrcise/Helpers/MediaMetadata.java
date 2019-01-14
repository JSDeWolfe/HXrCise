package com.type12clarity.hxrcise.hxrcise.Helpers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.content.Context;
import android.provider.MediaStore;
//https://www.youtube.com/watch?v=kf2fxYLOiSo
import java.util.ArrayList;

public class MediaMetadata {
    ArrayList<String> arrayList;

    public MediaMetadata(Context context)
    {
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
