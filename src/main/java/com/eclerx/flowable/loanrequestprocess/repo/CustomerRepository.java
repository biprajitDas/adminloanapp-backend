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
	
	@Query(value="SELECT COUNT(*) FROM Customer c WHERE c.current_task_assignee=:assignee AND c.current_task_name=:taskName", nativeQuery=true)
	int findNumberOfTasksByAssignee(String assignee,String taskName);
	
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

	@Query("FROM Customer WHERE approver=:assignee AND status=:approvedStatus")
	List<Customer> findApprovalStatusLoanCustomers(String assignee, String approvedStatus);
	
	@Query("FROM Customer WHERE reviewer=:assignee AND status=:approvedStatus")
	List<Customer> findReviewStatusLoanCustomers(String assignee, String approvedStatus);
	 
	@Query(value="SELECT * FROM Customer c WHERE c.current_task_assignee=:assigneeUserId",nativeQuery = true)
	List<Customer> findCustomerDetailsByAssignee(String assigneeUserId);
	
	@Query(value="SELECT COUNT(*) FROM Customer c WHERE c.reviewer=:assignee AND YEAR(c.reviewed_date) = YEAR(CURRENT_DATE()) AND MONTH(c.reviewed_date) = MONTH(CURRENT_DATE())",
			nativeQuery=true)
	int findCurrentMonthReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM Customer c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND YEAR(c.rejected_date) = YEAR(CURRENT_DATE()) AND MONTH(c.rejected_date) = MONTH(CURRENT_DATE())",
			nativeQuery=true)
	int findCurrentMonthRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM Customer c WHERE c.approver=:assignee AND YEAR(c.approved_date) = YEAR(CURRENT_DATE()) AND MONTH(c.approved_date) = MONTH(CURRENT_DATE())",
			nativeQuery=true)
	int findCurrentMonthApprovedCustomers(String assignee);
	
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND c.reviewed_date>NOW()-INTERVAL 1 MONTH",
			nativeQuery=true)
	int findLastMonthReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND c.rejected_date>NOW()-INTERVAL 1 MONTH",
			nativeQuery=true)
	int findLastMonthRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND c.approved_date>NOW()-INTERVAL 1 MONTH",
			nativeQuery=true)
	int findLastMonthApprovedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND c.reviewed_date>NOW()-INTERVAL 6 MONTH",
			nativeQuery=true)
	int findLastSixMonthsReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND c.rejected_date>NOW()-INTERVAL 6 MONTH",
			nativeQuery=true)
	int findLastSixMonthsRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND c.approved_date>NOW()-INTERVAL 6 MONTH",
			nativeQuery=true)
	int findLastSixMonthsApprovedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND MONTH(c.reviewed_date)=MONTH(NOW()-INTERVAL 1 MONTH)",
			nativeQuery=true)
	int findLastOneMonthReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND MONTH(c.rejected_date)=MONTH(NOW()-INTERVAL 1 MONTH)",
			nativeQuery=true)
	int findLastOneMonthRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND MONTH(c.approved_date)=MONTH(NOW()-INTERVAL 1 MONTH)",
			nativeQuery=true)
	int findLastOneMonthApprovedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND MONTH(c.reviewed_date)=MONTH(NOW()-INTERVAL 2 MONTH)",
			nativeQuery=true)
	int findLastTwoMonthsReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND MONTH(c.rejected_date)=MONTH(NOW()-INTERVAL 2 MONTH)",
			nativeQuery=true)
	int findLastTwoMonthsRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND MONTH(c.approved_date)=MONTH(NOW()-INTERVAL 2 MONTH)",
			nativeQuery=true)
	int findLastTwoMonthsApprovedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND MONTH(c.reviewed_date)=MONTH(NOW()-INTERVAL 3 MONTH)",
			nativeQuery=true)
	int findLastThreeMonthsReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND MONTH(c.rejected_date)=MONTH(NOW()-INTERVAL 3 MONTH)",
			nativeQuery=true)
	int findLastThreeMonthsRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND MONTH(c.approved_date)=MONTH(NOW()-INTERVAL 3 MONTH)",
			nativeQuery=true)
	int findLastThreeMonthsApprovedCustomers(String assignee);
	
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND MONTH(c.reviewed_date)=MONTH(NOW()-INTERVAL 4 MONTH)",
			nativeQuery=true)
	int findLastFourMonthsReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND MONTH(c.rejected_date)=MONTH(NOW()-INTERVAL 4 MONTH)",
			nativeQuery=true)
	int findLastFourMonthsRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND MONTH(c.approved_date)=MONTH(NOW()-INTERVAL 4 MONTH)",
			nativeQuery=true)
	int findLastFourMonthsApprovedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE c.reviewer=:assignee AND MONTH(c.reviewed_date)=MONTH(NOW()-INTERVAL 5 MONTH)",
			nativeQuery=true)
	int findLastFiveMonthsReviewedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE (c.reviewer=:assignee OR c.approver=:assignee) AND MONTH(c.rejected_date)=MONTH(NOW()-INTERVAL 5 MONTH)",
			nativeQuery=true)
	int findLastFiveMonthsRejectedCustomers(String assignee);
	
	@Query(value="SELECT COUNT(*) FROM CUSTOMER c WHERE  c.approver=:assignee AND MONTH(c.approved_date)=MONTH(NOW()-INTERVAL 5 MONTH)",
			nativeQuery=true)
	int findLastFiveMonthsApprovedCustomers(String assignee);
	
	
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
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.approved_date=:date WHERE c.process_instance_id =:processInstaId",nativeQuery=true)
	void changeApprovedDateByProcessInstanceId(String processInstaId,Date date);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.reviewed_date=:date WHERE c.process_instance_id =:processInstaId",nativeQuery=true)
	void changeReviewedDateByProcessInstanceId(String processInstaId,Date date);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.rejected_date=:date WHERE c.process_instance_id =:processInstaId",nativeQuery=true)
	void changeRejectedDateByProcessInstanceId(String processInstaId,Date date);
	
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.reviewer=:reviewer WHERE c.process_instance_id =:processInstaId",nativeQuery=true)
	
	void changeReviewerByProcessInstanceId(String processInstaId,String reviewer);
	@Transactional()
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE Customer c SET c.approver=:approver WHERE c.process_instance_id =:processInstaId",nativeQuery=true)
	void changeApproverByProcessInstanceId(String processInstaId,String approver);
	
	
	
}
