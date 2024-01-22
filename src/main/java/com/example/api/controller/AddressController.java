package com.example.api.controller;

import com.example.api.entity.Address;
import com.example.api.request.AddressRequest;
import com.example.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/insert")
    public ResponseEntity insertAddress(
            @RequestParam Long clienteId,
            @RequestParam String cep,
            AddressRequest request) {
        addressService.insertAddress(clienteId, cep, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
