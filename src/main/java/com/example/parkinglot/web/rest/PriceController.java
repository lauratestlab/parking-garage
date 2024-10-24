package com.example.parkinglot.web.rest;


import com.example.parkinglot.entity.Price;
import com.example.parkinglot.repo.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.parkinglot.service.PriceService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    @Autowired
    private PriceService priceService;

    @GetMapping
    public List<Price> getAllPrices() {

        return priceRepository.findAll();
    }

    //Method to create new duration, new price
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        Price savedPrice = priceRepository.save(price);
        return new ResponseEntity<>(savedPrice, HttpStatus.CREATED);
    }

    // Method to update the price by duration (id) and the new price
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{duration}")
    public ResponseEntity<Price> updatePriceByDuration(
            @PathVariable("duration") int duration,
            @RequestParam("price") BigInteger newPrice) {
        Price updatedPrice = priceService.updatePriceByDurationAndValue(duration, newPrice);
        if (updatedPrice != null) {
            return ResponseEntity.ok(updatedPrice);
        }
        return ResponseEntity.notFound().build();
    }

    //Delete based on id
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable("id") int id) {
        if (!priceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        priceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}





