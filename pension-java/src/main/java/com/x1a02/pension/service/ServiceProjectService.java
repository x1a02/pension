package com.x1a02.pension.service;

import com.x1a02.pension.common.PageResult;
import com.x1a02.pension.entity.ServiceProject;
import java.util.List;

public interface ServiceProjectService {

    boolean addServiceProject(ServiceProject serviceProject);

    boolean updateServiceProject(ServiceProject serviceProject);

    boolean deleteServiceProject(Long id);

    ServiceProject getServiceProjectById(Long id);

    List<ServiceProject> getAllServiceProjects();

    List<ServiceProject> getServiceProjectsByCategoryId(Long categoryId);

    PageResult<ServiceProject> getServiceProjectsByPage(int pageNum, int pageSize);
}
