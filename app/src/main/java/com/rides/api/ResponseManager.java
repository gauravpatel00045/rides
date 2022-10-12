package com.rides.api;

import com.google.gson.Gson;
import com.rides.enums.RequestCode;
import com.rides.util.Debug;

import org.json.JSONException;

import java.util.Arrays;

public class ResponseManager {
    public static <T> Object parse(RequestCode requestCode, String response, Gson gson) throws JSONException {

        Object object = null;

        switch (requestCode) {
            case API_VEHICLE_LIST:
                object = gson.fromJson(response, requestCode.getLocalClass());
                object = Arrays.asList((T[])object);
                Debug.trace("res manager ",object.toString());
                break;
            default:
                object = response;
                break;
        }
        return object;
    }
}
