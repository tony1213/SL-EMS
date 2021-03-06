package com.overtech.ems.activity.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.overtech.ems.R;

public class PersonalAccountNoCountAdapter extends BaseAdapter {

	private Context context;
	private List<Map<String, Object>> list;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public PersonalAccountNoCountAdapter(Context context,
			List<Map<String, Object>> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Map<String, Object> data = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_personal_no_account, null);
			holder.mTaskName = (TextView) convertView
					.findViewById(R.id.tv_personal_village_name);
			holder.mMaintenanceTime = (TextView) convertView
					.findViewById(R.id.tv_personal_maintenance_time);
			holder.mTaskNo = (TextView) convertView
					.findViewById(R.id.tv_personal_taskno);
			holder.mTaskMoney = (TextView) convertView
					.findViewById(R.id.tv_personal_account);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTaskName.setText(data.get("taskPackageName").toString());
		holder.mMaintenanceTime.setText(data.get("maintenanceDate").toString());
		holder.mTaskNo.setText(data.get("taskNo").toString());
		holder.mTaskMoney.setText("￥" + data.get("totalPrice"));
		return convertView;
	}

	class ViewHolder {
		public TextView mTaskName;
		public TextView mMaintenanceTime;
		public TextView mTaskNo;
		public TextView mTaskMoney;
	}

	public void setData(List<Map<String, Object>> data) {
		// TODO Auto-generated method stub
		this.list=data;
	}
}
