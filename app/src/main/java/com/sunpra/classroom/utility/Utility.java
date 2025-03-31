package com.sunpra.classroom.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Utility {

    public static <T> T fromJson(String jsonString, Class<T> classOfT) {
       return new Gson().fromJson(jsonString, classOfT);
    }

    public static <T> T fromJson(String jsonString, TypeToken<T> typeToken) {
        return new Gson().fromJson(jsonString, typeToken);
    }

}
