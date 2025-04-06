/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.backend;

import java.time.LocalDateTime;

/**
 *
 * @author Taif
 */
public class Assignment {

    private String assignmentName;
    private int assignmentId;
    private LocalDateTime dueDateTime;
    private boolean isGraded;

    public Assignment(String assignmentName, int assignmentId, LocalDateTime dueDateTime, boolean isGraded) {
        this.assignmentName = assignmentName;
        this.assignmentId = assignmentId;
        this.dueDateTime = dueDateTime;
        this.isGraded = isGraded;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
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
