package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class EmployeeUpdateCommand {
    public Employee execute(){
        this.validProperties();
        this.updateEmployeeEntity();
        return this.apiEmployee;
    }

    // Helper Methods
    private void validProperties(){
        if(StringUtils.isBlank(this.apiEmployee.getFirstName())){
            throw new UnprocessableEntityException("First Name");
        }
        if(StringUtils.isBlank(this.apiEmployee.getLastName())){
            throw new UnprocessableEntityException("Last Name");
        }
        if(EmployeeClassification.map(this.apiEmployee.getClassification()) == EmployeeClassification.NOT_DEFINED){
            throw new UnprocessableEntityException("Classification");
        }
    }

    private void updateEmployeeEntity(){
        Optional<EmployeeEntity> queriedEmployeeEntity = this.employeeRepository.findById(this.employeeId);
        if(!queriedEmployeeEntity.isPresent()){
            throw new NotFoundException("Employee"); // No record with the assoicated record ID exists in the database
        }

        this.apiEmployee = queriedEmployeeEntity.get().synchronize(this.apiEmployee); // Synchronize any incoming changes for UPDATE to the database.
        this.employeeRepository.save(queriedEmployeeEntity.get()); // Write, via an UPDATE, any changes to the database.
    }

    // Properties
    private UUID employeeId;
    public UUID getEmployeeId(){
        return this.employeeId;
    }
    public EmployeeUpdateCommand setEmployeeId(final UUID employeeId){
        this.employeeId = employeeId;
        return this;
    }

    private Employee apiEmployee;
    public Employee getApiEmployee(){
        return this.apiEmployee;
    }
    public EmployeeUpdateCommand setApiEmployee(final Employee apiEmployee){
        this.apiEmployee = apiEmployee;
        return this;
    }

    private EmployeeRepository employeeRepository;
}
