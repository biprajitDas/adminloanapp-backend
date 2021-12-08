package com.eclerx.flowable.loanrequestprocess.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eclerx.flowable.loanrequestprocess.pojo.TaskInstanceJson;
import com.eclerx.flowable.loanrequestprocess.service.TaskInstanceService;

@CrossOrigin
@RestController
@RequestMapping("task")
public class TaskInstanceController {

	@Autowired
	private TaskInstanceService taskInstanceService;

	@GetMapping("/tasks")
	public ResponseEntity<List<TaskInstanceJson>> startProcessInstance(@RequestHeader("assignee") String assignee) {
		return new ResponseEntity<List<TaskInstanceJson>>(taskInstanceService.getTaskInstances(assignee),
				HttpStatus.OK);

	}
	
	@GetMapping("/tasks/processId")
	public ResponseEntity<List<TaskInstanceJson>> getTaskById(@RequestHeader("processId") String processId) {
		return new ResponseEntity<List<TaskInstanceJson>>(taskInstanceService.getTaskbyProcessId(processId),
				HttpStatus.OK);

	}

	@PutMapping("/complete")
	public ResponseEntity<String> complteTask(@RequestHeader("taskId") String taskId,
			@RequestBody Map<String, Object> taskVars) {
		return new ResponseEntity<String>(taskInstanceService.complteTask(taskId, taskVars), HttpStatus.OK);
	}

}