package com.example.e_commerce_informatiques.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_commerce_informatiques.model.Customers;
import com.example.e_commerce_informatiques.service.CustomersService;

@RestController
public class CustomersController {

    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping("/customers/newCustomers")
    public boolean createCustomer(@RequestBody Customers customer) throws SQLException {
        return customersService.createCustomer(customer);
    }

    @GetMapping("/customers/{customer_Id}")
    public Customers getCustomerById(@PathVariable int customer_Id) throws SQLException {
        return customersService.getCustomerById(customer_Id);
    }

    @PutMapping("/customers/{customer_Id}/update")
    public boolean updateCustomer(@PathVariable int customer_Id, @RequestBody Customers updatedCustomer) throws SQLException {
        return customersService.updateCustomer(
                customer_Id,
                updatedCustomer.getFirst_name(),
                updatedCustomer.getLast_name(),
                updatedCustomer.getEmail(),
                updatedCustomer.getShipping_address(),
                updatedCustomer.getPhone_number()
        );
    }

    @DeleteMapping("/customers/delete/{customer_Id}")
    public void deleteCustomer(@PathVariable int customer_Id) throws SQLException {
        customersService.deleteCustomer(customer_Id);
    }

    @GetMapping("/customers")
    public List<Customers> getAllCustomers() throws SQLException {
        return customersService.getAllCustomers();
    }
}
