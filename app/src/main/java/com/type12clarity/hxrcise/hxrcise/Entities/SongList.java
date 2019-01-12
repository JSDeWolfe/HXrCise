package com.type12clarity.hxrcise.hxrcise.Entities;

public class SongList {
    private Long _slid;
    private String _title;
    private String _datetime;

    public SongList() {
    }

    public SongList(String title) {
        this._title = title;
    }

    public void set_id(Long _id) {
        this._slid = _id;
    }

    public Long get_slid() {
        return _slid;
    }

    public void set_title(String title) {
        this._title = title;
    }

    public String get_title() {
        return _title;
    }

    public void set_datetime(String datetime) {
        this._datetime = datetime;
    }

    public String get_datetime() { return _datetime; }
}

