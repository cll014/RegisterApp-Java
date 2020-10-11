package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

//define class
public class EmployeeSignIn{
    //Properties
    private String employeeId;
    private String password;

    //getters
    public String getEmployeeId(){
        return this.employeeId;
    }
    public String getPassword(){
        return this.password;
    }

    //setters
    public EmployeeSignIn setEmployeeId(final String employeeId){
        this.employeeId = employeeId;
        return this;
    }

    public EmployeeSignIn setPassword(final String password){
        this.password = password;
        return this;
    }

    public EmployeeSignIn(){
        this.password = StringUtils.EMPTY;
        this.employeeId = StringUtils.EMPTY;
    }
}
