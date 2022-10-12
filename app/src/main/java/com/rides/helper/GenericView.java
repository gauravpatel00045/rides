package com.rides.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

public class GenericView {

	/**
	 * Convenience method of findViewById
	 */
	@SuppressWarnings("radio_btn_unchecked")
	public static <T extends View> T findViewById(final View parent, final int id) {
		return (T) parent.findViewById(id);
	}

	/**
	 * Convenience method of findViewById
	 */
	@SuppressWarnings("radio_btn_unchecked")
	public static <T extends View> T findViewById(final Activity activity, final int id) {
		return (T) activity.findViewById(id);
	}

	/**
	 * Convenience method of findViewById
	 */
	@SuppressWarnings("radio_btn_unchecked")
	public static <T extends View> T findViewById(final Dialog dialog, final int id) {
		return (T) dialog.findViewById(id);
	}

	public static final String getString(final Context context, final int id) {
		return context.getResources().getString(id);
	}
}
