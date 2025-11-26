package com.x1a02.pension.mapper;

import com.x1a02.pension.entity.Activity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ActivityMapper {

    int insert(Activity activity);

    int update(Activity activity);

    int deleteById(Long id);

    Activity selectById(Long id);

    List<Activity> selectAll();

    List<Activity> selectByStatus(@Param("status") Integer status);

    List<Activity> selectUpcoming();

    int updateParticipantCount(@Param("id") Long id, @Param("increment") int increment);
}
