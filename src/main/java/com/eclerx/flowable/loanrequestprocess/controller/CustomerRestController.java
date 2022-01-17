package com.eclerx.flowable.loanrequestprocess.controller;

import java.util.ArrayList;
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


	
	@GetMapping("/customers/assignee/{assigneeUserId}")
	public List<Customer> findCustomersByAssignee(@PathVariable String assigneeUserId){
		return customerRepo.findCustomerDetailsByAssignee(assigneeUserId);
	}
   
	@GetMapping("/loanreviews/{assignee}")
	public int findLoanReviewsByAssignee(@PathVariable String assignee) {
		return customerRepo.findNumberOfTasksByAssignee(assignee,"Loan Review");
	}
	
	@GetMapping("/loanapprovals/{assignee}")
	public int findLoanApprovalsByAssignee(@PathVariable String assignee) {
		return customerRepo.findNumberOfTasksByAssignee(assignee,"Loan Approval");
	}

	
	@GetMapping("/approved-loan/{approver}")
	public List<Customer> findApprovedLoanCust(@PathVariable String approver) {
		return customerRepo.findApprovalStatusLoanCustomers(approver, "Approved");
	}
	
	@GetMapping("/rejected-loan/{approver}")
	public List<Customer> findRejectedLoanCust(@PathVariable String approver) {
		return customerRepo.findApprovalStatusLoanCustomers(approver, "Rejected");
	}

	@GetMapping("/review-failed-loan/{reviewer}")
	public List<Customer> findFailedReviewLoanCust(@PathVariable String reviewer) {
		return customerRepo.findReviewStatusLoanCustomers(reviewer, "Review Failed");
	}
	
	@GetMapping("/currentmonth/{assignee}")
	public List<Integer> findCurrentMonthsData(@PathVariable String assignee){
		List<Integer> list = new ArrayList<Integer>();
		int x = customerRepo.findCurrentMonthReviewedCustomers(assignee);
		int y = customerRepo.findCurrentMonthRejectedCustomers(assignee);
		int z = customerRepo.findCurrentMonthApprovedCustomers(assignee);
		list.add(x);
		list.add(y);
		list.add(z);
		return list;
	}
	@GetMapping("/lastmonth/{assignee}")
	public List<Integer> findLastMonthsData(@PathVariable String assignee){
		List<Integer> list = new ArrayList<Integer>();
		int x = customerRepo.findLastMonthReviewedCustomers(assignee);
		int y = customerRepo.findLastMonthRejectedCustomers(assignee);
		int z = customerRepo.findLastMonthApprovedCustomers(assignee);
		list.add(x);
		list.add(y);
		list.add(z);
		return list;
	}
	@GetMapping("/lastsixmonths/{assignee}")
	public List<Integer> findLastSixMonthsData(@PathVariable String assignee){
		List<Integer> list = new ArrayList<Integer>();
		int x = customerRepo.findLastSixMonthsReviewedCustomers(assignee);
		int y = customerRepo.findLastSixMonthsRejectedCustomers(assignee);
		int z = customerRepo.findLastSixMonthsApprovedCustomers(assignee);
		list.add(x);
		list.add(y);
		list.add(z);
		return list;
	}
	
	@GetMapping("/eachmonthdata/{assignee}")
	public List<Integer> findEachMonthsData(@PathVariable String assignee){
		List<Integer> list = new ArrayList<Integer>();
		int x = customerRepo.findCurrentMonthReviewedCustomers(assignee);
		int y = customerRepo.findCurrentMonthRejectedCustomers(assignee);
		int z = customerRepo.findCurrentMonthApprovedCustomers(assignee);
		list.add(x);
		list.add(y);
		list.add(z);
		int x2 = customerRepo.findLastOneMonthReviewedCustomers(assignee);
		int y2 = customerRepo.findLastOneMonthRejectedCustomers(assignee);
		int z2 = customerRepo.findLastOneMonthApprovedCustomers(assignee);
		list.add(x2);
		list.add(y2);
		list.add(z2);
		int x3 = customerRepo.findLastTwoMonthsReviewedCustomers(assignee);
		int y3 = customerRepo.findLastTwoMonthsRejectedCustomers(assignee);
		int z3 = customerRepo.findLastTwoMonthsApprovedCustomers(assignee);
		list.add(x3);
		list.add(y3);
		list.add(z3);
		int x4 = customerRepo.findLastThreeMonthsReviewedCustomers(assignee);
		int y4 = customerRepo.findLastThreeMonthsRejectedCustomers(assignee);
		int z4 = customerRepo.findLastThreeMonthsApprovedCustomers(assignee);
		list.add(x4);
		list.add(y4);
		list.add(z4);
		int x5 = customerRepo.findLastFourMonthsReviewedCustomers(assignee);
		int y5 = customerRepo.findLastFourMonthsRejectedCustomers(assignee);
		int z5 = customerRepo.findLastFourMonthsApprovedCustomers(assignee);
		list.add(x5);
		list.add(y5);
		list.add(z5);
		int x6 = customerRepo.findLastFiveMonthsReviewedCustomers(assignee);
		int y6 = customerRepo.findLastFiveMonthsRejectedCustomers(assignee);
		int z6 = customerRepo.findLastFiveMonthsApprovedCustomers(assignee);
		list.add(x6);
		list.add(y6);
		list.add(z6);
		
		int x7 = customerRepo.findLastSixMonthsReviewedCustomers(assignee);
		int y7 = customerRepo.findLastSixMonthsRejectedCustomers(assignee);
		int z7 = customerRepo.findLastSixMonthsApprovedCustomers(assignee);
		list.add(x7);
		list.add(y7);
		list.add(z7);
		return list;
	}
}
