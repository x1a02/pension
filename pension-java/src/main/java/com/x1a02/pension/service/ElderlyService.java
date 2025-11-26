package com.x1a02.pension.service;

import com.x1a02.pension.common.PageResult;
import com.x1a02.pension.entity.Elderly;
import java.util.List;

public interface ElderlyService {

    boolean addElderly(Elderly elderly);

    boolean updateElderly(Elderly elderly);

    boolean deleteElderly(Long id);

    Elderly getElderlyById(Long id);

    List<Elderly> getAllElderly();

    PageResult<Elderly> getElderlyByPage(int pageNum, int pageSize);

    List<Elderly> searchElderlyByName(String name);
}
