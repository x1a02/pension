package com.x1a02.pension.mapper;

import com.x1a02.pension.entity.Elderly;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ElderlyMapper {

    int insert(Elderly elderly);

    int update(Elderly elderly);

    int deleteById(Long id);

    Elderly selectById(Long id);

    List<Elderly> selectAll();

    List<Elderly> selectByPage(@Param("offset") int offset, @Param("limit") int limit);

    Long countAll();

    List<Elderly> selectByName(@Param("name") String name);

    Elderly selectByIdCard(@Param("idCard") String idCard);
}
