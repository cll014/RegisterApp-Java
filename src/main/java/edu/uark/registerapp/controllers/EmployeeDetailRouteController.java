package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController{

    public ModelAndView start(
            @RequestParam final Map<String, String> queryParameters,
            final HttpServletRequest request

            ) {
        Optional<ActiveUserEntity> user = this.getCurrentUser(request);

        if (user.isPresent()) {     // checks if active user exists
            if (!this.activeUserExists() || isElevatedUser(user.get())) {   // Checks if Employee exists or if user is elevated
                return this.buildStartResponse(!activeUserExists, queryParameters);  // Employee Detail View
            }
        }
        else if (!user.isPresent())  // runs if no active user is present
        {
            return this.buildInvalidSessionResponse();
        }
        else
        {
            return this.buildInvalidSessionResponse();
        }

        return new ModelAndView(ViewModelNames.EMPLOYEE_TYPES.getValue());
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
    public ModelAndView startWithEmployee(
            @PathVariable final UUID employeeId,
            @RequestParam final Map<String, String> queryParameters,
            final HttpServletRequest request
    ) {
        // TODO
    return new ModelAndView(ViewModelNames.EMPLOYEE_TYPES.getValue());
    }

    private boolean activeUserExists() {
        // TODO: Helper method to determine if any active users Exist
        if(ActiveEmployeeExistsQuery.exists())
            return true;
        else
            return false;
    }

    private ModelAndView buildStartResponse(
            final boolean isInitialEmployee,
            final Map<String, String> queryParameters
    ) {

        return this.buildStartResponse(
                isInitialEmployee,
                (new UUID(0, 0)),
                queryParameters);
    }
}
