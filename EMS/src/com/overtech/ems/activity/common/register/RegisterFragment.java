package com.overtech.ems.activity.common.register;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.overtech.ems.R;
import com.overtech.ems.utils.Utilities;
import com.overtech.ems.widget.EditTextWithDelete;
import com.overtech.ems.widget.TimeButton;

public class RegisterFragment extends Fragment implements OnClickListener {
	private Context mContext;
	private View view;
	private TimeButton mGetValidate;
	private EditTextWithDelete mRegisterPhone;
	private EditText mEtValidateCode;
	private Button mSubmitValidate;
	private String validateCode;
	private TextView mHeadTitle;
	private ImageView mDoBack;
	private Button mNext;
	public String mPhoneNo;
	/**
	 * 验证码是否正确
	 */
	public boolean isCorrect;
	private RegFraBtnClickListener listener;
	private EventHandler eh = new EventHandler() {
		@Override
		public void afterEvent(int event, int result, Object data) {
			// TODO Auto-generated method stub
			super.afterEvent(event, result, data);
			Message msg = new Message();
			msg.arg1 = event;
			msg.arg2 = result;
			msg.obj = data;
			handler.sendMessage(msg);
		}
	};
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			if (result == SMSSDK.RESULT_COMPLETE) {
				if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					Utilities.showToast("验证码已发送", mContext);
				} else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					isCorrect = true;
					Utilities.showToast("验证码正确", mContext);
				}
			} else {
				try {
					Throwable throwable = (Throwable) data;
					throwable.printStackTrace();
					JSONObject object = new JSONObject(throwable.getMessage());
					int status = object.optInt("status");
					Utilities.showToast("验证码错误：" + status, mContext);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		};
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mContext = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_register, null);
		findViewById(view);
		init();
		SMSSDK.registerEventHandler(eh);
		return view;
	}

	private void init() {
		mHeadTitle.setText("注册");
		mDoBack.setVisibility(View.VISIBLE);
		mDoBack.setOnClickListener(this);
		mNext.setOnClickListener(this);
		mSubmitValidate.setOnClickListener(this);
		mGetValidate.setOnClickListener(this);
	}

	private void findViewById(View v) {
		mHeadTitle = (TextView) v.findViewById(R.id.tv_headTitle);
		mDoBack = (ImageView) v.findViewById(R.id.iv_headBack);
		mNext = (Button) v.findViewById(R.id.btn_next_fragment);
		mRegisterPhone = (EditTextWithDelete) v
				.findViewById(R.id.et_register_phone);
		mGetValidate = (TimeButton) v.findViewById(R.id.btn_get_valicate_code);
		mEtValidateCode = (EditText) v.findViewById(R.id.et_valicate_code);
		mSubmitValidate = (Button) v.findViewById(R.id.btn_valicate_code);
	}

	public void setRegFraBtnClickListener(RegFraBtnClickListener listener) {
		this.listener = listener;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_headBack:
			getActivity().onBackPressed();
			break;
		case R.id.btn_get_valicate_code:// 获取验证码
			isCorrect=false;
			mPhoneNo = mRegisterPhone.getText().toString().trim();
			if (!TextUtils.isEmpty(mPhoneNo) && Utilities.isMobileNO(mPhoneNo)) {
				mGetValidate.setTextAfter("秒后重试").setTextBefore("重新获取验证码")
						.setLenght(60 * 1000).setEnabled(false);
				SMSSDK.getVerificationCode("86", mPhoneNo);
			} else {
				Utilities.showToast("请输入正确的手机号", mContext);
				mGetValidate.setTextAfter("获取验证码").setTextBefore("获取验证码").setLenght(0).setEnabled(true);
			}
			break;
		case R.id.btn_valicate_code:// 提交验证码
			validateCode = mEtValidateCode.getText().toString().trim();
			if (TextUtils.isEmpty(validateCode)) {
				Utilities.showToast("验证码不能为空", mContext);
			} else {
				SMSSDK.submitVerificationCode("86", mPhoneNo, validateCode);
			}
			break;
		case R.id.btn_next_fragment:
			if (listener != null) {
				listener.onRegFraBtnClick();
			}
			break;

		default:
			break;
		}
	}

	public interface RegFraBtnClickListener {
		void onRegFraBtnClick();
	}
}
