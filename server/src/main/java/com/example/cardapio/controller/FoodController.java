package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.repositories.FoodRepository;
import com.example.cardapio.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("food")
public class FoodController {

    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private FoodRepository repository;

    @Autowired
    private FoodService service;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Food> findAll(){
        return service.findAll();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Food findById(@PathVariable(value = "id") Long id){
        return service.findById(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Food add(@RequestBody Food food){
        return service.add(food);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Food update(@RequestBody Food food){
        return service.update(food);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
