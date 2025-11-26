package com.x1a02.pension.service.impl;

import com.x1a02.pension.entity.Activity;
import com.x1a02.pension.mapper.ActivityMapper;
import com.x1a02.pension.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    public boolean addActivity(Activity activity) {
        if (activity.getStatus() == null) {
            activity.setStatus(1);
        }
        if (activity.getCurrentParticipants() == null) {
            activity.setCurrentParticipants(0);
        }
        int rows = activityMapper.insert(activity);
        return rows > 0;
    }

    public boolean updateActivity(Activity activity) {
        int rows = activityMapper.update(activity);
        return rows > 0;
    }

    public boolean deleteActivity(Long id) {
        int rows = activityMapper.deleteById(id);
        return rows > 0;
    }

    public Activity getActivityById(Long id) {
        return activityMapper.selectById(id);
    }

    public List<Activity> getAllActivities() {
        return activityMapper.selectAll();
    }

    public List<Activity> getUpcomingActivities() {
        return activityMapper.selectUpcoming();
    }

    public boolean registerActivity(Long activityId, Long elderlyId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity != null && activity.getCurrentParticipants() < activity.getMaxParticipants()) {
            int rows = activityMapper.updateParticipantCount(activityId, 1);
            return rows > 0;
        }
        return false;
    }

    public boolean cancelRegistration(Long activityId, Long elderlyId) {
        int rows = activityMapper.updateParticipantCount(activityId, -1);
        return rows > 0;
    }

    public boolean checkIn(Long activityId, Long elderlyId) {
        return true;
    }
}
