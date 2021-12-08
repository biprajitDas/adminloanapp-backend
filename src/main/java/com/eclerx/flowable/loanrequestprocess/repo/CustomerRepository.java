package com.eclerx.flowable.loanrequestprocess.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.eclerx.flowable.loanrequestprocess.pojo.Customer;
import com.eclerx.flowable.loanrequestprocess.pojo.CustomerDetails;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
	
	@Query("FROM Customer WHERE status=:state")
	List<Customer> findCustomersByStatus(String state);
	
	@Query("FROM Customer WHERE customerId=:id")
	Customer findCustomerById(int id);
	
	@Query("FROM Customer WHERE processDefinitionKey=:ProcessKey")
	Customer findCustomerByProcessId(String ProcessKey);
	
	@Query("FROM Customer WHERE current_task_id=:TaskId")
	Customer findCustomerByTaskId(String TaskId);
	
	@Query(value="SELECT process_instance_id FROM Customer u WHERE u.current_task_id=:TaskId", nativeQuery = true)
	String findCurrentProcessInstaceId(String TaskId);
	
	@Query("FROM Customer WHERE process_instance_id=:processInstaId")
	Customer findCustomerByProcessInstanceId(String processInstaId);
	
	@Query(value="SELECT first_name,last_name,date_of_birth,address,city,state,country,pincode,pannumber,aadharnumber,uploadaadharcard,uploadpancard,loan_type,loanterm,mail,current_task_id,current_task_name FROM Customer c WHERE c.current_task_name=:taskName",nativeQuery = true)
	List<String[]> findCustomerDetails(String taskName);
	

//	@Query(value="SELECT first_name,last_name,date_of_birth,address,city,state,country,pincode,pannumber,aadharnumber,uploadaadharcard,uploadpancard,loan_type,loanterm,mail,current_task_id,current_task_name FROM Customer c WHERE c.current_task_assignee=:assigneeUserId",nativeQuery = true)
//	List<String[]> findCustomerDetailsByAssignee(String assigneeUserId);
	@Query(value="SELECT * FROM Customer c WHERE c.current_task_assignee=:assigneeUserId",nativeQuery = true)
	List<Customer[]> findCustomerDetailsByAssignee(String assigneeUserId);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.status=:state WHERE c.current_task_id=:current_task_id1",nativeQuery = true)
	void changeCustomerStatus(String current_task_id1,String state);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.status=:state WHERE c.process_instance_id=:processInstanceId",nativeQuery = true)
	void changeCustomerStatusByProcessInstanceId(String processInstanceId,String state);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.status=:state WHERE c.customer_id=:id",nativeQuery = true)
	void changeCustomerStatusByCustomerId(int id,String state);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.current_task_id=:newtaskId WHERE c.process_instance_id=:processInstaceId",nativeQuery = true)
	void changeCurrentTaskId(String processInstaceId,String newtaskId);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.current_task_name=:newtaskName WHERE c.process_instance_id=:processInstaceId",nativeQuery = true)
	void changeCurrentTaskName(String processInstaceId,String newtaskName);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.current_task_assignee=:current_task_assignee1 WHERE c.process_instance_id=:processInstaceId",nativeQuery = true)
	void changeCurrentTaskAssignee(String processInstaceId,String current_task_assignee1);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.reviewer_comment=:comment WHERE c.process_instance_id=:processInstanceId",nativeQuery = true)
	void changeCustomerReviewerCommentByProcessInstanceId(String processInstanceId,String comment);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.approver_comment=:comment WHERE c.process_instance_id=:processInstanceId",nativeQuery = true)
	void changeCustomerApproverCommentByProcessInstanceId(String processInstanceId,String comment);
	
	@Query(value="SELECT DOUBLE_ from act_ru_variable a WHERE a.PROC_INST_ID_ =:processInstaId AND a.NAME_='loaninterest'",nativeQuery = true)
	double getLoanInterestByProcessInstaceId(String processInstaId);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.loaninterest=:loaninterest1 WHERE c.process_instance_id =:processInstaId",nativeQuery=true)
	void changeLoanInterestByProcessInstanceId(String processInstaId,double loaninterest1);
	
}
