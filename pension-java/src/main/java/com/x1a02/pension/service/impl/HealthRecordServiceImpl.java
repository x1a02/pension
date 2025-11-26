package com.x1a02.pension.service.impl;

import com.x1a02.pension.entity.HealthRecord;
import com.x1a02.pension.mapper.HealthRecordMapper;
import com.x1a02.pension.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    public boolean addHealthRecord(HealthRecord healthRecord) {
        if (healthRecord.getRecordDate() == null) {
            healthRecord.setRecordDate(new Date());
        }
        if (healthRecord.getDataSource() == null) {
            healthRecord.setDataSource(1);
        }
        int rows = healthRecordMapper.insert(healthRecord);
        return rows > 0;
    }

    public boolean deleteHealthRecord(Long id) {
        int rows = healthRecordMapper.deleteById(id);
        return rows > 0;
    }

    public HealthRecord getHealthRecordById(Long id) {
        return healthRecordMapper.selectById(id);
    }

    public List<HealthRecord> getHealthRecordsByElderlyId(Long elderlyId) {
        return healthRecordMapper.selectByElderlyId(elderlyId);
    }

    public List<HealthRecord> getHealthRecordsByDateRange(Long elderlyId, Date startDate, Date endDate) {
        return healthRecordMapper.selectByElderlyIdAndDateRange(elderlyId, startDate, endDate);
    }

    public HealthRecord getLatestHealthRecord(Long elderlyId) {
        return healthRecordMapper.selectLatestByElderlyId(elderlyId);
    }
}
