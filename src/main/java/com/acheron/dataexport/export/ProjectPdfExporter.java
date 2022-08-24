
package com.acheron.dataexport.export;

import com.acheron.dataexport.model.Project;
import com.itextpdf.text.*;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.List;
import com.acheron.dataexport.model.FieldNames;
/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
public class ProjectPdfExporter {

    private List<Project> projectList;

    public ProjectPdfExporter(List<Project> projectList) {
        this.projectList = projectList;
    }

    /**
     *
     * @param table the instance of PdfPTable
     */
    private void writeTableHeader(PdfPTable table){

        // creating a pdf cell
        PdfPCell cell = new PdfPCell();
        // setting the cell colour
        cell.setBackgroundColor(BaseColor.WHITE);
        // setting padding
        cell.setPadding(5);

        // creating a font style
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        // setting font color
        font.setColor(BaseColor.RED);

        // setting all columns names into the cells
        cell.setPhrase(new Phrase(FieldNames.ID.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.PROJECT.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.STATUS.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.COST.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.MANAGER.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.CLIENT.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.NOOFRESOURCES.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.CREATIONDATE.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.ENDDATE.type,font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(FieldNames.DURATION.type,font));
        table.addCell(cell);

    }

    /**
     *
     * @param table the instance of PdfPTable
     */
    private void writeTableData(PdfPTable table){

        // iterating projectList to insert values into the pdf cells
        for(Project project: projectList){
            table.addCell(String.valueOf(project.getProjectId()));
            table.addCell(project.getProjectName());
            table.addCell(project.getProjectStatus());
            table.addCell(String.valueOf(project.getCostInDollars()));
            table.addCell(project.getManagerName());
            table.addCell(project.getClientName());
            table.addCell(String.valueOf(project.getNoOfResources()));
            table.addCell(String.valueOf(project.getCreationDate()));
            table.addCell(String.valueOf(project.getEstimatedEndDate()));
            table.addCell(String.valueOf(project.getDurationInMonths()));
        }

    }

    /**
     *
     * @param response the instance of HttpServletResponse
     * @throws IOException if the invalid input or invalid output occurs
     * @throws DocumentException if the document is not found
     */
    public void export(HttpServletResponse response) throws IOException, DocumentException {
        // creating A4 size document
        Document document = new Document(PageSize.A4);
        // sending the document as servlet response
        PdfWriter.getInstance(document,response.getOutputStream());
        // opening the document
        document.open();
        // adding the paragraph
        document.add(new Paragraph("List of all projects"));

        // declaring no of columns in a table
        PdfPTable table = new PdfPTable(10);
        // setting table properties
        table.setWidthPercentage(100);
        table.setSpacingBefore(20);
        // calling the method to write the header row
        writeTableHeader(table);
        // calling the method to write the actual values into the pdf cells
        writeTableData(table);
        // adding table to document
        document.add(table);
        // closing the document
        document.close();
    }
}
