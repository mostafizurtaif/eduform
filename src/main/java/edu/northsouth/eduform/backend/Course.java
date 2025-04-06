/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.backend;

import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

/**
 *
 * @author Taif
 */
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String courseCode;
    private final List<Student> students;
    private final List<Assignment> assignments;

    public Course(String courseCode) {
        this.courseCode = courseCode;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    // ---------- courseCode SECTION: BEGINS ---------- //
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    // ---------- courseCode SECTION: ENDS ---------- //

    // ---------- students SECTION: BEGINS ---------- //
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(String id) throws NotFoundException {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        throw new NotFoundException("No student found with ID: " + id);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudentById(String id) throws NotFoundException {
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            Student student = iterator.next();

            if (student.getId().equals(id)) {
                iterator.remove();
                return;
            }
        }

        throw new NotFoundException("No student found with ID: " + id);
    }
    // ---------- students SECTION: ENDS ---------- //

    // ---------- assignment SECTION: BEGINS ---------- //
    public List<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    public Assignment getAssignmentById(int assignmentId) throws NotFoundException {
        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentId() == assignmentId) {
                return assignment;
            }
        }
        throw new NotFoundException("No assignment found with ID: " + assignmentId);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void removeAssignmentById(int assignmentId) throws NotFoundException {
        Iterator<Assignment> iterator = assignments.iterator();

        while (iterator.hasNext()) {
            Assignment assignment = iterator.next();

            if (assignment.getAssignmentId() == assignmentId) {
                iterator.remove();
                return;
            }
        }

        throw new NotFoundException("No assignment found with ID: " + assignmentId);
    }
    // ---------- assignment SECTION: ENDS ---------- //
}
