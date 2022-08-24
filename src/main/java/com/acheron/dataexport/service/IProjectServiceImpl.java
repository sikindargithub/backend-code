
package com.acheron.dataexport.service;

import com.acheron.dataexport.exception.ProjectNotFoundException;
import com.acheron.dataexport.model.Project;
import com.acheron.dataexport.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
@Service
public class IProjectServiceImpl implements IProjectService{


    IProjectRepository iProjectRepository;
    @Autowired
    public void setiProjectRepository(IProjectRepository iProjectRepository) {
        this.iProjectRepository = iProjectRepository;
    }

    @Override
    public void addProject(Project project) {
     iProjectRepository.save(project);
    }

    @Override
    public void updateProject(Project project) {
        iProjectRepository.save(project);
    }

    @Override
    public void deleteProject(int projectId) {
        iProjectRepository.deleteById(projectId);
    }

    @Override
    public List<Project> getAll() {
        return iProjectRepository.findAll();
    }


    /**
     *
     * @param keyword the word by which user wants to search the DB
     * @return the list of all projects after getting filtered by keyword
     * @throws ProjectNotFoundException if the projects with the given keyword are not found
     */
    @Override
    public List<Project> getProjects(String keyword) {
        List<Project> projectList = iProjectRepository.findProjects(keyword);
        if(projectList.isEmpty())
            throw new ProjectNotFoundException("Projects of keyword "+keyword+" are not found");
        return projectList;
    }

    /**
     *
     * @param keyword the word by which user wants to search the DB
     * @param sort instance of Sort class which accepts sort order & sortBy as arguments
     * @return the list of all projects after getting filtered by keyword & sorted
     * @throws ProjectNotFoundException if the projects with the given keyword are not found
     */
    @Override
    public List<Project> getProjectsBySort(String keyword, Sort sort) {
        List<Project> projectList = iProjectRepository.findProjectsBySort(keyword,sort);
        if(projectList.isEmpty())
            throw new ProjectNotFoundException("Projects of keyword "+keyword+" are not found");
        return projectList;

    }


}
