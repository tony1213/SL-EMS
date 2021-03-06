package com.overtech.ems.activity.fulltime.maintenance;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.overtech.ems.R;
import com.overtech.ems.activity.BaseFragment;
import com.overtech.ems.activity.fulltime.activity.MaintenanceDetailActivity;
import com.overtech.ems.activity.fulltime.adapter.MaintenaceNoneAdapter;
import com.overtech.ems.activity.parttime.MainActivity;
import com.overtech.ems.entity.bean.Bean;
import com.overtech.ems.http.HttpConnector;
import com.overtech.ems.http.constant.Constant;

public class MaintenanceNoneFragment extends BaseFragment {
	private SwipeRefreshLayout swipeRefresh;
	private ListView listview;
	private LinearLayout llNoPage;
	private Button reLoad;
	private String uid;
	private String certificate;
	private List<Map<String, Object>> list;
	private MaintenaceNoneAdapter adapter;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_maintenance_none,
				container, false);
		swipeRefresh = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefresh);
		listview = (ListView) view.findViewById(R.id.lv_maintenance_none);
		llNoPage = (LinearLayout) view.findViewById(R.id.page_no_result);
		reLoad = (Button) view.findViewById(R.id.load_btn_retry);
		uid = ((MainActivity) getActivity()).getUid();
		certificate = ((MainActivity) getActivity()).getCertificate();
		initEvent();
		swipeRefresh.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				swipeRefresh.setRefreshing(true);
			}
		});
		requestLoading();
		return view;
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
				R.color.colorPrimary30);
		swipeRefresh.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				requestLoading();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String workorderCode = ((MaintenaceNoneAdapter) arg0
						.getAdapter()).getItem(arg2);
				Intent intent = new Intent(getActivity(),
						MaintenanceDetailActivity.class);
				intent.putExtra(Constant.WORKORDERCODE, workorderCode);
				startActivity(intent);
			}

		});
		reLoad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestLoading();
			}
		});
	}

	private void requestLoading() {
		// TODO Auto-generated method stub

		HttpConnector<Bean> conn = new HttpConnector<Bean>(20000, uid,
				certificate, null) {

			@Override
			public Context getContext() {
				// TODO Auto-generated method stub
				return activity;
			}

			@Override
			public void bizSuccess(Bean response) {
				// TODO Auto-generated method stub
				list = (List<Map<String, Object>>) response.body.get("data");
				if (list == null || list.size() == 0) {
					llNoPage.setVisibility(View.VISIBLE);
					swipeRefresh.setVisibility(View.GONE);
				} else {
					llNoPage.setVisibility(View.GONE);
					swipeRefresh.setVisibility(View.VISIBLE);
					if (adapter == null) {
						adapter = new MaintenaceNoneAdapter(getActivity(), list);
						listview.setAdapter(adapter);
					} else {
						adapter.setData(list);
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void bizFailed() {
				// TODO Auto-generated method stub
			}

			@Override
			public void bizStIs1Deal(Bean response) {
				// TODO Auto-generated method stub
			}

			@Override
			public void stopDialog() {
				// TODO Auto-generated method stub
				stopProgressDialog();
				if (swipeRefresh.isRefreshing()) {
					swipeRefresh.setRefreshing(false);
				}
			}
		};
		conn.sendRequest();
	}
}
