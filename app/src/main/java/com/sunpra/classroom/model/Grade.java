package com.sunpra.classroom.model;

import androidx.annotation.NonNull;

import com.sunpra.classroom.R;

public enum Grade {

    ONE(R.string.one),
    TWO(R.string.two),
    THREE(R.string.three),
    FOUR(R.string.four),
    FIVE(R.string.five),
    SIX(R.string.six),
    SEVEN(R.string.seven),
    EIGHT(R.string.eight),
    NINE(R.string.nine),
    TEN(R.string.ten);

    int displayName;

    Grade(int displayName){
        this.displayName = displayName;
    }
}
