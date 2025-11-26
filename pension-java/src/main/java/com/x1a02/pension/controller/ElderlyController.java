package com.x1a02.pension.controller;

import com.x1a02.pension.common.PageResult;
import com.x1a02.pension.common.Result;
import com.x1a02.pension.entity.Elderly;
import com.x1a02.pension.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elderly")
public class ElderlyController {

    @Autowired
    private ElderlyService elderlyService;

    @PostMapping("/add")
    public Result<Boolean> addElderly(@RequestBody Elderly elderly) {
        boolean success = elderlyService.addElderly(elderly);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to add elderly information");
    }

    @PutMapping("/update")
    public Result<Boolean> updateElderly(@RequestBody Elderly elderly) {
        boolean success = elderlyService.updateElderly(elderly);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to update elderly information");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteElderly(@PathVariable Long id) {
        boolean success = elderlyService.deleteElderly(id);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to delete elderly information");
    }

    @GetMapping("/get/{id}")
    public Result<Elderly> getElderlyById(@PathVariable Long id) {
        Elderly elderly = elderlyService.getElderlyById(id);
        if (elderly != null) {
            return Result.success(elderly);
        }
        return Result.error("Elderly not found");
    }

    @GetMapping("/list")
    public Result<List<Elderly>> getAllElderly() {
        List<Elderly> list = elderlyService.getAllElderly();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<PageResult<Elderly>> getElderlyByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageResult<Elderly> pageResult = elderlyService.getElderlyByPage(pageNum, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping("/search")
    public Result<List<Elderly>> searchElderlyByName(@RequestParam String name) {
        List<Elderly> list = elderlyService.searchElderlyByName(name);
        return Result.success(list);
    }
}
