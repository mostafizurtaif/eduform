package edu.northsouth.eduform.backend.users;

import edu.northsouth.eduform.backend.Grade;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Taif
 */
public class Student extends User {
    private int currentSemester;
    private final List<Grade> grades;

    public Student() {
        this.grades = new ArrayList<>();
    }
    
    public Student(String id, String password) {
        super(id, password);
        this.grades = new ArrayList<>();
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
    
    @Override
    public String toString() {
        return "Student ID: " + super.getId() + "\n"
                + "Current semester: " + currentSemester;
    }
}
