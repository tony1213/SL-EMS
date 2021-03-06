package com.overtech.ems.activity.common.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.overtech.ems.R;
import com.overtech.ems.activity.BaseFragment;
import com.overtech.ems.config.SystemConfig;
import com.overtech.ems.entity.bean.ZoneBean;
import com.overtech.ems.entity.bean.ZoneBean.City;
import com.overtech.ems.entity.bean.ZoneBean.Zone;
import com.overtech.ems.entity.common.Requester;
import com.overtech.ems.http.OkHttpClientManager;
import com.overtech.ems.http.OkHttpClientManager.ResultCallback;
import com.overtech.ems.utils.AppUtils;
import com.overtech.ems.utils.Logr;
import com.overtech.ems.utils.Utilities;
import com.overtech.ems.widget.EditTextWithDelete;
import com.squareup.okhttp.Request;

public class RegisterAddPersonInfoFragment extends BaseFragment implements
		OnClickListener {
	private View view;
	private Context mContext;
	private Button mNext;
	private EditTextWithDelete mName;
	private EditTextWithDelete mIdNum;
	private EditTextWithDelete mWorkNum;
	private TextView mCity;
	private AppCompatSpinner mZone;
	private ArrayAdapter<Zone> adapter;
	private int spSelectPosition;//记录spinner选择的位置
	public String nameContent = null;
	public String idNumContent = null;
	public String workNumContent = null;
	private final int SELECTCITY = 0x0023;
	public String cityName;
	public String cityCode;
	public String zoneName;
	public String zoneCode;
	private RegAddPerInfoFrgClickListener listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(savedInstanceState!=null){
			nameContent=savedInstanceState.getString("nameContent");
			idNumContent=savedInstanceState.getString("idNumContent");
			workNumContent=savedInstanceState.getString("workNumContent");
			cityName=savedInstanceState.getString("cityName");
			cityCode=savedInstanceState.getString("cityCode");
			zoneName=savedInstanceState.getString("zoneName");
			zoneCode=savedInstanceState.getString("zoneCode");
		}
		view = inflater.inflate(R.layout.fragment_register_add_person_info,
				container,false);
		findViewById(view);
		return view;
	}

	private void findViewById(View v) {
		mNext = (Button) v.findViewById(R.id.btn_next_fragment);
		mName = (EditTextWithDelete) v.findViewById(R.id.et_register_add_name);
		mIdNum = (EditTextWithDelete) v
				.findViewById(R.id.et_register_add_id_card);
		mWorkNum = (EditTextWithDelete) v
				.findViewById(R.id.et_register_add_workno);
		mCity = (TextView) v.findViewById(R.id.tv_add_city);
		mZone = (AppCompatSpinner) v.findViewById(R.id.sp_add_zone);

		mNext.setOnClickListener(this);

		mCity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						SelectCityActivity.class);
				startActivityForResult(intent, SELECTCITY);
			}
		});
		mZone.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Zone zone = (Zone) parent.getItemAtPosition(position);
				spSelectPosition=position;
				zoneCode = zone.code;
				zoneName = zone.name;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_next_fragment:
			if(nameContent!=null){
				Logr.e("PersonInfo=="+nameContent);
			}
			nameContent = mName.getText().toString().trim();
			if (!Utilities.isAllChinese(nameContent)) {
				Utilities.showToast("姓名必须为中文", mContext);
				return;
			}
			idNumContent = mIdNum.getText().toString().trim();
			if (TextUtils.isEmpty(idNumContent)
					|| !AppUtils.IDCardValidate(idNumContent)) {
				Utilities.showToast("身份证输入不正确", mContext);
				return;
			}
			workNumContent = mWorkNum.getText().toString().trim();
			if (TextUtils.isEmpty(workNumContent)) {
				Utilities.showToast("上岗证编号不能为空", mContext);
				return;
			}
			if(!AppUtils.isNumberOrCharac(workNumContent)){
				Utilities.showToast("上岗证输入不合法", mContext);
				return ;
			}
			if (TextUtils.isEmpty(cityName)) {
				Utilities.showToast("您还没有选择城市", mContext);
				return;
			}
			if (TextUtils.isEmpty(zoneName)) {
				Utilities.showToast("您还没有选择区域", mContext);
				return;
			}
			Logr.e("city==" + cityName + "==citycode==" + cityCode
					+ "==zoneName==" + zoneName + "==zoneCode==" + zoneCode);
			if (listener != null) {
				listener.onRegAddPerInfoFrgClick();
			}
			break;

		default:
			break;
		}
	}
	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
		if(cityName!=null){
			mCity.setText(cityName);
		}
		if(zoneName!=null){
			mZone.setAdapter(adapter);
			mZone.setSelection(spSelectPosition);
		}
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("nameContent", nameContent);
		outState.putString("idNumContent", idNumContent);
		outState.putString("workNumContent", workNumContent);
		outState.putString("cityName", cityName);
		outState.putString("cityCode", cityCode);
		outState.putString("zoneName", zoneName);
		outState.putString("zoneCode", zoneCode);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECTCITY:
			if (resultCode == Activity.RESULT_OK) {
				cityName = data.getStringExtra("childName");
				cityCode = data.getStringExtra("childCode");
				Logr.e("cityName==" + cityName + "==cityCode==" + cityCode);
				mCity.setText(cityName);
				startRequestZoneData();
			}
			break;

		default:
			break;
		}
	}

	private void startRequestZoneData() {
		// TODO Auto-generated method stub
		startProgressDialog("正在加载区域...");
		Requester requester = new Requester();
		requester.cmd = 4;
		requester.body.put("code", cityCode);
		ResultCallback<ZoneBean> callback = new ResultCallback<ZoneBean>() {

			@Override
			public void onError(Request request, Exception e) {
				// TODO Auto-generated method stub
				stopProgressDialog();
				Logr.e(request.toString());
			}

			@Override
			public void onResponse(ZoneBean response) {
				// TODO Auto-generated method stub
				stopProgressDialog();
				if (response == null) {
					Utilities.showToast(R.string.response_no_object, mContext);
					return;
				}
				if (response.body.data != null) {
					City city = response.body.data.get(0);
					if (city != null) {
						adapter = new ArrayAdapter<Zone>(
								activity, android.R.layout.simple_spinner_item,
								city.list);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						mZone.setAdapter(adapter);
					}
				}
			}
		};
		OkHttpClientManager.postAsyn(SystemConfig.NEWIP, callback,
				gson.toJson(requester));
	}

	public void setRegAddPerInfoFrgClickListener(
			RegAddPerInfoFrgClickListener listener) {
		this.listener = listener;
	}

	public interface RegAddPerInfoFrgClickListener {
		void onRegAddPerInfoFrgClick();
	}
}
