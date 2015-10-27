package com.overtech.ems.activity.common;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.overtech.ems.R;
import com.overtech.ems.activity.BaseActivity;
import com.overtech.ems.activity.adapter.GridViewAdapter2;
import com.overtech.ems.widget.popwindow.DimPopupWindow;

public class RegisterAddPersonEduAndWorkActivity extends BaseActivity {
	private TextView mHeadContent;
	private ImageView mHeadBack;
	private Button mGoIdCard;
	private Context mActivity;
	private RelativeLayout mElevatorBrand;
	private TextView mElevator;
	private DimPopupWindow mPopupWindow;
	private GridViewAdapter2 adapter;
	private GridView mGridView;
	private Button mConfirm;
	private Button mCancle;
	private HashMap<Integer,Boolean> isSelected;
	private StringBuilder mCheckedMessage;
	private String[] data={"日立","广日","上海三菱","日本三菱",
			"通力","巨人通力","奥的斯","西子奥的斯",
			"西奥","迅达","许昌西继","东芝","蒂森克虏伯",
			"富士达","西尼","上海富士","华升富士达","浦东开灵",
			"长江斯迈普","三荣","永大","现代","华特","爱登堡",
			"新时达","崇友","德胜米高","阿尔法","上海房屋设备",
			"席尔诺","森赫（原莱茵）","大连星玛","博林特","三洋",
			"江南嘉捷","扬州三星","东南","康力","宏大","京都",
			"曼隆","巨立","帝奥","德奥","霍普曼","怡达","快速",
			"沃克斯","恒达富士","西屋","铃木","菱王"," 其他"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_add_person_edu_and_work);
		findViewById();
		init();
		mGoIdCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(
						RegisterAddPersonEduAndWorkActivity.this,
						RegisterAddIdCardActivity.class);
				startActivity(intent);
			}
		});
		dealElevator();
	}

	private void dealElevator() {
		if(adapter!=null){
			isSelected=adapter.getCheckBox();
			mCheckedMessage=new StringBuilder();
			for(int i=0;i<data.length;i++){
				if(isSelected.containsKey(i)){
					mCheckedMessage.append(data[i]+"|");
				}
			}
			mElevator.setText(mCheckedMessage.toString());
		}
	}

	private void init() {
		mHeadContent.setText("学历/工作信息");
		mHeadBack.setVisibility(View.VISIBLE);
		mElevatorBrand.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopupWindow();
			}
		});
	}

	protected void showPopupWindow() {
		mPopupWindow=new DimPopupWindow(this);
		View view=LayoutInflater.from(context).inflate(R.layout.register_gridview_elevator_brand, null);
		mGridView=(GridView) view.findViewById(R.id.gridViewElevator);
		mConfirm=(Button) view.findViewById(R.id.bt_elevator_confirm);
		mCancle=(Button) view.findViewById(R.id.bt_elevator_cancle);
		adapter=new GridViewAdapter2(data,mActivity);
		mGridView.setAdapter(adapter);
		mPopupWindow.setOutsideTouchable(false);
		mPopupWindow.setContentView(view);
		mPopupWindow.showAtLocation(mElevatorBrand, Gravity.RIGHT|Gravity.BOTTOM,0,0);
		
		mConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dealElevator();
				mPopupWindow.dismiss();
			}
		});
		mCancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
	}

	private void findViewById() {
		mActivity=RegisterAddPersonEduAndWorkActivity.this;
		mHeadContent = (TextView) findViewById(R.id.tv_headTitle);
		mHeadBack = (ImageView) findViewById(R.id.iv_headBack);
		mGoIdCard = (Button) findViewById(R.id.btn_add_id_card);
		mElevatorBrand=(RelativeLayout) findViewById(R.id.rl_register_add_info_4);
		mElevator=(TextView) findViewById(R.id.tv_elevator_message);
	}
	

}
