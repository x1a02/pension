package com.x1a02.pension.mapper;

import com.x1a02.pension.entity.ServiceProject;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ServiceProjectMapper {

    int insert(ServiceProject serviceProject);

    int update(ServiceProject serviceProject);

    int deleteById(Long id);

    ServiceProject selectById(Long id);

    List<ServiceProject> selectAll();

    List<ServiceProject> selectByCategoryId(@Param("categoryId") Long categoryId);

    List<ServiceProject> selectByPage(@Param("offset") int offset, @Param("limit") int limit);

    Long countAll();
}
