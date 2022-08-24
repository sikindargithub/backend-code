package com.acheron.dataexport.model;
/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
public enum FieldNames {
    ID("Project Id"),
    PROJECT("Project Name"),
    STATUS("Project Status"),
    COST("Cost In Dollars"),
    MANAGER("Manager Name"),
    CLIENT("Client Name"),
    NOOFRESOURCES("No of Resources"),
    CREATIONDATE("Creation Date"),
    ENDDATE("Estimated End Date"),
    DURATION("Duration In Months");

    public String type;
    FieldNames(String type){
        this.type = type;
    }

}
