package com.mesabrook.ib.util.apiaccess;

import java.util.concurrent.ConcurrentLinkedQueue;

public class DataRequestQueue {
	private volatile boolean running = false;
	private volatile boolean cancel = false;
	static
	{
		INSTANCE = new DataRequestQueue();
	}
	
	private DataRequestQueue() {}
	public static DataRequestQueue INSTANCE;

	public boolean start()
	{
		if (running)
		{
			return false;
		}
		
		Thread thread = new Thread(() -> run(), "IB Data Request Queue");
		thread.start();
		return true;
	}
	
	public boolean stop()
	{
		if (!running)
		{
			return false;
		}
		
		cancel = true;
		return true;
	}
	
	public void addTask(DataRequestTask task)
	{
		task.setStatus(DataRequestTaskStatus.Queued);
		requestTasks.add(task);
	}
	
	private ConcurrentLinkedQueue<DataRequestTask> requestTasks = new ConcurrentLinkedQueue<>();
	private void run()
	{
		try
		{
			while(!cancel)
			{
				running = true;
				DataRequestTask task;
				while((task = requestTasks.poll()) != null)
				{
					task.setStatus(DataRequestTaskStatus.Running);
					task.getTask().execute();
					task.setStatus(DataRequestTaskStatus.Complete);
					
					if (cancel)
					{
						return;
					}
					
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						return;
					}
				}
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		finally
		{
			requestTasks.clear();
			running = false;
		}
	}
}
