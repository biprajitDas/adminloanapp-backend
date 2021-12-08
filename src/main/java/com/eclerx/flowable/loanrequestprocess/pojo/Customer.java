package com.eclerx.flowable.loanrequestprocess.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;




@Entity
@Table(name= "Customer", schema = "eclerx-flowable-db")
public class Customer {
	  @Id
	  @GeneratedValue
	  private Integer customerId;
	  @Column(name="First_Name",length=30)
      private String firstname;
	  @Column(name="Last_Name",length=30)
      private String lastname;
	  @Column(name="Date_Of_Birth")
	  private Date dateofbirth;
	  private String address;
	  private String city;
	  private String state;
	  private String country;
	  private String pincode;
	  private String pannumber;
	  private String aadharnumber;
	  @Lob
	  @Column(name="uploadaadharcard",length=1000)
	  private byte[] uploadaadharcard;
	  @Lob
	  @Column(name="uploadpancard",length=1000)
	  private byte[] uploadpancard;
	  @Column(name="Loan_Type",length=30)
      private String loantype;
	  private String loanamount;
	  private String loanterm;
	  private double loaninterest;
	  private String mail;
	  @Column(name="Status",length=30)
      private String status;
	  private String reviewer_comment;
	  private String approver_comment;
	  @Column(name="process_definiton_key")
	  private String processDefinitionKey;
	  @Column(name="process_instance_id")
	  private String processInstanceId;
	  @Column(name="current_task_id")
	  private String current_task_id;
	  @Column(name="current_task_name")
	  private String current_task_name;
	  @Column(name="current_task_assignee")
	  private String current_task_assignee;
	  
	public Customer() {
		super();
	}

	public Customer(String firstname, String lastname, Date dateofbirth, String address,
			String city, String state, String country, String pincode, String pannumber, String aadharnumber,
			byte[] uploadaadharcard, byte[] uploadpancard, String loantype,String loanamount, String loanterm, double loaninterest,
			String mail, String status,String reviewer_comment,
			String approver_comment,
			String processDefinitionKey, String processInstanceId, String current_task_id, String current_task_name,
			String current_task_assignee) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateofbirth = dateofbirth;
		this.address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.pincode = pincode;
		this.pannumber = pannumber;
		this.aadharnumber = aadharnumber;
		this.uploadaadharcard = uploadaadharcard;
		this.uploadpancard = uploadpancard;
		this.loantype = loantype;
		this.loanamount=loanamount;
		this.loanterm = loanterm;
		this.loaninterest = loaninterest;
		this.mail = mail;
		this.status = status;
		this.reviewer_comment=reviewer_comment;
		this.approver_comment=approver_comment;
		this.processDefinitionKey = processDefinitionKey;
		this.processInstanceId = processInstanceId;
		this.current_task_id = current_task_id;
		this.current_task_name = current_task_name;
		this.current_task_assignee = current_task_assignee;
	}

	public String getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(String loanamount) {
		this.loanamount = loanamount;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPannumber() {
		return pannumber;
	}

	public void setPannumber(String pannumber) {
		this.pannumber = pannumber;
	}

	public String getAadharnumber() {
		return aadharnumber;
	}

	public void setAadharnumber(String aadharnumber) {
		this.aadharnumber = aadharnumber;
	}

	public byte[] getUploadaadharcard() {
		return uploadaadharcard;
	}

	public void setUploadaadharcard(byte[] uploadaadharcard) {
		this.uploadaadharcard = uploadaadharcard;
	}

	public byte[] getUploadpancard() {
		return uploadpancard;
	}

	public void setUploadpancard(byte[] uploadpancard) {
		this.uploadpancard = uploadpancard;
	}

	public String getLoantype() {
		return loantype;
	}

	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	public String getLoanterm() {
		return loanterm;
	}

	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}

	public double getLoaninterest() {
		return loaninterest;
	}

	public void setLoaninterest(double loaninterest) {
		this.loaninterest = loaninterest;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
   
	public String getReviewer_comment() {
		return reviewer_comment;
	}

	public void setReviewer_comment(String reviewer_comment) {
		this.reviewer_comment = reviewer_comment;
	}

	public String getApprover_comment() {
		return approver_comment;
	}

	public void setApprover_comment(String approver_comment) {
		this.approver_comment = approver_comment;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getCurrent_task_id() {
		return current_task_id;
	}

	public void setCurrent_task_id(String current_task_id) {
		this.current_task_id = current_task_id;
	}

	public String getCurrent_task_name() {
		return current_task_name;
	}

	public void setCurrent_task_name(String current_task_name) {
		this.current_task_name = current_task_name;
	}

	public String getCurrent_task_assignee() {
		return current_task_assignee;
	}

	public void setCurrent_task_assignee(String current_task_assignee) {
		this.current_task_assignee = current_task_assignee;
	}


	
	
}
