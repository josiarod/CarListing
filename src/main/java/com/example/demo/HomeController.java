package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryConfig cloudc;


    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("cars", carRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("/addCategory")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "categoryform";
    }

    @PostMapping("/processCategory")
    public String processCategory(@ModelAttribute Category category){
       categoryRepository.save(category);
       return "redirect:/";
    }


    @GetMapping("/addCar")
    public String addCar(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", new Car());
        return "carform";
    }

//    @PostMapping("/processCar")
//    public String processCar(@ModelAttribute Car car) {
//        carRepository.save(car);
//        return "redirect:/";
//    }









    @PostMapping("/processCar")
    public String processCar(@ModelAttribute Car car,
                              @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            carRepository.save(car);
            return "redirect:/";

        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            car.setPicture(uploadResult.get("url").toString());
            carRepository.save(car);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/processCar";
        }
        carRepository.save(car);
        return "redirect:/";
    }














    @RequestMapping("/detail/{id}")
    public String showCar(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("car", carRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCar(@PathVariable("id") long id,
                             Model model   ){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("car", carRepository.findById(id).get());
        return "carform";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") long id){
        carRepository.deleteById(id);
        return "redirect:/";
    }




    @RequestMapping("/categorydetail/{id}")
    public String showCategory(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "showcategory";
    }

}