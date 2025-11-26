package com.x1a02.pension.mapper;

import com.x1a02.pension.entity.ServiceOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ServiceOrderMapper {

    int insert(ServiceOrder serviceOrder);

    int update(ServiceOrder serviceOrder);

    int deleteById(Long id);

    ServiceOrder selectById(Long id);

    ServiceOrder selectByOrderNo(@Param("orderNo") String orderNo);

    List<ServiceOrder> selectByElderlyId(@Param("elderlyId") Long elderlyId);

    List<ServiceOrder> selectByStaffId(@Param("staffId") Long staffId);

    List<ServiceOrder> selectByStatus(@Param("status") Integer status);

    List<ServiceOrder> selectAll();
}
