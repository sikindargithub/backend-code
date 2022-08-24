
package com.acheron.dataexport.controller;

import com.acheron.dataexport.model.Project;
import com.acheron.dataexport.repository.IProjectRepository;
import com.acheron.dataexport.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
@RestController
@RequestMapping("/project-api")
@CrossOrigin("http://localhost:4200")
public class ProjectController {
    private IProjectService iProjectService;
    @Autowired
    public void setiProjectService(IProjectService iProjectService) {
        this.iProjectService = iProjectService;
    }

    IProjectRepository iProjectRepository;

    @Autowired
    public void setiProjectRepository(IProjectRepository iProjectRepository) {
        this.iProjectRepository = iProjectRepository;
    }

    /**
     *
     * @param project the instance of Project class
     * @return the status of the transaction
     */
    @PostMapping("/projects")
    public ResponseEntity<Void> addProject(@RequestBody Project project) {
        iProjectService.addProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     *
     * @param project the instance of Project class
     * @return the status of the transaction
     */
    @PutMapping("/projects")
    public ResponseEntity<Void> updateProject(@RequestBody Project project) {
        iProjectService.updateProject(project);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     *
     * @param projectId the project id of the project
     * @return the status of transaction along with headers
     */
    @GetMapping("/projects/projectId/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable("projectId") int projectId) {
        iProjectService.deleteProject(projectId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("desc", "deleting one project by project id " + projectId);
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).build();
    }

    /**
     *
     * @return list of all projects along with header and status
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects = iProjectService.getAll();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("desc", "getting all projects");
        return ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(projects);

    }

    /**
     *
     * @param keyword the word by which user wants to search the DB
     * @return the list of all projects after getting filtered by keyword
     */
    @GetMapping("/filterProjects")
    public ResponseEntity<Map<String, Object>> getAllFilteredProjects(@RequestParam(required = false) String keyword) {

        try{
            List<Project> projects = iProjectService.getProjects(keyword);
            int count =0;
            for(Project project:projects){
                count += 1;
            }
            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("totalItems",count);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("desc", "getting all projects");
            // returning the response along with status
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param keyword the word by which user wants to search the DB
     * @param sortBy the column name on which user wants to sort the records
     * @param sortOrder the integer value based on which sorting order happens
     * @return the filtered and sorted projects in the form of response
     */
    @GetMapping("/filteredAndSortedProjects")
    public ResponseEntity<Map<String, Object>> getAllFilteredAndSortedProjects(@RequestParam(required = false) String keyword,@RequestParam(required = false) String sortBy,@RequestParam(required = false) Integer sortOrder){

        List<Project> projects = null;
        // assigning sort order to the project list based on the value of sortOrder
        try{
            if(sortOrder == 1 ){
                projects = iProjectService.getProjectsBySort(keyword,Sort.by(Sort.Direction.ASC, sortBy));

            }
            else if (sortOrder == 2){
                projects = iProjectService.getProjectsBySort(keyword,Sort.by(Sort.Direction.DESC, sortBy));

            }
            int count =0;
            // iterating the projects to count the no of projects
            for(Project project:projects){
                count += 1;
            }
            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("totalItems",count);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("desc", "getting all projects");
            // returning the response along with status
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
