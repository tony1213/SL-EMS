package com.overtech.ems.entity.parttime;

/**
 * Created by Tony1213 on 15/12/16.
 * 任务包
 */
public class TaskPackage {
	private String taskNo;          //维保单号
	private String projectName;        //项目名称
	private String elevatorAmounts;    //电梯数量
	private String isFinish;			//抢单人数
	private String latitude;           //纬度
	private String longitude;          //经度
	private String maintenanceAddress; //维保地点
	private long maintenanceDate;    //维保日期
    private String topState;			//是否置顶
    
    
	public TaskPackage(String taskNo, String projectName,
			String elevatorAmounts, String isFinish, String latitude,
			String longitude, String maintenanceAddress, long maintenanceDate,
			String topState) {
		super();
		this.taskNo = taskNo;
		this.projectName = projectName;
		this.elevatorAmounts = elevatorAmounts;
		this.isFinish = isFinish;
		this.latitude = latitude;
		this.longitude = longitude;
		this.maintenanceAddress = maintenanceAddress;
		this.maintenanceDate = maintenanceDate;
		this.topState = topState;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getElevatorAmounts() {
		return elevatorAmounts;
	}
	public void setElevatorAmounts(String elevatorAmounts) {
		this.elevatorAmounts = elevatorAmounts;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getMaintenanceAddress() {
		return maintenanceAddress;
	}
	public void setMaintenanceAddress(String maintenanceAddress) {
		this.maintenanceAddress = maintenanceAddress;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTopState() {
		return topState;
	}
	public void setTopState(String topState) {
		this.topState = topState;
	}
	public long getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(long maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
	 
}
