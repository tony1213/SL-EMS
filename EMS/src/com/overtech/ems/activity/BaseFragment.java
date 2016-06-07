package com.overtech.ems.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.overtech.ems.R;
import com.overtech.ems.http.HttpEngine;
import com.overtech.ems.http.OkHttpClientManager;
import com.overtech.ems.widget.CustomProgressDialog;
import com.overtech.ems.widget.bitmap.ImageLoader;
import com.overtech.ems.widget.dialogeffects.NiftyDialogBuilder;

/**
 * Created by Tony1213 on 15/12/21.
 */
public class BaseFragment extends Fragment {

	public MyApplication application;

	public ImageLoader imageLoader;

	public Activity activity;

	public Context context;

	public FragmentManager fragmentManager;

	public OkHttpClientManager okHttpClientManager;

	public HttpEngine httpEngine;

	public CustomProgressDialog progressDialog;

	public NiftyDialogBuilder dialogBuilder;

	public SharedPreferences mSharedPreferences;

	public Gson gson;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (MyApplication) getActivity().getApplication();
		activity = getActivity();
		context = getActivity();
		fragmentManager = getFragmentManager();
		dialogBuilder = NiftyDialogBuilder.getInstance(activity);
		httpEngine = HttpEngine.getInstance();
		httpEngine.initContext(context);
		imageLoader = ImageLoader.getInstance();
		imageLoader.initContext(context);
		progressDialog = CustomProgressDialog.createDialog(context);
		progressDialog.setMessage(context
				.getString(R.string.loading_public_default));
		progressDialog.setCanceledOnTouchOutside(false);
		mSharedPreferences = application.getSharePreference();
		if (gson == null) {
			gson = new Gson();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void startProgressDialog(String content) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
		}
		progressDialog.setMessage(content);
		progressDialog.show();
	}

	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
