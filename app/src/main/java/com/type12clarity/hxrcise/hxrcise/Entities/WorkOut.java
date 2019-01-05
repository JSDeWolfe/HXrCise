package com.type12clarity.hxrcise.hxrcise.Entities;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class WorkOut {
    private Long _woid;
    private String _word;
    private String _datetime;

    public WorkOut() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy-hh:mm");
        String formattedDate = df.format(c);
        this._datetime = formattedDate;
    }

    public WorkOut(String word) {
        this._word = word;
    }

    public void set_id(Long _id) {
        this._woid = _id;
    }

    public Long get_woid() {
        return _woid;
    }

    public void set_word(String word) {
        this._word = word;
    }

    public String get_word() {
        return _word;
    }

    public void set_datetime(String datetime) {
        this._datetime = datetime;
    }

    public String get_datetime() { return _datetime; }
}
