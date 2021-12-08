package com.eclerx.flowable.loanrequestprocess.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.flowable.dmn.api.DmnDecisionService;
import org.flowable.dmn.engine.DmnEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import org.springframework.web.bind.annotation.RequestBody;

import com.eclerx.flowable.loanrequestprocess.pojo.Customer;
import com.eclerx.flowable.loanrequestprocess.pojo.TaskInstanceJson;
import com.eclerx.flowable.loanrequestprocess.repo.CustomerRepository;
import com.eclerx.flowable.loanrequestprocess.service.CustomerService;
import com.eclerx.flowable.loanrequestprocess.service.TaskInstanceService;

@Service
public class TaskInstanceServiceImpl implements TaskInstanceService {

	private Logger logger = LoggerFactory.getLogger(TaskInstanceServiceImpl.class);
    @Autowired
	private RuntimeService runtimeService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private TaskService taskService;
    private Customer customer;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private DmnDecisionService dmnruleService;
    
	@Override
	public List<TaskInstanceJson> getTaskInstances(String assignee) {
		logger.info("Retrieving in progress task by assignee : {}", assignee);

		List<TaskInstanceJson> taskInstanceJsons = new ArrayList<TaskInstanceJson>();
		try {
			List<Task> tasks = taskService.createTaskQuery().active().taskAssignee(assignee).list();
			for (Task task : tasks) {
				TaskInstanceJson taskInstanceJson = new TaskInstanceJson();
				taskInstanceJson.setTaskId(task.getId());
				taskInstanceJson.setTaskName(task.getName());
				taskInstanceJson.setAssignee(task.getAssignee());
				taskInstanceJson.setStartTime(task.getCreateTime());
				taskInstanceJsons.add(taskInstanceJson);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskInstanceJsons;
	}



	@Override
	public String complteTask(String taskId, Map<String, Object> taskVars) {
		
		
		try {
		     String processInstanceId = customerRepo.findCurrentProcessInstaceId(taskId);
		     logger.info("***************Current Process Instance Id:");
		     logger.info(processInstanceId);
			String current_task_name1=customerService.findCurrentTaskName(processInstanceId);
			taskService.complete(taskId, taskVars);
			Object isDetailsMissing =taskVars.get("detailsmissing");
            Object reviewSuccess = taskVars.get("isreviewsuccess");
            Object isLoanApproved = taskVars.get("loanapprove");
            String reviewerComment = (String) taskVars.get("reviewercomment");
            String approverComment = (String) taskVars.get("approvercomment");
           // List<Map<String, Object>> outputVariables = dmnRuleService.executeDecisionByKey(decisionTableInputJson.getDecisionKey(), decisionTableInputJson.getInputVariables());
            if(current_task_name1.equals("Loan Review")) {
            	logger.info("*******Loan Review******");
            	 if(isDetailsMissing==(Object)true) {
                 	//customer.setStatus("Details Missing");
            		 //customerRepo.changeCustomerStatus(taskId,"Details Misiing"); 
            		 customerRepo.changeCustomerStatusByProcessInstanceId(processInstanceId, "Details Missing");
            		 logger.info("****************Status Changed to :Details Missing");
            		
                 }
                 else if(reviewSuccess==(Object)true) {
                 	logger.info("review success"); 
                 	//customer.setStatus("Review Success");
                 	//customerRepo.changeCustomerStatus(taskId,"Review Success"); 
                 	customerRepo.changeCustomerStatusByProcessInstanceId(processInstanceId, "Review Success");
                 	logger.info("**********************Status Changed to : Review Success");
                    double  loaninterest = customerRepo.getLoanInterestByProcessInstaceId(processInstanceId);
                 	customerRepo.changeLoanInterestByProcessInstanceId(processInstanceId, loaninterest);
                    logger.info("*****Loan interest changed*******");                 	
                 	
                 	           }
                 else if(reviewSuccess==(Object)false) {
                 	logger.info("review failed"); 
                 	//customer.setStatus("Review Failed");
                 	//customerRepo.changeCustomerStatus(taskId,"Review Failed"); 
                 	customerRepo.changeCustomerStatusByProcessInstanceId(processInstanceId, "Review Failed");
                 	logger.info("*********************Status Changed to : Review Fail");
      
                 }
            	 customerRepo.changeCustomerReviewerCommentByProcessInstanceId(processInstanceId,reviewerComment);
            }
            else if(current_task_name1.equals("Loan Request Update")) {
            	logger.info("******* In Loan Request Update******");
            	Customer customer = customerRepo.findCustomerByProcessInstanceId(processInstanceId);
            	customer.setFirstname((String)taskVars.get("firstname"));
            	customer.setLastname((String)taskVars.get("lastname"));
            	customer.setDateofbirth((Date)taskVars.get("dateofbirth"));
            	customer.setAddress((String)taskVars.get("address"));
            	customer.setCity((String)taskVars.get("city"));
            	customer.setState((String)taskVars.get("state"));
            	customer.setCountry((String)taskVars.get("country"));
            	customer.setPincode((String)taskVars.get("pincode"));
            	customer.setPannumber((String)taskVars.get("pannumber"));
            	customer.setAadharnumber((String)taskVars.get("aadharnumber"));
            	customer.setUploadaadharcard((byte[])taskVars.get("uploadaadharcard"));
            	customer.setUploadpancard((byte[])taskVars.get("uploadpancard"));
            	customer.setLoantype((String)taskVars.get("loantype"));
            	customer.setLoanterm((String)taskVars.get("loanterm"));
            	customer.setMail((String)taskVars.get("mail"));
            	customer.setStatus("Details Updated");
            	logger.info("*********************Status Changed to : Details updated");
            	customerRepo.save(customer);

            }
            else if(current_task_name1.equals("Loan Approval")) {
            	logger.info("*******Loan Approval******");
            	 if(isLoanApproved==(Object)true) {
                 	//customer.setStatus("Loan Approved");
            		// customerRepo.changeCustomerStatus(taskId,"Loan Approved"); 
            		 customerRepo.changeCustomerStatusByProcessInstanceId(processInstanceId, "Loan Approved");
            		 logger.info("*********************Status Changed to : Loan Approved");
                 }
                 else if(isLoanApproved==(Object)false) {
                 	logger.info("loan success"); 
                 	//customer.setStatus("Loan Rejected");
                 	//customerRepo.changeCustomerStatus(taskId,"Loan Rejected"); 
                 	customerRepo.changeCustomerStatusByProcessInstanceId(processInstanceId, "Loan Rejected");
                 	logger.info("*********************Status Changed to : Loan Rejected");
                 }
            	 customerRepo.changeCustomerApproverCommentByProcessInstanceId(processInstanceId, approverComment);
            }
           
             String current_task_assignee = customerService.getTaskAssineeByProcessInstaId(processInstanceId);
             logger.info("**********8current_task_assignee*****************");
             logger.info(current_task_assignee);
             String current_task_id= customerService.findCurrentTaskId(processInstanceId);
             String current_task_name=customerService.findCurrentTaskName(processInstanceId);
 			 customerRepo.changeCurrentTaskId(processInstanceId, current_task_id);
 			 customerRepo.changeCurrentTaskName(processInstanceId, current_task_name);
 			 customerRepo.changeCurrentTaskAssignee(processInstanceId, current_task_assignee);
			
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Task " + taskId + "completed successfully";
	}



	@Override
	public List<TaskInstanceJson> getTaskbyProcessId(String processId) {
	logger.info("Retrieving in progress task by process ID : {}", processId);
		
		List<TaskInstanceJson> taskInstances = new ArrayList<TaskInstanceJson>();
		
		try {
			List<Task> tasks= taskService.createTaskQuery().active().processDefinitionKey(processId).list();
			
			for(Task task: tasks) {
				TaskInstanceJson obj = new TaskInstanceJson();
				obj.setProcessId(processId);
				obj.setAssignee(task.getAssignee());
				obj.setTaskId(task.getId());
				obj.setStartTime(task.getCreateTime());
				obj.setTaskName(task.getName());
				
				taskInstances.add(obj);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return taskInstances;
	}
	public String getTaskNameByProcessInstaId(String processInstanceId) {
//		List<Task> tasks= taskService.createTaskQuery().active().processDefinitionKey(processid).list();
		List<Task> tasks= taskService.createTaskQuery().active().processInstanceId(processInstanceId).list();
		String taskName="";
	    for(Task task:tasks) {
	    	taskName = task.getName();
	    }
	    return taskName;
	}
	public String getTaskIdByProcessInstaId(String processInstanceId) {
//		List<Task> tasks= taskService.createTaskQuery().active().processDefinitionKey(processid).list();
		List<Task> tasks= taskService.createTaskQuery().active().processInstanceId(processInstanceId).list();
		String taskId="";
	    for(Task task:tasks) {
	    	taskId = task.getId();
	    	
	    }
	    return taskId;
	}
	public String getTaskAssineeByProcessInstaId(String processInstanceId) {
//		List<Task> tasks= taskService.createTaskQuery().active().processDefinitionKey(processid).list();
		List<Task> tasks= taskService.createTaskQuery().active().processInstanceId(processInstanceId).list();
		String taskAssignee="";
	    for(Task task:tasks) {
	    	taskAssignee = task.getAssignee();
	    		
	    	
	    }
	    return taskAssignee;
	}

}
