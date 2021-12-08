package com.eclerx.flowable.loanrequestprocess.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eclerx.flowable.loanrequestprocess.pojo.Customer;
import com.eclerx.flowable.loanrequestprocess.repo.CustomerRepository;
import com.eclerx.flowable.loanrequestprocess.service.impl.TaskInstanceServiceImpl;

@Component
public class CustomerService {
	
   @Autowired
   private CustomerRepository customerRepo;
   
   @Autowired
   private TaskInstanceServiceImpl taskService; 
   public void saveCustomer(Customer cust) {
	   customerRepo.save(cust);
   }
   
   public String changeLoanStatus(int customerId,Map<String,Object>updatedDetails) {
	   Customer customer = customerRepo.findCustomerById(customerId);
	   String s="status";
	   Object obj=s;
	   String statusKey = (String)updatedDetails.get(obj);
	   customer.setStatus(statusKey);
	   customerRepo.save(customer);
	   return "Customer Updated Successfully";
   }

   public String changeLoanStatus(int customerId,String status){
	customerRepo.changeCustomerStatusByCustomerId(customerId, status);
	return customerId+"Status Updated";
   }
   public List<Customer> findCustomerByStatus(String status){
	   return customerRepo.findCustomersByStatus(status);
  }
   public List<Customer> findAllCustomers(){
	   return customerRepo.findAll();
   }
   public String removeCustomer(int customerId) {
	   customerRepo.deleteById(customerId);
	   return "Customer Removed Successfully.";
   }
   public Customer findCustomerByTaskId(String ProcessId) {
	   Customer customer = customerRepo.findCustomerByProcessId(ProcessId);
	   return customer;
	   
   }
   public String findCurrentTaskId(String processInstanceId) {
	   return taskService.getTaskIdByProcessInstaId(processInstanceId);
   }
   public String findCurrentTaskName(String processInstanceId) {
	   return taskService.getTaskNameByProcessInstaId(processInstanceId);
   }
   public Customer findCustomerById(int id) {
	   return customerRepo.findCustomerById(id);
   }
   public String getTaskAssineeByProcessInstaId(String processInstanceId) {
	   return taskService.getTaskAssineeByProcessInstaId(processInstanceId);
   }

}
