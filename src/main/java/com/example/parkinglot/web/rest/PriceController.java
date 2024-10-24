package com.example.parkinglot.web.rest;


import com.example.parkinglot.entity.Price;
import com.example.parkinglot.repo.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
@CrossOrigin(origins = "http://localhost:4200")
public class PriceController {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @GetMapping
    public List<Price> getAllPrices() {

        return priceRepository.findAll();
    }

//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        Price savedPrice = priceRepository.save(price);
        return new ResponseEntity<>(savedPrice, HttpStatus.CREATED);
    }

}





