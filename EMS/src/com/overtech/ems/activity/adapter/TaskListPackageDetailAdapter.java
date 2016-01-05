package com.overtech.ems.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.overtech.ems.R;
import com.overtech.ems.entity.parttime.TaskPackageDetail;
import com.overtech.ems.entity.test.Data2;

public class TaskListPackageDetailAdapter extends BaseAdapter {
	
	private Context context;
	private List<TaskPackageDetail> list;
	
	
	public TaskListPackageDetailAdapter(Context context,List<TaskPackageDetail> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TaskPackageDetail data=list.get(position);
		ViewHolder vh=null;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=LayoutInflater.from(context).
					inflate(R.layout.item_list_task_list_package_detail, null);
			vh.mElevatorName=(TextView) convertView.findViewById(R.id.tv_tasklist_package_detail_name);
			vh.mElevatorProductor=(TextView) convertView.findViewById(R.id.tv_tasklist_package_detail_productor);
			vh.mElevatorNo=(TextView) convertView.findViewById(R.id.tv_tasklist_package_detail_elevator_no);
			vh.mElevatorType=(TextView)convertView.findViewById(R.id.tv_tasklist_package_detail_type);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.mElevatorName.setText(data.getElevatorName());
		vh.mElevatorProductor.setText(data.getElevatorBrand());
		vh.mElevatorNo.setText(data.getElevatorNo());
		vh.mElevatorType.setText(data.getElevatorFloor());
		return convertView;
	}
	class ViewHolder {
		TextView mElevatorName;
		TextView mElevatorProductor;
		TextView mElevatorNo;
		TextView mElevatorType;
	}
	
}
