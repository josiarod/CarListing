package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
   CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("employees", employeeRepository.findAll());
        model.addAttribute("companies", companyRepository.findAll());
        return "index";
    }

    @GetMapping("/addCompany")
    public String addCompany(Model model){
        model.addAttribute("company", new Company());
        return "companyform";
    }

    @PostMapping("/processCompany")
    public String processCompany(@ModelAttribute Company company){
       companyRepository.save(company);
       return "redirect:/";
    }


    @GetMapping("/addEmployee")
    public String addEmployee(Model model) {
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("employee", new Employee());
        return "employeeform";
    }

    @PostMapping("/processEmployee")
    public String processEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/";
    }





}