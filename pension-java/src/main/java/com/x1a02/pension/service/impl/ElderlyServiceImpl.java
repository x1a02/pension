package com.x1a02.pension.service.impl;

import com.x1a02.pension.common.PageResult;
import com.x1a02.pension.entity.Elderly;
import com.x1a02.pension.mapper.ElderlyMapper;
import com.x1a02.pension.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElderlyServiceImpl implements ElderlyService {

    @Autowired
    private ElderlyMapper elderlyMapper;

    public boolean addElderly(Elderly elderly) {
        if (elderly.getStatus() == null) {
            elderly.setStatus(1);
        }
        int rows = elderlyMapper.insert(elderly);
        return rows > 0;
    }

    public boolean updateElderly(Elderly elderly) {
        int rows = elderlyMapper.update(elderly);
        return rows > 0;
    }

    public boolean deleteElderly(Long id) {
        int rows = elderlyMapper.deleteById(id);
        return rows > 0;
    }

    public Elderly getElderlyById(Long id) {
        return elderlyMapper.selectById(id);
    }

    public List<Elderly> getAllElderly() {
        return elderlyMapper.selectAll();
    }

    public PageResult<Elderly> getElderlyByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Elderly> records = elderlyMapper.selectByPage(offset, pageSize);
        Long total = elderlyMapper.countAll();
        return new PageResult<Elderly>(total, records);
    }

    public List<Elderly> searchElderlyByName(String name) {
        return elderlyMapper.selectByName(name);
    }
}
