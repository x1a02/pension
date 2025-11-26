package com.x1a02.pension.service.impl;

import com.x1a02.pension.common.PageResult;
import com.x1a02.pension.entity.ServiceProject;
import com.x1a02.pension.mapper.ServiceProjectMapper;
import com.x1a02.pension.service.ServiceProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProjectServiceImpl implements ServiceProjectService {

    @Autowired
    private ServiceProjectMapper serviceProjectMapper;

    public boolean addServiceProject(ServiceProject serviceProject) {
        if (serviceProject.getStatus() == null) {
            serviceProject.setStatus(1);
        }
        int rows = serviceProjectMapper.insert(serviceProject);
        return rows > 0;
    }

    public boolean updateServiceProject(ServiceProject serviceProject) {
        int rows = serviceProjectMapper.update(serviceProject);
        return rows > 0;
    }

    public boolean deleteServiceProject(Long id) {
        int rows = serviceProjectMapper.deleteById(id);
        return rows > 0;
    }

    public ServiceProject getServiceProjectById(Long id) {
        return serviceProjectMapper.selectById(id);
    }

    public List<ServiceProject> getAllServiceProjects() {
        return serviceProjectMapper.selectAll();
    }

    public List<ServiceProject> getServiceProjectsByCategoryId(Long categoryId) {
        return serviceProjectMapper.selectByCategoryId(categoryId);
    }

    public PageResult<ServiceProject> getServiceProjectsByPage(int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<ServiceProject> records = serviceProjectMapper.selectByPage(offset, pageSize);
        Long total = serviceProjectMapper.countAll();
        return new PageResult<ServiceProject>(total, records);
    }
}
