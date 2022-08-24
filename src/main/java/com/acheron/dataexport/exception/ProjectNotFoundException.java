
package com.acheron.dataexport.exception;

/**
 * @Author : Sipoy Sikindar Jillepally
 * @Date : 04-08-2022
 * @Project Name : data-export
 */
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super();
    }

    /**
     *
     * @param message the message the developer wants to show when the exception occurs
     */
    public ProjectNotFoundException(String message) {
        super(message);
    }
}
