package com.x1a02.pension.service.impl;

import com.x1a02.pension.entity.ServiceOrder;
import com.x1a02.pension.mapper.ServiceOrderMapper;
import com.x1a02.pension.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    public boolean createServiceOrder(ServiceOrder serviceOrder) {
        if (serviceOrder.getOrderNo() == null || serviceOrder.getOrderNo().isEmpty()) {
            serviceOrder.setOrderNo(generateOrderNo());
        }
        if (serviceOrder.getOrderStatus() == null) {
            serviceOrder.setOrderStatus(1);
        }
        int rows = serviceOrderMapper.insert(serviceOrder);
        return rows > 0;
    }

    public boolean updateServiceOrder(ServiceOrder serviceOrder) {
        int rows = serviceOrderMapper.update(serviceOrder);
        return rows > 0;
    }

    public boolean assignStaff(Long orderId, Long staffId) {
        ServiceOrder order = serviceOrderMapper.selectById(orderId);
        if (order != null) {
            order.setStaffId(staffId);
            order.setOrderStatus(2);
            int rows = serviceOrderMapper.update(order);
            return rows > 0;
        }
        return false;
    }

    public boolean updateOrderStatus(Long orderId, Integer status) {
        ServiceOrder order = serviceOrderMapper.selectById(orderId);
        if (order != null) {
            order.setOrderStatus(status);
            int rows = serviceOrderMapper.update(order);
            return rows > 0;
        }
        return false;
    }

    public boolean completeOrder(Long orderId, String completionNotes) {
        ServiceOrder order = serviceOrderMapper.selectById(orderId);
        if (order != null) {
            order.setOrderStatus(4);
            order.setCompletionTime(new Date());
            order.setCompletionNotes(completionNotes);
            int rows = serviceOrderMapper.update(order);
            return rows > 0;
        }
        return false;
    }

    public boolean rateOrder(Long orderId, Integer rating, String feedback) {
        ServiceOrder order = serviceOrderMapper.selectById(orderId);
        if (order != null) {
            order.setRating(rating);
            order.setFeedback(feedback);
            int rows = serviceOrderMapper.update(order);
            return rows > 0;
        }
        return false;
    }

    public ServiceOrder getServiceOrderById(Long id) {
        return serviceOrderMapper.selectById(id);
    }

    public ServiceOrder getServiceOrderByOrderNo(String orderNo) {
        return serviceOrderMapper.selectByOrderNo(orderNo);
    }

    public List<ServiceOrder> getServiceOrdersByElderlyId(Long elderlyId) {
        return serviceOrderMapper.selectByElderlyId(elderlyId);
    }

    public List<ServiceOrder> getServiceOrdersByStaffId(Long staffId) {
        return serviceOrderMapper.selectByStaffId(staffId);
    }

    public List<ServiceOrder> getPendingOrders() {
        return serviceOrderMapper.selectByStatus(1);
    }

    private String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        int random = (int) (Math.random() * 10000);
        return "SO" + timestamp + String.format("%04d", random);
    }
}
