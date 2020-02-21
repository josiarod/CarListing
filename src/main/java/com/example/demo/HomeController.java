package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;


@Controller
public class HomeController {
    @Autowired
    Repository repository;

    @RequestMapping("/")
    public String listItems(Model model){
        model.addAttribute("jobs", repository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String jobForm(Model model){
        model.addAttribute("job", new Job());
        return "form";
    }






    @PostMapping("/process")
    public String processForm(@Valid Job job, BindingResult result){
        if (result.hasErrors()){
            return "form";
        }
        repository.save(job);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("job", repository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("job", repository.findById(id).get());
        return "form";
    }

    @RequestMapping("/delete/{id}")
    public String delJob(@PathVariable("id") long id){
        repository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

}