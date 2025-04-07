/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.backend;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Taif
 */
public class Assignment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String assignmentName;
    private String assignmentId;
    private LocalDateTime dueDateTime;
    private boolean isGraded;

    public Assignment(String assignmentName, String assignmentId) {
        this.assignmentName = assignmentName;
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public LocalDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(LocalDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public boolean isIsGraded() {
        return isGraded;
    }

    public void setIsGraded(boolean isGraded) {
        this.isGraded = isGraded;
    }

}
