package com.eclerx.flowable.loanrequestprocess.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.eclerx.flowable.loanrequestprocess.pojo.TaskInstanceJson;

public interface TaskInstanceService {
	public List<TaskInstanceJson> getTaskInstances(String assignee);
	public List<TaskInstanceJson> getTaskbyProcessId(String processId);
	public String complteTask(@RequestHeader("taskId") String taskId, @RequestBody Map<String, Object> taskVars);
}
