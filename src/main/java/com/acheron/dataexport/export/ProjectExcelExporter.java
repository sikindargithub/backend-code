
package com.acheron.dataexport.export;

import com.acheron.dataexport.model.FieldNames;
import com.acheron.dataexport.model.Project;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
public class ProjectExcelExporter {

    // creating Excel workbook instance
    public HSSFWorkbook workbook;
    // creating Excel worksheet instance
    public HSSFSheet sheet;
    public List<Project> projectList;

    public ProjectExcelExporter(List<Project> projectList){
        this.projectList = projectList;
        workbook = new HSSFWorkbook();

    }

    /**
     *
     * @param row instance of Row class
     * @param columnCount no of columns
     * @param value the value which will be placed in an excel cell
     * @param style the style of the excel cell
     */
    public void createCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);

        // creating cells for no of columns
        Cell cell = row.createCell(columnCount);
        // type checking of values before storing them into cells
        if(value instanceof Long){
            cell.setCellValue((Long) value);
        }
        else if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }
        else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }
        else if(value instanceof Double){
            cell.setCellValue((Double) value);
        }
        else if(value instanceof LocalDate){
            cell.setCellValue(String.valueOf((LocalDate) value));
        }
        else{
            cell.setCellValue((String) value);
        }
        // setting cell style
        cell.setCellStyle(style);
    }

    /**
     * Used to write header row in excel
     */
    public void writeHeaderLine(){
        // giving the name to excel sheet
        sheet = workbook.createSheet("Project Details");
        // creating first row
        Row row = sheet.createRow(0);
        // creating cell style
        CellStyle style = workbook.createCellStyle();
        // creating font
        HSSFFont font = workbook.createFont();
        // setting font properties
        font.setBold(true);
        font.setFontHeight((short) 20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        // giving name to first row
        createCell(row,0,"Project Information",style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,9));
        font.setFontHeightInPoints((short) 10);

        // creating second row
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight((short) 15);
        style.setFont(font);
        // inserting column name into the cells
        createCell(row,0, FieldNames.ID.type,style);
        createCell(row,1,FieldNames.PROJECT.type,style);
        createCell(row,2,FieldNames.STATUS.type,style);
        createCell(row,3,FieldNames.COST.type,style);
        createCell(row,4,FieldNames.MANAGER.type,style);
        createCell(row,5,FieldNames.CLIENT.type,style);
        createCell(row,6,FieldNames.NOOFRESOURCES.type,style);
        createCell(row,7,FieldNames.CREATIONDATE.type,style);
        createCell(row,8,FieldNames.ENDDATE.type,style);
        createCell(row,9,FieldNames.DURATION.type,style);

    }

    /**
     * Used to write actual data into the cells of second & following rows in excel
     */
    public void writeDataLines(){
        int rowCount=2;
        // creating style for the cell
        CellStyle style = workbook.createCellStyle();
        // creating a font
        HSSFFont font = workbook.createFont();
        // setting font height
        font.setFontHeight((short) 14);
        style.setFont(font);
        // iterating the projectList to insert actual values into the cells
        for (Project project: projectList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount =0;
            createCell(row,columnCount++,project.getProjectId(),style);
            createCell(row,columnCount++,project.getProjectName(),style);
            createCell(row,columnCount++,project.getProjectStatus(),style);
            createCell(row,columnCount++,project.getCostInDollars(),style);
            createCell(row,columnCount++,project.getManagerName(),style);
            createCell(row,columnCount++,project.getClientName(),style);
            createCell(row,columnCount++,project.getNoOfResources(),style);
            createCell(row,columnCount++,project.getCreationDate(),style);
            createCell(row,columnCount++,project.getEstimatedEndDate(),style);
            createCell(row,columnCount++,project.getDurationInMonths(),style);
        }
    }

    /**
     *
     * @param response the instance of HttpServletResponse
     * @throws IOException
     */
    public void export(HttpServletResponse response) throws IOException{

        // calling the method to write the header row
        writeHeaderLine();
        // calling the method to write the actual data into the cells
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        // passing output stream to workbook write method
        workbook.write(outputStream);
        // closing the workbook
        workbook.close();
        // closing the output stream
        outputStream.close();
    }


}

