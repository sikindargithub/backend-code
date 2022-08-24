
package com.acheron.dataexport.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {
    @Id
    @GeneratedValue(generator = "project_gen", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "project_gen", sequenceName = "seq_gen", initialValue = 10340, allocationSize = 1)
    private Integer projectId;
    private String projectName;
    private String projectStatus;
    private double costInDollars;
    private String managerName;
    private String clientName;
    private int noOfResources;
    private LocalDate creationDate;
    private LocalDate estimatedEndDate;
    private int durationInMonths;

}