package edu.uark.registerapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController{
    @RequestMapping(method = RequestMethod.GET)
    public ViewAndDocument ViewAndDocument(@RequestParam final Map<String, String> queryParameters){
        //Functionality
        //if employee exists
        try{
          this.activeEmployeeExistsQuery.execit();
        }catch(NotFoundException e){
          //if no employee exists
          return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.EMPLOYEE_DETAIL.getRoute()));
        }
        ViewAndDocument viewAndDocument = this.setErrorMessageFromQueryString(
          new ModelAndView(ViewName.SIGN_IN.getViewName()), queryParameters);
        if(queryParameters.containsKey(QueryParametersNames.EMPLOYEE_ID.getValue())){
          viewAndDocument.addObject(ViewModelNames.EMPLOYEE_ID.getValue(), queryParameters.get(
            QueryParametersNames.EMPLOYEE_ID.getValue()));
        }
    }
    return viewAndDocument;
  }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ViewAndDocument performSignIn(EmployeeSignIn employeeSignIn, HttpServletRequest request){
      //Verify Employee Credentials
      try{
        //create record
        this.emloyeeSignInCommand.setSessionId(request.getSession.getId())
          .setEmployeeSignIn(employeeSignIn).execute();
      }catch (Exception e){
        ViewAndDocument viewAndDocument = new ViewAndDocument(ViewNames.SIGN_IN.getViewName());
        viewAndDocument.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), e.getMessage());
        viewAndDocument.addObject(ViewModelNames.EMPLOYEE_ID.getValue(), employeeSignIn.getEmployeeId());
        return viewAndDocument;
      }
      //redirect to Main Menu
      return new ViewAndDocument(REDIRECT_PREPEND.concat(ViewName.MAIN_MENU.getRoute()));
    }

@Autowired
private EmployeeSignInCommand EmployeeSignInCommand;

@Autowired
private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
