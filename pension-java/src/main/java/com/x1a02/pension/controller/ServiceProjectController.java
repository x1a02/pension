package com.x1a02.pension.controller;

import com.x1a02.pension.common.PageResult;
import com.x1a02.pension.common.Result;
import com.x1a02.pension.entity.ServiceProject;
import com.x1a02.pension.service.ServiceProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceProjectController {

    @Autowired
    private ServiceProjectService serviceProjectService;

    @PostMapping("/add")
    public Result<Boolean> addServiceProject(@RequestBody ServiceProject serviceProject) {
        boolean success = serviceProjectService.addServiceProject(serviceProject);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to add service project");
    }

    @PutMapping("/update")
    public Result<Boolean> updateServiceProject(@RequestBody ServiceProject serviceProject) {
        boolean success = serviceProjectService.updateServiceProject(serviceProject);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to update service project");
    }

    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteServiceProject(@PathVariable Long id) {
        boolean success = serviceProjectService.deleteServiceProject(id);
        if (success) {
            return Result.success(true);
        }
        return Result.error("Failed to delete service project");
    }

    @GetMapping("/get/{id}")
    public Result<ServiceProject> getServiceProjectById(@PathVariable Long id) {
        ServiceProject project = serviceProjectService.getServiceProjectById(id);
        if (project != null) {
            return Result.success(project);
        }
        return Result.error("Service project not found");
    }

    @GetMapping("/list")
    public Result<List<ServiceProject>> getAllServiceProjects() {
        List<ServiceProject> list = serviceProjectService.getAllServiceProjects();
        return Result.success(list);
    }

    @GetMapping("/category/{categoryId}")
    public Result<List<ServiceProject>> getServiceProjectsByCategoryId(@PathVariable Long categoryId) {
        List<ServiceProject> list = serviceProjectService.getServiceProjectsByCategoryId(categoryId);
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<PageResult<ServiceProject>> getServiceProjectsByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        PageResult<ServiceProject> pageResult = serviceProjectService.getServiceProjectsByPage(pageNum, pageSize);
        return Result.success(pageResult);
    }
}
