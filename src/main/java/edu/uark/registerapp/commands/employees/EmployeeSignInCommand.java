package edu.uark.registerapp.commands.employees;

import java.util.Arrays;
import java.util.Optional;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;

import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

//define class
@Service
public class EmployeeSignInCommand implements ResultCommandInterface<Employee>{
    //Properties
    //employee signIn object
    private EmployeeSignIn employeeSignIn;
    public EmployeeSignIn getEmployeeSignIn(){
        return this.employeeSignIn;
    }
    public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn){
        this.employeeSignIn = employeeSignIn;
        return this;
    }


    //current sessionId
    private String sessionId;
    public String getSessionID(){
        return this.sessionId;
    }
    public EmployeeSignInCommand setSessionId(final String sessionId){
        this.sessionId = sessionId;
        return this;
    }

    //Functionality
    //validate the incoming Employee request object
    @Override
    public Employee execute(){
        this.validateProperties();
        return new Employee(this.SignInEmployee());
    }

    //validate the incoming Employee request object
    private void validateProperties(){
        //error if employeeID is blank
        if (StringUtils.isBlank(this.employeeSignIn.getEmployeeId())){
            throw new UnprocessableEntityException("employee ID");
        }
        //error if employee ID is not a number
        try{
            Integer.parseInt(this.employeeSignIn.getEmployeeId());
        } catch(final NumberFormatException e){
            throw new UnprocessableEntityException("employee ID");
        }
        //error if password is blank
        if(StringUtils.isBlank(this.employeeSignIn.getPassword())){
            throw new UnprocessableEntityException("password");
        }

    }
    //query the employee by employee ID
    @Transactional
    EmployeeEntity SignInEmployee(){
        //use the EmployeeRepository.queryByEmployeeId()
        final Optional<EmployeeEntity> employeeEntity = this.employeeRepository.findByEmployeeId(
                                                            Integer.parseInt(this.employeeSignIn.getEmployeeId()));
        //verify the employee exists
        if(!employeeEntity.isPresent() || !Arrays.equals(
                                            employeeEntity.get().getPassword(),
                                            EmployeeHelper.hashPassword(this.employeeSignIn.getPassword()))){
            throw new UnauthorizedException();
        }
        //query activeUser table for a record with the employee ID
        final Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findByEmployeeId(
                                                                employeeEntity.get().getId());
        //is user exists 
        if(activeUserEntity.isPresent()){
            //update entity's sessionKey property with the current session key
            //use the ActiveUserRepository.save()
            this.activeUserRepository.save(activeUserEntity.get().setSessionKey(this.sessionId));
        }
        else{
            //create new active user
            //set the session key
            //set the necessary employee details
            //use the ActiveUserRepository.save()
            this.activeUserRepository.save(
                new ActiveUserEntity()).setSessionKey(this.sessionId).setEmployeeId(
                    employeeEntity.get().getId()).setClassification(employeeEntity.get().getClassification()).setName(
                        employeeEntity.get().getFirstName().concat(" ").concat(employeeEntity.get().getLastName()));

        }
        return employeeEntity.get();
        
    }

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired 
    private ActiveUserRepository activeUserRepository;
}