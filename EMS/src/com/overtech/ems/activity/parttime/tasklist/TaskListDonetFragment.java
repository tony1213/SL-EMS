package com.overtech.ems.activity.parttime.tasklist;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.overtech.ems.R;
import com.overtech.ems.activity.BaseFragment;
import com.overtech.ems.activity.adapter.TaskListAdapter;
import com.overtech.ems.config.StatusCode;
import com.overtech.ems.entity.bean.TaskPackageBean;
import com.overtech.ems.entity.common.ServicesConfig;
import com.overtech.ems.entity.parttime.TaskPackage;
import com.overtech.ems.http.HttpEngine.Param;
import com.overtech.ems.http.constant.Constant;
import com.overtech.ems.utils.SharedPreferencesKeys;
import com.overtech.ems.utils.Utilities;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class TaskListDonetFragment extends BaseFragment {
	private ListView mDonet;
	private Activity mActivity;
	private List<TaskPackage> list;
	private TaskListAdapter adapter;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case StatusCode.TASKLIST_DONET_SUCCESS:
				String json=(String) msg.obj;
				Gson gson=new Gson();
				TaskPackageBean bean=gson.fromJson(json, TaskPackageBean.class);
				list=bean.getModel();
				if(list!=null){
					adapter=new TaskListAdapter(list, mActivity);
				}
				mDonet.setAdapter(adapter);
				break;
			case StatusCode.TASKLIST_DONET_FAILED:
				Utilities.showToast((String)msg.obj, mActivity);
				break;

			default:
				break;
			}
		};
	};
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_task_list_donet, container, false);
		findViewById(view);
		startLoading();
		return view;
	}

	
	

	private void startLoading() {
		Param param=new Param(Constant.LOGINNAME,mSharedPreferences.getString(SharedPreferencesKeys.CURRENT_LOGIN_NAME, null));
		Request request=httpEngine.createRequest(ServicesConfig.TASK_LIST_DONE, param);
		Call call=httpEngine.createRequestCall(request);
		call.enqueue(new Callback() {
			
			@Override
			public void onResponse(Response response) throws IOException {
				Message msg=new Message();
				if(response.isSuccessful()){
					msg.what=StatusCode.TASKLIST_DONET_SUCCESS;
					msg.obj=response.body().string();
				}else{
					msg.what=StatusCode.TASKLIST_DONET_FAILED;
					msg.obj="数据异常";
				}
				handler.sendMessage(msg);
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Message msg=new Message();
				msg.what=StatusCode.TASKLIST_DONET_FAILED;
				msg.obj="网络链接错误";
				handler.sendMessage(msg);
			}
		});
	}
	private void findViewById(View view) {
		mDonet=(ListView) view.findViewById(R.id.donet_task_list_listview);
	}
}
