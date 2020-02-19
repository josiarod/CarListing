package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    FoodRepository repository;

    @Override
    public void run(String... strings) throws Exception{
        Food food;
        food = new Food("Pancakes with strawberries and whipped cream", "FluffyPancakes_hk0qjs.jpg", "Victor", "Fluffy Pancakes");
        repository.save(food);

        food = new Food(" This recipe is VERY forgiving, so you can add or remove ingredients according to your taste!", "spinach.jpg", "Reynaldo", "Spinach Quiche");
        repository.save(food);

        food = new Food("Roasted beets with balsamic vinegar dressing.", "RoastedBeetSalad.jpg", "Nora", "Roasted Beet Salad");
        repository.save(food);

    }

}
//String description, String pictureURL, @NotNull String user, @NotNull String foodName