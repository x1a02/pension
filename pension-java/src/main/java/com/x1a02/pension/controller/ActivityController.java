package com.x1a02.pension.controller;

import com.x1a02.pension.common.Result;
import com.x1a02.pension.entity.Activity;
import com.x1a02.pension.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/add")
    public Result<Boolean> addActivity(@RequestBody Activity activity) {
        boolean success = activityService.addActivity(activity);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to add activity");
    }

    @PutMapping("/update")
    public Result<Boolean> updateActivity(@RequestBody Activity activity) {
        boolean success = activityService.updateActivity(activity);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to update activity");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteActivity(@PathVariable Long id) {
        boolean success = activityService.deleteActivity(id);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to delete activity");
    }

    @GetMapping("/get/{id}")
    public Result<Activity> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        if (activity != null) {
            return Result.success(activity);
        }
        return Result.error("Activity not found");
    }

    @GetMapping("/list")
    public Result<List<Activity>> getAllActivities() {
        List<Activity> list = activityService.getAllActivities();
        return Result.success(list);
    }

    @GetMapping("/upcoming")
    public Result<List<Activity>> getUpcomingActivities() {
        List<Activity> list = activityService.getUpcomingActivities();
        return Result.success(list);
    }

    @PostMapping("/register")
    public Result<Boolean> registerActivity(@RequestParam Long activityId, @RequestParam Long elderlyId) {
        boolean success = activityService.registerActivity(activityId, elderlyId);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to register for activity");
    }

    @PostMapping("/cancel")
    public Result<Boolean> cancelRegistration(@RequestParam Long activityId, @RequestParam Long elderlyId) {
        boolean success = activityService.cancelRegistration(activityId, elderlyId);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to cancel registration");
    }

    @PostMapping("/checkin")
    public Result<Boolean> checkIn(@RequestParam Long activityId, @RequestParam Long elderlyId) {
        boolean success = activityService.checkIn(activityId, elderlyId);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to check in");
    }
}
