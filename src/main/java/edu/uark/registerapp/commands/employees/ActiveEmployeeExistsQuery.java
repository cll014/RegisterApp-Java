//commands/employees package
package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowires;
import org.springframework.sterotype.Service;
import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repostitories.EmployeeRepository;

//define class
public class ActiveEmployeeExistsQuery implements VoidCommandInterface{
    
    @Override
    //Query if any active employees exist in the database
    public void execut(){
        //use the existing Employee repository.existsByIsActive() method
        if(!this.employeeRepository.existsByIsActive(true)){
            //throw a notFoundException
            throw new NotFoundException("Employee");
        }
    }
    @Autowired
    private EmployeeRepository employeeRepository;
    
}