package com.eclerx.flowable.loanrequestprocess.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;

public class CustomerDetails {

	  private Integer customerId;
      private String firstname;
      private String lastname;
	  private Date dateofbirth;
	  private String address;
	  private String city;
	  private String state;
	  private String country;
	  private String pincode;
	  private String pannumber;
	  private String aadharnumber;
	  private byte[] uploadaadharcard;
	  private byte[] uploadpancard;
      private String loantype;
	  private String loanterm;
	  private String mail;
	public CustomerDetails(Integer customerId, String firstname, String lastname, Date dateofbirth, String address,
			String city, String state, String country, String pincode, String pannumber, String aadharnumber,
			byte[] uploadaadharcard, byte[] uploadpancard, String loantype, String loanterm, String mail) {
		super();
		this.customerId = customerId;
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
		this.loanterm = loanterm;
		this.mail = mail;
	}
	public CustomerDetails() {
		super();
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	  
	  
}
