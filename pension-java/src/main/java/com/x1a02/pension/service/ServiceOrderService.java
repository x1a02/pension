package com.x1a02.pension.service;

import com.x1a02.pension.entity.ServiceOrder;
import java.util.List;

public interface ServiceOrderService {

    boolean createServiceOrder(ServiceOrder serviceOrder);

    boolean updateServiceOrder(ServiceOrder serviceOrder);

    boolean assignStaff(Long orderId, Long staffId);

    boolean updateOrderStatus(Long orderId, Integer status);

    boolean completeOrder(Long orderId, String completionNotes);

    boolean rateOrder(Long orderId, Integer rating, String feedback);

    ServiceOrder getServiceOrderById(Long id);

    ServiceOrder getServiceOrderByOrderNo(String orderNo);

    List<ServiceOrder> getServiceOrdersByElderlyId(Long elderlyId);

    List<ServiceOrder> getServiceOrdersByStaffId(Long staffId);

    List<ServiceOrder> getPendingOrders();
}
