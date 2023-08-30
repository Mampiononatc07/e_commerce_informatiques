package com.example.e_commerce_informatiques.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

public class Products {
    private int product_id;
    private String product_name;
    private String description;
    private Float price;
    private String category;
}
