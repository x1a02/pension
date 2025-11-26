package com.x1a02.pension.controller;

import com.x1a02.pension.common.Result;
import com.x1a02.pension.entity.HealthRecord;
import com.x1a02.pension.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/health")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @PostMapping("/add")
    public Result<Boolean> addHealthRecord(@RequestBody HealthRecord healthRecord) {
        boolean success = healthRecordService.addHealthRecord(healthRecord);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to add health record");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteHealthRecord(@PathVariable Long id) {
        boolean success = healthRecordService.deleteHealthRecord(id);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to delete health record");
    }

    @GetMapping("/get/{id}")
    public Result<HealthRecord> getHealthRecordById(@PathVariable Long id) {
        HealthRecord record = healthRecordService.getHealthRecordById(id);
        if (record != null) {
            return Result.success(record);
        }
        return Result.error("Health record not found");
    }

    @GetMapping("/list/{elderlyId}")
    public Result<List<HealthRecord>> getHealthRecordsByElderlyId(@PathVariable Long elderlyId) {
        List<HealthRecord> list = healthRecordService.getHealthRecordsByElderlyId(elderlyId);
        return Result.success(list);
    }

    @GetMapping("/range/{elderlyId}")
    public Result<List<HealthRecord>> getHealthRecordsByDateRange(
            @PathVariable Long elderlyId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<HealthRecord> list = healthRecordService.getHealthRecordsByDateRange(elderlyId, startDate, endDate);
        return Result.success(list);
    }

    @GetMapping("/latest/{elderlyId}")
    public Result<HealthRecord> getLatestHealthRecord(@PathVariable Long elderlyId) {
        HealthRecord record = healthRecordService.getLatestHealthRecord(elderlyId);
        if (record != null) {
            return Result.success(record);
        }
        return Result.error("No health record found");
    }
}
