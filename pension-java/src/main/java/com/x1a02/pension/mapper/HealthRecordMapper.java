package com.x1a02.pension.mapper;

import com.x1a02.pension.entity.HealthRecord;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

public interface HealthRecordMapper {

    int insert(HealthRecord healthRecord);

    int deleteById(Long id);

    HealthRecord selectById(Long id);

    List<HealthRecord> selectByElderlyId(@Param("elderlyId") Long elderlyId);

    List<HealthRecord> selectByElderlyIdAndDateRange(@Param("elderlyId") Long elderlyId,
                                                      @Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate);

    HealthRecord selectLatestByElderlyId(@Param("elderlyId") Long elderlyId);
}
