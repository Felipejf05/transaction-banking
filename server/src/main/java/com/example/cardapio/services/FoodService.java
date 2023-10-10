package com.example.cardapio.services;

import com.example.cardapio.food.Food;
import com.example.cardapio.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class FoodService {

    private Logger logger = Logger.getLogger(FoodService.class.getName());
    @Autowired
    private FoodRepository repository;

    public List<Food> findAll(){
        logger.info("Finding all Food!");

        return repository.findAll();
    }

    public Food findById(Long id) {

        logger.info("Finding one Food!");

        return repository.findById(id).orElseThrow();
    }

    public Food add(Food food){
        logger.info("Additing one Food!");
        return repository.save(food);
    }

    public Food update(Food food){
        logger.info("Updating one Food!");
       var entity =  repository.findById(food.getId()).orElseThrow();

        entity.setTitle(food.getTitle());
        entity.setImg(food.getImg());
        entity.setPrice(food.getPrice());

        return repository.save(food);
    }

    public void  delete (Long id){
        logger.info("Deleteting one Food!");

        var entity =  repository.findById(id).orElseThrow();
        repository.delete(entity);

    }

}

