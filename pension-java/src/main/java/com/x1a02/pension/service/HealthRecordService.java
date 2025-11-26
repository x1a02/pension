package com.x1a02.pension.service;

import com.x1a02.pension.entity.HealthRecord;
import java.util.Date;
import java.util.List;

public interface HealthRecordService {

    boolean addHealthRecord(HealthRecord healthRecord);

    boolean deleteHealthRecord(Long id);

    HealthRecord getHealthRecordById(Long id);

    List<HealthRecord> getHealthRecordsByElderlyId(Long elderlyId);

    List<HealthRecord> getHealthRecordsByDateRange(Long elderlyId, Date startDate, Date endDate);

    HealthRecord getLatestHealthRecord(Long elderlyId);
}
