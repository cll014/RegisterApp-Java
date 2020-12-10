package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

public class EmployeeSignIn {
	//getter for employee ID
	private String employeeId;
	public String getEmployeeId() {
		return this.employeeId;
	}
	//sets employee ID based on input
	public EmployeeSignIn setEmployeeId(final String employeeId) {
		this.employeeId = employeeId;
		return this;
	}
	
	//getter for password
	private String password;
	public String getPassword() {
		return this.password;
	}
	//sets password from user input
	public EmployeeSignIn setPassword(final String password) {
		this.password = password;
		return this;
	}
	//how the employee signs in and inputs will then be verified
	public EmployeeSignIn() {
		this.password = StringUtils.EMPTY;
		this.employeeId = StringUtils.EMPTY;
	}
}
