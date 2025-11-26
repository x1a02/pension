package com.x1a02.pension.controller;

import com.x1a02.pension.common.Result;
import com.x1a02.pension.entity.ServiceOrder;
import com.x1a02.pension.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @PostMapping("/create")
    public Result<Boolean> createServiceOrder(@RequestBody ServiceOrder serviceOrder) {
        boolean success = serviceOrderService.createServiceOrder(serviceOrder);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to create service order");
    }

    @PutMapping("/update")
    public Result<Boolean> updateServiceOrder(@RequestBody ServiceOrder serviceOrder) {
        boolean success = serviceOrderService.updateServiceOrder(serviceOrder);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to update service order");
    }

    @PutMapping("/assign")
    public Result<Boolean> assignStaff(@RequestParam Long orderId, @RequestParam Long staffId) {
        boolean success = serviceOrderService.assignStaff(orderId, staffId);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to assign staff");
    }

    @PutMapping("/status")
    public Result<Boolean> updateOrderStatus(@RequestParam Long orderId, @RequestParam Integer status) {
        boolean success = serviceOrderService.updateOrderStatus(orderId, status);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to update order status");
    }

    @PutMapping("/complete")
    public Result<Boolean> completeOrder(@RequestParam Long orderId, @RequestParam String completionNotes) {
        boolean success = serviceOrderService.completeOrder(orderId, completionNotes);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to complete order");
    }

    @PutMapping("/rate")
    public Result<Boolean> rateOrder(@RequestParam Long orderId, @RequestParam Integer rating, @RequestParam String feedback) {
        boolean success = serviceOrderService.rateOrder(orderId, rating, feedback);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to rate order");
    }

    @GetMapping("/get/{id}")
    public Result<ServiceOrder> getServiceOrderById(@PathVariable Long id) {
        ServiceOrder order = serviceOrderService.getServiceOrderById(id);
        if (order != null) {
            return Result.success(order);
        }
        return Result.error("Service order not found");
    }

    @GetMapping("/elderly/{elderlyId}")
    public Result<List<ServiceOrder>> getServiceOrdersByElderlyId(@PathVariable Long elderlyId) {
        List<ServiceOrder> list = serviceOrderService.getServiceOrdersByElderlyId(elderlyId);
        return Result.success(list);
    }

    @GetMapping("/staff/{staffId}")
    public Result<List<ServiceOrder>> getServiceOrdersByStaffId(@PathVariable Long staffId) {
        List<ServiceOrder> list = serviceOrderService.getServiceOrdersByStaffId(staffId);
        return Result.success(list);
    }

    @GetMapping("/pending")
    public Result<List<ServiceOrder>> getPendingOrders() {
        List<ServiceOrder> list = serviceOrderService.getPendingOrders();
        return Result.success(list);
    }
}
