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

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController{
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView start(
            @RequestParam final Map<String, String> queryParameters,
            final HttpServletRequest request
            ) {
        if (this.activeUserExists())

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
}
