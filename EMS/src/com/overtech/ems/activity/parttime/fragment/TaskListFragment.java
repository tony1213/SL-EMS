package com.overtech.ems.activity.parttime.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.overtech.ems.R;
import com.overtech.ems.activity.parttime.MainActivity;
import com.overtech.ems.activity.parttime.tasklist.ScanCodeActivity;
import com.overtech.ems.activity.parttime.tasklist.TaskListDonetFragment;
import com.overtech.ems.activity.parttime.tasklist.TaskListNoneFragment;

public class TaskListFragment extends Fragment implements OnClickListener {

	private Activity mActivity;
	private Button mNone;
	private Button mDonet;
	private TextView mHead;
	private TextView mHeadRightContent;
	private FragmentManager manager;
	private FragmentTransaction transaction;
	private Fragment mTaskNone;
	private Fragment mTaskDonet;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_task_list, container,
				false);
		initView(view);
		setDefaultView();
		return view;
	}

	private void initView(View view) {
		mNone=(Button) view.findViewById(R.id.btn_task_list_title_none);
		mDonet=(Button) view.findViewById(R.id.btn_task_list_title_donet);
		mHead=(TextView) view.findViewById(R.id.tv_headTitle);
		mHeadRightContent=(TextView) view.findViewById(R.id.tv_headTitleRight);		
		mNone.setOnClickListener(this);
		mDonet.setOnClickListener(this);
		mHeadRightContent.setOnClickListener(this);
		manager = getFragmentManager();
		mTaskNone=new TaskListNoneFragment();
		mTaskDonet=new TaskListDonetFragment();
	}
	
	private void setDefaultView() {
		mHead.setText("任务单");
		mHeadRightContent.setText("开始");
		transaction=manager.beginTransaction();
		transaction.replace(R.id.fl_container, mTaskNone).commit();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btn_task_list_title_none:
			switchContent(mTaskDonet,mTaskNone);
			mNone.setBackgroundResource(R.drawable.btn_selector_left_blue);
			mDonet.setBackgroundResource(R.drawable.btn_selector_right_white);
			mNone.setTextColor(getResources().getColor(R.color.main_white));
			mDonet.setTextColor(Color.rgb(0, 163, 233));
			break;
		case R.id.btn_task_list_title_donet:
			switchContent(mTaskNone,mTaskDonet);
			mNone.setBackgroundResource(R.drawable.btn_selector_left_white);
			mDonet.setBackgroundResource(R.drawable.btn_selector_right_blue);
			mNone.setTextColor(Color.rgb(0, 163, 233));
			mDonet.setTextColor(getResources().getColor(R.color.main_white));
			break;
		case R.id.tv_headTitleRight:
			Intent intent = new Intent();
			intent.setClass(mActivity, ScanCodeActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private void switchContent(Fragment from, Fragment to) {
		transaction = manager.beginTransaction();
		if (!to.isAdded()) { 
			transaction.hide(from).add(R.id.fl_container, to).commit(); 
		} else {
			transaction.hide(from).show(to).commit();
		}
	}
}
