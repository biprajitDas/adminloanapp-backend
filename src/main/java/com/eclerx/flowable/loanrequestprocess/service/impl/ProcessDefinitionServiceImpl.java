package com.eclerx.flowable.loanrequestprocess.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.rest.service.api.BpmnRestApiInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eclerx.flowable.loanrequestprocess.pojo.Customer;
import com.eclerx.flowable.loanrequestprocess.pojo.ProcessInstanceJson;
import com.eclerx.flowable.loanrequestprocess.service.CustomerService;
import com.eclerx.flowable.loanrequestprocess.service.ProcessDefinitionService;

@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

	private Logger logger = LoggerFactory.getLogger(ProcessDefinitionServiceImpl.class);
    
	@Autowired
	private RuntimeService runtimeService;
    private Customer customer;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private TaskInstanceServiceImpl taskService;
    
    @Autowired(required=false)
    protected BpmnRestApiInterceptor restApiInterceptor;
    
	@Override
	public String startProcessInstance(String processKey, Map<String, Object> processVars) {
		logger.info("Starting process instances for process : {}", processKey);
		ProcessInstance processInstance = null;
		try {
			processInstance = runtimeService.startProcessInstanceByKey(processKey, processVars);
			
			String firstName= (String)processVars.get(String.valueOf("firstname"));
			String lastName = (String)processVars.get(String.valueOf("lastname"));
			String dateofbirth1=(String)processVars.get(String.valueOf("dateofbirth"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateofbirth = dateFormat.parse(dateofbirth1);
			logger.info("****************************************");
			logger.info(dateofbirth1);
			logger.info("****************************************");
			String address = (String)processVars.get(String.valueOf("address"));
			String city=(String)processVars.get(String.valueOf("city"));
			String state=(String)processVars.get(String.valueOf("state"));
			String country=(String)processVars.get(String.valueOf("country"));
			String pincode=(String)processVars.get(String.valueOf("pincode"));
			String pannumber=(String)processVars.get(String.valueOf("pannumber"));
			String aadharnumber=(String)processVars.get(String.valueOf("aadharnumber"));
			byte[] uploadpancard = null;
			//MultipartFile file =  (MultipartFile) processVars.get(String.valueOf("uploadpancard"));
			byte[] uploadaadharcard = null;
					//file.getBytes();
			//byte[] uploadaadharcard =null;	
					//(byte[]) processVars.get(String.valueOf("uploadaadharcard"));
			String loanType = (String)processVars.get(String.valueOf("loantype"));
			String loanamount = (String)processVars.get(String.valueOf("loanamount"));
			String loanterm = (String)processVars.get(String.valueOf("loanterm"));
			String reviewer_comment ="";
			String approver_comment="";
			String mail = (String)processVars.get(String.valueOf("mail"));
			String status = "Appliction Started";
		    String process_definition_key = processInstance.getProcessDefinitionKey();
		    String process_instance_id = processInstance.getProcessInstanceId();
			String current_task_id=taskService.getTaskIdByProcessInstaId(processInstance.getProcessInstanceId());
			String current_task_name=taskService.getTaskNameByProcessInstaId(processInstance.getProcessInstanceId());
			String current_task_assignee = customerService.getTaskAssineeByProcessInstaId(processInstance.getProcessInstanceId());
            logger.info("**********current_task_assignee*****************");
            logger.info(current_task_assignee);
			Customer customer = new Customer(firstName, lastName, dateofbirth,address, city,state,
					country,pincode, pannumber,aadharnumber, uploadaadharcard, uploadpancard,
					loanType,loanamount, loanterm,0, mail, status,reviewer_comment,approver_comment,process_definition_key,
					process_instance_id, current_task_id, current_task_name,current_task_assignee);
			customerService.saveCustomer(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("Process instance created with id : {}", processInstance.getProcessInstanceId());
		return processInstance.getProcessInstanceId();
	}

	@Override
	public List<ProcessInstanceJson> getProcessInstances() {
		logger.info("Retrving inprogress process instances");

		List<ProcessInstanceJson> processInstanceJsons = new ArrayList<ProcessInstanceJson>();
		try {
			List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
			for (ProcessInstance processInstance : processInstances) {
				ProcessInstanceJson processInstanceJson = new ProcessInstanceJson();
				processInstanceJson.setProcessInstanceId(processInstance.getProcessInstanceId());
				processInstanceJson.setProcessDefinitionId(processInstance.getProcessDefinitionKey());
				processInstanceJson.setStartUser(processInstance.getStartUserId());
				processInstanceJson.setStartDate(processInstance.getStartTime());

				processInstanceJsons.add(processInstanceJson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return processInstanceJsons;

	}

	@Override
	public List<ProcessInstanceJson> getProcessByName(String processDefinitionId) {
		logger.info("Retreiving process instances by processNameId- {}", processDefinitionId);
			
		List<ProcessInstanceJson> instancesByName= new ArrayList<ProcessInstanceJson>();
			
		try {
			List<ProcessInstance> instances= runtimeService.createProcessInstanceQuery().active().processDefinitionKey(processDefinitionId).list();
				
			for(ProcessInstance instance:instances) {
				
				ProcessInstanceJson obj = new ProcessInstanceJson();
	
				obj.setPname(instance.getProcessDefinitionName());
				obj.setProcessInstanceId(instance.getProcessInstanceId());
				obj.setProcessDefinitionId(instance.getProcessDefinitionKey());
				obj.setStartUser(instance.getStartUserId());
				obj.setStartDate(instance.getStartTime());
					
				instancesByName.add(obj);
				
			}
				
		} catch (Exception e) 
		{
				e.printStackTrace();
		}
			
		return instancesByName;
	}
    public Execution getProcessInstanceFromRequest(String processInstanceId) {
        Execution execution = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (execution == null) {
            throw new FlowableObjectNotFoundException("Could not find a process instance with id '" + processInstanceId + "'.", ProcessInstance.class);
        }
        
        if (restApiInterceptor != null) {
            restApiInterceptor.accessProcessInstanceInfoById((ProcessInstance) execution);
        }
        
        return execution;
    }
}
