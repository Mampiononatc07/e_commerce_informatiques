package com.example.e_commerce_informatiques.model;

import java.sql.Date;

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

public class Orders {
    private int order_id;
    private Date order_date;
    private float total_amount;
    private char order_status;
}
