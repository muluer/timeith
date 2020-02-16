package com.timeith.server;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobScheduleExample implements Job{
	private static final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerExample.class);  

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("executing job..");
		LOGGER.debug("job executed..");
	}
	
}
