package com.example.e_commerce_informatiques.controller;

import org.springframework.web.bind.annotation.*;
import com.example.e_commerce_informatiques.model.Orders;
import com.example.e_commerce_informatiques.service.OrdersService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/orders/newOrders")
    public boolean createOrder(@RequestBody Orders order) throws SQLException {
        return ordersService.createOrder(order);
    }

    @GetMapping("/orders/{order_Id}")
    public Orders getOrderById(@PathVariable int order_Id) throws SQLException {
        return ordersService.getOrderById(order_Id);
    }

    @PutMapping("/orders/{order_Id}/update")
    public boolean updateOrder(@PathVariable int order_Id, @RequestBody Orders updatedOrder) throws SQLException {
        return ordersService.updateOrder(
            order_Id,
            updatedOrder.getOrder_date(),
            updatedOrder.getTotal_amount(),
            updatedOrder.getOrder_status()
        );
    }

    @DeleteMapping("/orders/delete/{order_Id}")
    public void deleteOrder(@PathVariable int order_Id) throws SQLException {
        ordersService.deleteOrder(order_Id);
    }

    @GetMapping("/orders")
    public List<Orders> getAllOrders() throws SQLException {
        return ordersService.getAllOrders();
    }
}
