package com.eclerx.flowable.loanrequestprocess.controller;

import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eclerx.flowable.loanrequestprocess.pojo.ProcessInstanceJson;
import com.eclerx.flowable.loanrequestprocess.service.FileStorageService;
import com.eclerx.flowable.loanrequestprocess.service.ProcessDefinitionService;


@CrossOrigin
@RestController
@RequestMapping("process")
public class ProcessDefinitionController {
	private Logger logger = LoggerFactory.getLogger(ProcessDefinitionController.class);
	@Autowired
	private ProcessDefinitionService processDefinitionService;

	  @Autowired
	  private RepositoryService repositoryService;
	  @Autowired
	  private RuntimeService runtimeService;
	  
	  @Autowired
	  private ProcessEngineConfiguration processEngineConfiguration;
	  
	
	  
	@PostMapping("/start")
	public ResponseEntity<String> startProcessInstance(@RequestHeader("processKey") String processId,
			@RequestBody Map<String, Object>  processVars
			) {	
		
		return new ResponseEntity<String>(processDefinitionService.startProcessInstance(processId, processVars),
				HttpStatus.CREATED);

	}

	@GetMapping("/process-instances")    
	public ResponseEntity<List<ProcessInstanceJson>> getProcessInstances() {
		return new ResponseEntity<List<ProcessInstanceJson>>(processDefinitionService.getProcessInstances(),
				HttpStatus.OK);

	}
	
	@GetMapping("/process-instance")
	public ResponseEntity<List<ProcessInstanceJson>> getProcessByPname(@RequestHeader ("process-id") String processDefinitionId){
		return new ResponseEntity<List<ProcessInstanceJson>>(processDefinitionService.getProcessByName(processDefinitionId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/runtime/process-instances/{processInstanceId}/diagram")
    public ResponseEntity<byte[]> getProcessInstanceDiagram(@PathVariable String processInstanceId, HttpServletResponse response) {
        ProcessInstance processInstance = (ProcessInstance) processDefinitionService.getProcessInstanceFromRequest(processInstanceId);

        ProcessDefinition pde = repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        if (pde != null && pde.hasGraphicalNotation()) {
            BpmnModel bpmnModel = repositoryService.getBpmnModel(pde.getId());
            ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            InputStream resource = diagramGenerator.generateDiagram(bpmnModel, "png", runtimeService.getActiveActivityIds(processInstance.getId()), Collections.emptyList(),
                    processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(),
                    processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader(), 1.0,processEngineConfiguration.isDrawSequenceFlowNameWithNoLabelDI());

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/png");
            try {
            	 logger.info("sending diagram");
                return new ResponseEntity<>(IOUtils.toByteArray(resource), responseHeaders, HttpStatus.OK);
            } catch (Exception e) {
                throw new FlowableIllegalArgumentException("Error exporting diagram", e);
            }

        } else {
            throw new FlowableIllegalArgumentException("Process instance with id '" + processInstance.getId() + "' has no graphical notation defined.");
        }
    }

}

