package com.eclerx.flowable.loanrequestprocess.service;


import java.util.List;
import java.util.Map;

import org.flowable.engine.runtime.Execution;
import org.springframework.web.multipart.MultipartFile;

import com.eclerx.flowable.loanrequestprocess.pojo.ProcessInstanceJson;

public interface ProcessDefinitionService {
	public String startProcessInstance(String processKey, Map<String, Object> processVars);

	public List<ProcessInstanceJson> getProcessInstances();
	
	public List<ProcessInstanceJson> getProcessByName(String processDefinitionId);
	public Execution getProcessInstanceFromRequest(String processInstanceId);
}

