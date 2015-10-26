package com.overtech.ems.activity.adapter;

import java.util.List;

import com.overtech.ems.R;
import com.overtech.ems.entity.test.Data;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

	private List<Data> list;
	private Context context;

	public List<Data> getData() {
		return list;
	}

	public void setData(List<Data> data) {
		this.list = data;
	}

	public TaskListAdapter(List<Data> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	public TaskListAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return 50;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.item_list_tasklist, null);
			new ViewHolder(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.tv_name.setText("南虹小区" + position);
		return convertView;
	}

	class ViewHolder {
		TextView tv_name;

		public ViewHolder(View view) {
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			view.setTag(this);
		}
	}

}