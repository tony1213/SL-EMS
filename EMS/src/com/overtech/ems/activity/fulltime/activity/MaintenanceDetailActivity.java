package com.overtech.ems.activity.fulltime.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.baidu.mapapi.utils.route.RouteParaOption.EBusStrategyType;
import com.overtech.ems.R;
import com.overtech.ems.activity.BaseActivity;
import com.overtech.ems.activity.MyApplication;
import com.overtech.ems.activity.parttime.common.ElevatorDetailActivity;
import com.overtech.ems.entity.bean.Bean;
import com.overtech.ems.http.HttpConnector;
import com.overtech.ems.http.constant.Constant;
import com.overtech.ems.mapdialog.InstallerMapDialog;
import com.overtech.ems.utils.Logr;
import com.overtech.ems.utils.SharePreferencesUtils;
import com.overtech.ems.utils.SharedPreferencesKeys;
import com.overtech.ems.utils.Utilities;
import com.overtech.ems.widget.zxing.CaptureActivity;

public class MaintenanceDetailActivity extends BaseActivity implements
		OnClickListener {
	private Toolbar toolbar;
	private ActionBar actionBar;
	private AppCompatTextView tvTitle;
	private AppCompatTextView tvNavigation;
	private AppCompatTextView tvQrcode;
	private AppCompatTextView tvPartners;
	private LinearLayout elevatorDetail;
	private AppCompatTextView tvAddress;
	private AppCompatTextView tvElevatorNo;
	private AppCompatTextView tvFaultFrom;
	private AppCompatTextView tvRepairContent;
	private AppCompatButton btClosePeople;
	private AppCompatButton btComponentLists;
	private InstallerMapDialog mapDialog;
	private String isMain;// 是否主修
	private String peopleInEmergency;// 是否是关人状态
	private String hasChooseComponent;// 主修是否已经进行了配件操作
	private String hasReport;// 主修是否已经提交了维修报告
	private String siteTel;// 站点电话
	private String uid;
	private String certificate;
	private String workorderCode;
	private List<Map<String, Object>> listPartners;// 搭档列表
	private String[] partners;// 此次维修的搭档提取出来用于显示姓名和电话
	private String elevatorNo;
	private String faultTime;
	private double desLatitude;
	private double desLongitude;
	private MaintenanceDetailActivity activity;

	@Override
	protected int getLayoutResIds() {
		// TODO Auto-generated method stub
		return R.layout.activity_maintenance_detail;
	}

	@Override
	protected void afterCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity = this;
		stackInstance.pushActivity(activity);
		uid = (String) SharePreferencesUtils.get(this,
				SharedPreferencesKeys.UID, "");
		certificate = (String) SharePreferencesUtils.get(this,
				SharedPreferencesKeys.CERTIFICATED, "");
		workorderCode = getIntent().getStringExtra(Constant.WORKORDERCODE);
		Logr.e(workorderCode);
		initView();
		initEvent();
		loadingData();
	}

	private void loadingData() {
		// TODO Auto-generated method stub
		startProgressDialog(getResources().getString(
				R.string.loading_public_default));
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("workorderCode", workorderCode);
		HttpConnector<Bean> conn = new HttpConnector<Bean>(20002, uid,
				certificate, body) {

			@Override
			public Context getContext() {
				// TODO Auto-generated method stub
				return activity;
			}

			@Override
			public void bizSuccess(Bean response) {
				// TODO Auto-generated method stub
				tvAddress.setText("地址："
						+ response.body.get("repairAddress").toString());
				tvElevatorNo.setText("梯号："
						+ response.body.get("elevatorNo").toString());
				tvFaultFrom.setText("故障来源："
						+ response.body.get("storeySite").toString());
				tvRepairContent.setText("报修内容："
						+ response.body.get("faultCause").toString());
				tvFaultFrom.setText("故障来源："
						+ response.body.get("faultFrom").toString());
				listPartners = (List<Map<String, Object>>) response.body
						.get("partners");
				if (listPartners != null && listPartners.size() >= 0) {
					partners = new String[listPartners.size()];
					for (int i = 0; i < listPartners.size(); i++) {
						Map<String, Object> p = listPartners.get(i);
						if (TextUtils.equals(p.get("isMain").toString(), "0")) {
							partners[i] = p.get("partnerPhone").toString()
									+ " " + p.get("partnerName").toString()
									+ "(主修)";
						} else {
							partners[i] = p.get("partnerPhone").toString()
									+ " " + p.get("partnerName").toString();
						}
					}
				}

				elevatorNo = response.body.get("elevatorNo").toString();
				desLatitude = Double.parseDouble(response.body.get("latitude")
						.toString());
				desLongitude = Double.parseDouble(response.body
						.get("longitude").toString());
				isMain = response.body.get("isMain").toString();
				peopleInEmergency = response.body.get("peopleInEmergency")
						.toString();
				hasChooseComponent = response.body.get("hasChooseComponent")
						.toString();
				faultTime = response.body.get("faultTime").toString();
				hasReport = response.body.get("hasReport").toString();
				siteTel = response.body.get("siteTel").toString();
				if (TextUtils.equals(peopleInEmergency, "1")
						&& TextUtils.equals(isMain, "0")) {
					btClosePeople.setVisibility(View.VISIBLE);
				} else {
					btClosePeople.setVisibility(View.GONE);
				}
				if (TextUtils.equals(hasChooseComponent, "1")) {
					btComponentLists.setVisibility(View.VISIBLE);
				} else {
					btComponentLists.setVisibility(View.GONE);
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
			}
		};
		conn.sendRequest();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		toolbar.setNavigationOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stackInstance.popActivity(activity);
			}
		});
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		tvNavigation.setOnClickListener(this);
		tvQrcode.setOnClickListener(this);
		tvPartners.setOnClickListener(this);
		elevatorDetail.setOnClickListener(this);
		btClosePeople.setOnClickListener(this);
		btComponentLists.setOnClickListener(this);
		tvTitle.setText(workorderCode);
		double latitude = ((MyApplication) getApplicationContext()).latitude;
		double longitude = ((MyApplication) getApplicationContext()).longitude;
	}

	private void initView() {
		// TODO Auto-generated method stub
		toolbar = (Toolbar) findViewById(R.id.toolBar);
		setSupportActionBar(toolbar);
		actionBar = getSupportActionBar();
		tvTitle = (AppCompatTextView) findViewById(R.id.tvTitle);
		tvNavigation = (AppCompatTextView) findViewById(R.id.tv_navigation);
		tvQrcode = (AppCompatTextView) findViewById(R.id.tv_qrcode);
		tvPartners = (AppCompatTextView) findViewById(R.id.tv_partner);
		elevatorDetail = (LinearLayout) findViewById(R.id.rl_elevator_detail);
		tvAddress = (AppCompatTextView) findViewById(R.id.tv_address);
		tvElevatorNo = (AppCompatTextView) findViewById(R.id.tv_elevator_no);
		tvFaultFrom = (AppCompatTextView) findViewById(R.id.tv_fault_from);
		tvRepairContent = (AppCompatTextView) findViewById(R.id.tv_repair_content);
		btClosePeople = (AppCompatButton) findViewById(R.id.bt_close_people);
		btComponentLists = (AppCompatButton) findViewById(R.id.bt_component_list);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_navigation:
			if (mapDialog == null) {
				mapDialog = new InstallerMapDialog(activity);
				mapDialog.showDialog(desLatitude, desLongitude);
			} else {
				mapDialog.showDialog(desLatitude, desLongitude);
			}

			break;
		case R.id.tv_partner:
			if (partners == null) {
				Utilities.showToast("尚未获取到搭档信息", activity);
			} else if (partners.length == 0) {
				Utilities.showToast("暂无搭档", activity);
			} else {// 有搭档
				alertBuilder
						.setTitle("搭档电话")
						.setMessage(null)
						.setSingleChoiceItems(partners, -1, null)
						.setNegativeButton("取消", null)
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										int checkPosition = ((AlertDialog) dialog)
												.getListView()
												.getCheckedItemPosition();

										Intent dial = new Intent(
												Intent.ACTION_DIAL,
												Uri.parse("tel:"
														+ listPartners
																.get(checkPosition)
																.get("partnerPhone")
																.toString()));
										startActivity(dial);
									}
								}).show();
			}

			break;
		case R.id.tv_qrcode:
			Intent i = new Intent(activity, CaptureActivity.class);
			i.putExtra(Constant.WORKORDERCODE, workorderCode);
			i.putExtra(Constant.CURRSTATE, Constant.MAINTENANCE);
			startActivity(i);
			break;
		case R.id.rl_elevator_detail:
			Intent elevatorDetail = new Intent(this,
					ElevatorDetailActivity.class);
			elevatorDetail.putExtra(Constant.ELEVATORNO, elevatorNo);
			startActivity(elevatorDetail);
			break;
		case R.id.bt_close_people:
			alertBuilder
					.setTitle("关人确认")
					.setMessage("是否关人？")
					.setNegativeButton("否",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent i2 = new Intent(activity,
											MaintenanceTaskActivity.class);
									i2.putExtra("workorderCode", workorderCode);
									i2.putExtra("isMain", isMain);
									i2.putExtra("siteTel", siteTel);
									i2.putExtra("hasReport", hasReport);
									i2.putExtra("hasChooseComponent",
											hasChooseComponent);
									startActivity(i2);
								}
							})
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent i = new Intent(activity,
											ClosePeopleSolveActivity.class);
									i.putExtra("workorderCode", workorderCode);
									i.putExtra("faultTime", faultTime);
									i.putExtra("isMain", isMain);
									i.putExtra("hasChooseComponent",
											hasChooseComponent);
									i.putExtra("hasReport", hasReport);
									startActivity(i);
								}
							}).show();
			break;
		case R.id.bt_component_list:
			Intent i2 = new Intent(activity, MaintenanceTaskActivity.class);
			i2.putExtra("workorderCode", workorderCode);
			i2.putExtra("isMain", isMain);
			i2.putExtra("siteTel", siteTel);
			i2.putExtra("hasReport", hasReport);
			i2.putExtra("hasChooseComponent", hasChooseComponent);
			startActivity(i2);
			break;
		default:
			break;
		}
	}

	//百度专用
	private void startNavicate(LatLng startPoint, LatLng endPoint,
			String endName) {
		// TODO Auto-generated method stub
		RouteParaOption para = new RouteParaOption().startName("我的位置")
				.startPoint(startPoint).endPoint(endPoint).endName(endName)
				.busStrategyType(EBusStrategyType.bus_recommend_way);
		try {
			BaiduMapRoutePlan.setSupportWebRoute(true);
			BaiduMapRoutePlan.openBaiduMapTransitRoute(para, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		stackInstance.popActivity(activity);
	}

}
