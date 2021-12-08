package com.eclerx.flowable.loanrequestprocess.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eclerx.flowable.loanrequestprocess.pojo.Customer;
import com.eclerx.flowable.loanrequestprocess.pojo.CustomerDetails;
import com.eclerx.flowable.loanrequestprocess.repo.CustomerRepository;
import com.eclerx.flowable.loanrequestprocess.service.CustomerService;

@CrossOrigin
@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@PostMapping("/addcustomer")
	public String addCustomer(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);
		return "Customer Added Successfully.";
	}
	@GetMapping("/allcustomers")
	public List<Customer> fetchAllCustomers(){
		return customerService.findAllCustomers();
	}
	@GetMapping("/customer/{status}")
	public List<Customer> findCustomerbyStatus(@PathVariable String status){
		return customerService.findCustomerByStatus(status);
	}
	@DeleteMapping("/delete/{id}")
	public void removeCustomerById(@PathVariable Integer custId) {
		customerService.removeCustomer(custId);
	}
	@PutMapping("/customer/{id}/{status}")
	public ResponseEntity<String> changeStatusById(@PathVariable int id,@RequestBody @PathVariable String status){
		return new ResponseEntity<String>(customerService.changeLoanStatus(id, status),HttpStatus.OK);
	}
	@GetMapping("/customer/{id}")
	public Customer findCustomerById(@PathVariable int id) {
		return customerService.findCustomerById(id);
	}

	
	@GetMapping("/customers/{taskname}")
	public List<String[]> findLoanReviewCustomers(@PathVariable String taskname){
		return customerRepo.findCustomerDetails(taskname);
	}
//	@GetMapping("/loanapprovalcustomers")
//	public List<String[]> findLoanApprovalCustomers(String str2){
//		return customerRepo.findCustomerDetails(str2);
//	}
	
	@GetMapping("/customers/assignee/{assigneeUserId}")
	public List<Customer[]> findCustomersByAssignee(@PathVariable String assigneeUserId){
		return customerRepo.findCustomerDetailsByAssignee(assigneeUserId);
	}

}
