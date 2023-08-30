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

public class Customers {
    private int customer_id;
    private String first_name;
    private String last_name;
    private String email;
    private String shipping_address;
    private String phone_number;
}
