package com.rides.interfaces;


import com.rides.enums.RequestCode;

public interface RequestListener {
    /**
     * Called when a request completes with the given response. Executed by a
     * background thread: do not update the UI in this method.
     *
     * @param requestCode request code which use to identify api call
     * @param object      api response
     */
    void onRequestComplete(RequestCode requestCode, Object object);

    /**
     * Called when a request has a network or request error. Executed by a
     * background thread: do not update the UI in this method.
     *
     * @param error       error
     * @param status      status success or fail
     * @param requestCode request code which use to identify the api call
     */
    void onRequestError(String error, String status, RequestCode requestCode);
}
