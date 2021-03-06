package com.timeith.server;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobSchedulerExample extends GenericServlet{

	private static final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerExample.class);  
	private static final long serialVersionUID = 821078375800855758L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void init() throws ServletException {
		super.init();
		Scheduler scheduler = null;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			
			JobDetail jobDetail = JobBuilder
					.newJob(JobScheduleExample.class)
					.withIdentity("RSSJob")
					.build();
			ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
					.simpleSchedule()
					.withIntervalInSeconds(10)
					.repeatForever();
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withSchedule(scheduleBuilder)
					.startNow()
					.build();
			scheduler.scheduleJob(jobDetail, trigger);
			LOGGER.debug("RSSJob job scheduled..");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		super.init();
	}

}
