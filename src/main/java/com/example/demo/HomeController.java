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

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listItems(Model model){
        model.addAttribute("todos", repository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String todoForm(Model model){
        model.addAttribute("todo", new Todo());
        return "form";
    }



    @PostMapping("/add")
    public String processTood(@ModelAttribute Todo todo,
                              @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/add";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            todo.setPicture(uploadResult.get("url").toString());
            repository.save(todo);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/add";
        }
        return "redirect:/";
    }



    @PostMapping("/process")
    public String processForm(@Valid Todo todo, BindingResult result){
        if (result.hasErrors()){
            return "form";
        }
        repository.save(todo);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("todo", repository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateTodo(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("todo", repository.findById(id).get());
        return "form";
    }

    @RequestMapping("/delete/{id}")
    public String delTodo(@PathVariable("id") long id){
        repository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

}