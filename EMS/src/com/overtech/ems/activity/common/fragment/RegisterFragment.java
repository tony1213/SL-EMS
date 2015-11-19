package com.overtech.ems.activity.common.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.overtech.ems.R;
import com.overtech.ems.activity.common.RegisterActivity;
import com.overtech.ems.utils.Utilities;
import com.overtech.ems.widget.EditTextWithDelete;
import com.overtech.ems.widget.ValicateCode;

public class RegisterFragment extends Fragment {
	private Context mContext;
	private View view;
	private ImageView mValicateCodeImage;
	private ValicateCode instance;
	private Button mGetValicateCode;
	private EditTextWithDelete mRegisterPhone;
	private EditText mValicateCode;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext=activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_register, null);
		findViewById(view);
		init();
		
		mGetValicateCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mValicateCodeImage.setImageBitmap(instance.createBitmap());
			}
		});
		
		return view;
	}
	public boolean isCorrect(){
		String phoneNo = mRegisterPhone.getText().toString().trim();
		String valicateCode = mValicateCode.getText().toString().trim();
		boolean isCorrectCode = instance.getCode().equalsIgnoreCase(
				valicateCode);
		if (TextUtils.isEmpty(phoneNo)
				|| TextUtils.isEmpty(valicateCode)) {
			Utilities.showToast("输入不能为空", mContext);
			return false;
		} else {
			if (Utilities.isMobileNO(phoneNo)) {
				if (isCorrectCode) {
					// 网络部分逻辑应该添加在此处，目前直接跳轉到下一個界面
					return true;
				} else {
					Utilities.showToast("验证码输入错误", mContext);
					return false;
				}
			} else {
				Utilities.showToast("手机号码输入不正确", mContext);
				return false;
			}
		}
	}
	private void init() {
		instance = ValicateCode.getInstance();
		mValicateCodeImage.setImageBitmap(instance.createBitmap());
	}
	private void findViewById(View v) {
		mValicateCodeImage = (ImageView) v.findViewById(R.id.iv_register_valicate_code);
		mGetValicateCode = (Button) v.findViewById(R.id.btn_get_valicate_code);
		mRegisterPhone = (EditTextWithDelete) v.findViewById(R.id.et_register_phone);
		mValicateCode = (EditText) v.findViewById(R.id.et_valicate_code);
	}
}