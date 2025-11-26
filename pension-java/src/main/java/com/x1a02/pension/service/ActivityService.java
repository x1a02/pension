package com.x1a02.pension.service;

import com.x1a02.pension.entity.Activity;
import java.util.List;

public interface ActivityService {

    boolean addActivity(Activity activity);

    boolean updateActivity(Activity activity);

    boolean deleteActivity(Long id);

    Activity getActivityById(Long id);

    List<Activity> getAllActivities();

    List<Activity> getUpcomingActivities();

    boolean registerActivity(Long activityId, Long elderlyId);

    boolean cancelRegistration(Long activityId, Long elderlyId);

    boolean checkIn(Long activityId, Long elderlyId);
}
