package com.acheron.dataexport.service;

import com.acheron.dataexport.exception.ProjectNotFoundException;
import com.acheron.dataexport.model.Project;
import org.springframework.data.domain.Sort;
import java.util.List;

/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
public interface IProjectService {

    // CRUD queries
    void addProject(Project project);
    void updateProject(Project project);
    void deleteProject(int projectId);
    List<Project> getAll();

    List<Project> getProjects(String keyword) throws ProjectNotFoundException;
    List<Project> getProjectsBySort(String keyword, Sort sort) throws ProjectNotFoundException;

}
