package com.overtech.ems.activity.common;

import com.overtech.ems.R;
import com.overtech.ems.R.id;
import com.overtech.ems.R.layout;
import com.overtech.ems.activity.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResetPasswordSuccessActivity extends BaseActivity {
	private TextView mHeadContent;
	private ImageView mHeadBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password_success);
		findViewById();
		init();
	}

	private void findViewById() {
		mHeadContent = (TextView) findViewById(R.id.tv_headTitle);
		mHeadBack = (ImageView) findViewById(R.id.iv_headBack);
	}

	private void init() {
		mHeadContent.setText("密码重置");
		mHeadBack.setVisibility(View.VISIBLE);
	}

}
