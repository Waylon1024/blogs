package top.waylon.service;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.waylon.entity.ScheduleJob;
import top.waylon.entity.ScheduleJobLog;
import top.waylon.exception.PersistenceException;
import top.waylon.mapper.ScheduleJobLogMapper;
import top.waylon.mapper.ScheduleJobMapper;
import top.waylon.util.quartz.ScheduleUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ScheduleJobService {
	@Autowired
	Scheduler scheduler;
	@Autowired
	ScheduleJobMapper schedulerJobMapper;
	@Autowired
	ScheduleJobLogMapper scheduleJobLogMapper;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		List<ScheduleJob> scheduleJobList = getJobList();
		for (ScheduleJob scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			//如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	public List<ScheduleJob> getJobList() {
		return schedulerJobMapper.getJobList();
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveJob(ScheduleJob scheduleJob) {
		if (schedulerJobMapper.saveJob(scheduleJob) != 1) {
			throw new PersistenceException("添加失败");
		}
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateJob(ScheduleJob scheduleJob) {
		if (schedulerJobMapper.updateJob(scheduleJob) != 1) {
			throw new PersistenceException("更新失败");
		}
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteJobById(Long jobId) {
		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		if (schedulerJobMapper.deleteJobById(jobId) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	public void runJobById(Long jobId) {
		ScheduleUtils.run(scheduler, schedulerJobMapper.getJobById(jobId));
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateJobStatusById(Long jobId, Boolean status) {
		if (status) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		} else {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}
		if (schedulerJobMapper.updateJobStatusById(jobId, status) != 1) {
			throw new PersistenceException("修改失败");
		}
	}

	public List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate) {
		return scheduleJobLogMapper.getJobLogListByDate(startDate, endDate);
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveJobLog(ScheduleJobLog jobLog) {
		if (scheduleJobLogMapper.saveJobLog(jobLog) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteJobLogByLogId(Long logId) {
		if (scheduleJobLogMapper.deleteJobLogByLogId(logId) != 1) {
			throw new PersistenceException("日志删除失败");
		}
	}
}
