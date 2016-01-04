package com.overtech.ems.activity.adapter;

import java.util.ArrayList;
import com.overtech.ems.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchHistoryAdapter extends BaseAdapter {

	private ArrayList<String> list;
	private Context context;

	public SearchHistoryAdapter(Context context, ArrayList<String> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size()==0 ? 0:list.size();
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
		if (convertView == null) {
			convertView = View.inflate(context,R.layout.item_list_search_history, null);
			new ViewHolder(convertView);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		String data=list.get(position);
		holder.history.setText(data);
		return convertView;
	}
	class ViewHolder {
		TextView history;
		
		public ViewHolder(View view) {
			history = (TextView) view.findViewById(R.id.tv_history_name);
			view.setTag(this);
		}
	}

}
