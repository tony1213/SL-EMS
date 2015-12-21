package com.overtech.ems.entity.parttime;

/**
 * Created by Tony1213 on 15/12/16.
 * 任务包
 */
public class TaskPackage {
	private int elevatorAmounts;    //电梯数量
	private int isFinish;			//抢单人数
	private String latitude;           //纬度
	private String longitude;          //经度
	private String maintenanceAddress; //维保地点
	private long maintenanceDate;    //维保日期
    private String projectName;        //项目名称
    private int topState;			//是否置顶
	public TaskPackage(String projectName, int elevatorAmounts,
			String maintenanceAddress, String longitude, String latitude,
			long maintenanceDate, int isFinish, int topState) {
		super();
		this.projectName = projectName;
		this.elevatorAmounts = elevatorAmounts;
		this.maintenanceAddress = maintenanceAddress;
		this.longitude = longitude;
		this.latitude = latitude;
		this.maintenanceDate = maintenanceDate;
		this.isFinish = isFinish;
		this.topState = topState;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public int getElevatorAmounts() {
		return elevatorAmounts;
	}
	public void setElevatorAmounts(int elevatorAmounts) {
		this.elevatorAmounts = elevatorAmounts;
	}
	public String getMaintenanceAddress() {
		return maintenanceAddress;
	}
	public void setMaintenanceAddress(String maintenanceAddress) {
		this.maintenanceAddress = maintenanceAddress;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public long getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(long maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
	public int getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
	}
	public int getTopState() {
		return topState;
	}
	public void setTopState(int topState) {
		this.topState = topState;
	}
	

}