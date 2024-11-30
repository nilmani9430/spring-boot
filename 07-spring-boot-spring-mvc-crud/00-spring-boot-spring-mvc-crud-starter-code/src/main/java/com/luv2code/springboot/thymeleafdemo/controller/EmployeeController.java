package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public  EmployeeController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model theModel){
        // get the employees from db
        List<Employee> theEmployees = employeeService.findAll();

        // add to the spring model
        theModel.addAttribute("employees",theEmployees);

        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){
        Employee theEmployee = new Employee();
        theModel.addAttribute("employee",theEmployee);
         return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee")Employee theEmployee){
        // save the employee
        employeeService.save(theEmployee);

        // use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String ShowFormForUpdate(@RequestParam("employeeId")int theId,Model theModel){
        Employee theEmployee = employeeService.findById(theId);
        theModel.addAttribute("employee",theEmployee);
        return "employees/employee-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId")int theId){
        // delete the employee
        employeeService.deleteById(theId);

        return "redirect:/employees/list";
    }

}
