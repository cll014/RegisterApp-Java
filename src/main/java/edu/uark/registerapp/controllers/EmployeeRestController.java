package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uark.registerapp.models.entities.ActiveUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeCreateCommand;
import edu.uark.registerapp.commands.employees.EmployeeUpdateCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody
    ApiResponse createEmployee(@RequestBody Employee employee, HttpServletRequest request, HttpServletResponse response) {
        final boolean employeeExists = this.activeUserExists(); // Active Employee Exists
        boolean isInit = false;
        ApiResponse createResponse;

        if (employeeExists) {
            createResponse = this.redirectUserNotElevated(request, response);
        }
        else {
            createResponse = new ApiResponse();
            isInit = true;
        }

        if (!createResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
            return createResponse;
        }
        else {
            Employee newEmployee = this.employeeCreateCommand.setApiEmployee(employee).setIsInitialEmployee(employeeExists).execute();
            if(isInit) {
                newEmployee.setRedirectUrl(ViewNames.SIGN_IN.getRoute().concat(
                        this.buildInitialQueryParameter(QueryParameterNames.EMPLOYEE_ID.getValue(), newEmployee.getEmployeeId())));
            }
            return newEmployee.setIsInitialEmployee(isInit);
        }
    }
    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
    public @ResponseBody ApiResponse updateEmployee(@PathVariable UUID employeeId, @RequestBody Employee employee, HttpServletResponse response, HttpServletRequest request) {
        ApiResponse createResponse = this.redirectUserNotElevated(request, response);

        if(!createResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
            return createResponse;
        }
        else {
            return this.employeeUpdateCommand.setEmployeeId(employeeId).setApiEmployee(employee).execute();
        }
    }

    private boolean activeUserExists() {
        try {
            this.activeEmployeeExistsQuery.execute();
            return true;
        } catch (final NotFoundException e) {
            return false;
        }

    }

    @Autowired
    private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

    @Autowired
    private EmployeeCreateCommand employeeCreateCommand;

    @Autowired
    private EmployeeUpdateCommand employeeUpdateCommand;
}