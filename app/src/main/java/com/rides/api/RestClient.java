package com.rides.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rides.MyApplication;
import com.rides.R;
import com.rides.enums.RequestCode;
import com.rides.helper.CustomDialog;
import com.rides.interfaces.RequestListener;
import com.rides.util.Debug;
import com.rides.util.Utils;

import org.json.JSONException;

import java.util.Map;

public class RestClient {
    private static RestClient instance;
    public static Gson gson;
    public static String STATUS_SUCCESS = "success";
    public static String STATUS_FAIL = "fail";

    private RestClient() {
    }

    static {
        initGson();
    }

    public static RestClient getInstance() {
        if (null == instance) {
            instance = new RestClient();
        }
        return instance;
    }

    public static void initGson() {
        gson = new GsonBuilder().create();
    }

    public void post(final Context mContext, int requestType, String url, final Map params, final RequestListener responseHandler, final RequestCode requestCode, final Boolean isFinalRequest, boolean isDialogRequired) {

        if (Utils.isInternetAvailable()) {

            if (isDialogRequired) {
                CustomDialog
                        .getInstance().showProgressBar(mContext,false);
            }

            StringRequest stringRequest = new StringRequest(requestType, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Debug.trace("response " + response);
                    Object object = null;
                    try {
                        object = ResponseManager.parse(requestCode, response, gson);
                        if (CustomDialog.getInstance().isDialogShowing()) {
                            CustomDialog.getInstance().hide();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        responseHandler.onRequestError(mContext.getResources().getString(R.string.error_msg_server), STATUS_FAIL, requestCode);
                    }
                    responseHandler.onRequestComplete(requestCode, object);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    if (mContext != null) {
                        if (CustomDialog.getInstance().isDialogShowing()) {
                            CustomDialog.getInstance().hide();
                        }
                    }
                    String errorMessage = "";
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        errorMessage = mContext.getResources().getString(R.string.errorMsgNoInternet);
                    } else {
                        errorMessage = mContext.getResources().getString(R.string.error_msg_server);
                    }

                    responseHandler.onRequestError(errorMessage, STATUS_FAIL, RequestCode.API_VEHICLE_LIST);
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return params;
                }
            };

            MyApplication.appInstance.setRequestTimeout(stringRequest);

            MyApplication.appInstance.addToRequestQueue(requestCode.name(), stringRequest);


        } else {
            if (mContext != null)
                CustomDialog.getInstance().showAlert(mContext, mContext.getString(R.string.errorMsgNoInternet), true, null);
        }
    }
}
