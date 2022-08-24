package com.acheron.dataexport.repository;

import com.acheron.dataexport.model.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
@Repository
public interface IProjectRepository extends JpaRepository<Project,Integer> {


    @Query("SELECT p FROM Project p WHERE CONCAT(p.projectName,'',p.projectStatus,'',p.costInDollars,'',p.managerName) LIKE %?1% ")
    List<Project> findProjects(String keyword);

    @Query("SELECT p FROM Project p WHERE CONCAT(p.projectName,'',p.projectStatus,'',p.costInDollars,'',p.managerName) LIKE %?1% ")
    List<Project> findProjectsBySort(String keyword, Sort sort);



}
