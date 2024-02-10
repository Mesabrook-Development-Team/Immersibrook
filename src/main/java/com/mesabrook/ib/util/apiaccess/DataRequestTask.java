package com.mesabrook.ib.util.apiaccess;

import java.util.HashMap;

public class DataRequestTask {
	private final DataAccess task;
	private final HashMap<String, Object> data = new HashMap<>();
	private DataRequestTaskStatus status = DataRequestTaskStatus.Constructing;
	
	public DataRequestTask(DataAccess task)
	{
		this.task = task;
	}

	public DataRequestTaskStatus getStatus() {
		return status;
	}

	public void setStatus(DataRequestTaskStatus status) {
		this.status = status;
	}

	public DataAccess getTask() {
		return task;
	}

	public HashMap<String, Object> getData() {
		return data;
	}
}
