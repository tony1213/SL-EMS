package com.overtech.ems.activity.common.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.overtech.ems.R;
import com.overtech.ems.activity.BaseActivity;
import com.overtech.ems.activity.common.register.adapter.SelectCityAdapter;
import com.overtech.ems.config.SystemConfig;
import com.overtech.ems.entity.bean.Bean;
import com.overtech.ems.entity.common.Requester;
import com.overtech.ems.http.OkHttpClientManager;
import com.overtech.ems.http.OkHttpClientManager.ResultCallback;
import com.overtech.ems.utils.Logr;
import com.overtech.ems.utils.Utilities;
import com.squareup.okhttp.Request;

public class SelectCityActivity extends BaseActivity {
	private Toolbar toolbar;
	private ActionBar actionBar;
	private AppCompatTextView tvTitle;
	private SelectCityActivity activity;
	private ExpandableListView elvCitys;
	private SelectCityAdapter adapter;
	private String parentCode;
	private String parentName;
	private String childCode;
	private String childName;

	@Override
	protected int getLayoutResIds() {
		// TODO Auto-generated method stub
		return R.layout.activity_selectcity;
	}

	@Override
	protected void afterCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		toolbar=(Toolbar) findViewById(R.id.toolBar);
		setSupportActionBar(toolbar);
		actionBar=getSupportActionBar();
		tvTitle=(AppCompatTextView) findViewById(R.id.tvTitle);
		elvCitys = (ExpandableListView) findViewById(R.id.elv_citys);
		activity = this;
		initEvent();
		initData();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		tvTitle.setText("城市选择");
		toolbar.setNavigationOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		// elvCitys.setOnGroupClickListener(new OnGroupClickListener() {
		//
		// @Override
		// public boolean onGroupClick(ExpandableListView parent, View v,
		// int groupPosition, long id) {
		// // TODO Auto-generated method stub
		// childName = adapter.getGroup(groupPosition).toString();
		// childCode = String.valueOf(adapter.getGroupId(groupPosition));
		// if (!adapter.isHasChild(groupPosition)) {
		// Intent data = new Intent();
		// data.putExtra("cityName", childName);
		// data.putExtra("cityCode", childCode);
		// setResult(Activity.RESULT_OK, data);
		// }
		// return false;
		// }
		// });
		elvCitys.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				childName = adapter.getChild(groupPosition, childPosition)
						.toString();
				childCode = adapter.getChildCode(groupPosition, childPosition);
				Intent data = new Intent();
				data.putExtra("childName", childName);
				data.putExtra("childCode", childCode);
				setResult(Activity.RESULT_OK, data);
				finish();
				return false;
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub
		startProgressDialog(getResources().getString(
				R.string.loading_public_default));
		Requester requester = new Requester();
		requester.cmd = 4;
		ResultCallback<Bean> callback = new ResultCallback<Bean>() {

			@Override
			public void onError(Request request, Exception e) {
				// TODO Auto-generated method stub
				stopProgressDialog();
				Logr.e(request.toString());
			}

			@Override
			public void onResponse(Bean response) {
				// TODO Auto-generated method stub
				stopProgressDialog();
				if (response == null) {
					Utilities.showToast(R.string.response_no_object, activity);
					return;
				}
				adapter = new SelectCityAdapter(activity, response.body);
				elvCitys.setAdapter(adapter);
			}
		};
		OkHttpClientManager.postAsyn(SystemConfig.NEWIP, callback,
				gson.toJson(requester));
	}

}
