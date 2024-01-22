package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long id;

    private String street;
    private Long number;
    private String city;
    private String country;
    private String zipCode;
    private String complement;
    private String neighborhood;
    private String state;

}
