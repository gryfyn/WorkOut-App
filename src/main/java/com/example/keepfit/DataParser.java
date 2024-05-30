package com.example.keepfit;

import android.database.Cursor;

public interface DataParser<T> {
    T parseData(Cursor cursor);
}
