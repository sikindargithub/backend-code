
package com.acheron.dataexport.controller;

import com.acheron.dataexport.export.ProjectExcelExporter;
import com.acheron.dataexport.export.ProjectPdfExporter;
import com.acheron.dataexport.model.Project;
import com.acheron.dataexport.repository.IProjectRepository;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/export")
public class ExportController {

    private IProjectRepository iProjectRepository;
    @Autowired
    public void setiProjectRepository(IProjectRepository iProjectRepository) {
        this.iProjectRepository = iProjectRepository;
    }

    /**
     *
     * @param response the instance of HttpServletResponse
     * @param keyword the word by which user wants to search the DB
     * @throws IOException if the invalid input or invalid output occurs
     */
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response,@RequestParam(required = false) String keyword) throws IOException{
        List<Project> projectList;
        // setting the response type
        response.setContentType("application/vnd.ms-excel; charset=UTF-8");
//        application/octet-stream
//        application/vnd.ms-excel; charset=UTF-8
        // setting date formatter to file name
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue ="attachment; filename=projects_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey,headerValue);
        // to get the all data
        if(keyword == null)
            projectList = iProjectRepository.findAll();
        // to get the filtered data
        else
            projectList = iProjectRepository.findProjects(keyword);

        ProjectExcelExporter excelExporter = new ProjectExcelExporter(projectList);
        // exporting the project list to excel format
        excelExporter.export(response);
    }

    /**
     *
     * @param response the instance of HttpServletResponse
     * @param keyword the word by which user wants to search the DB
     * @throws DocumentException if the document is not found
     * @throws IOException if the invalid input or invalid output occurs
     */
    @GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response,@RequestParam(required = false) String keyword) throws DocumentException, IOException {
        List<Project> projectList;
        // setting the response type
        response.setContentType("application/pdf");
        // setting date formatter to file name
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Project_info_"+currentDateTime +".pdf";
        response.setHeader(headerKey,headerValue);
        // to get the all data
        if(keyword == null)
            projectList = iProjectRepository.findAll();
        // to get the filtered data
        else
            projectList = iProjectRepository.findProjects(keyword);

        ProjectPdfExporter pdfExporter = new ProjectPdfExporter(projectList);
        // exporting the project list to pdf format
        pdfExporter.export(response);
    }
}
