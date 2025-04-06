/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.backend.users;

import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.exceptions.NotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Taif
 */
public class User implements Serializable, UserIdentifiable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String password;
    private String name;
    private List<Course> courses;

    public User() {
        courses = new ArrayList<>();
    }
    
    public User(String id, String password) {
        this.id = id;
        this.password = password;
        courses = new ArrayList<>();
    }
    
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
    
    public Course getCourseById(String courseCode) throws NotFoundException {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        throw new NotFoundException("No course found with course code: " + courseCode);
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public void addCourse(Course course) {
        courses.add(course);
    }

    public void dropCourseById(String courseCode) throws NotFoundException {
        Iterator<Course> iterator = courses.iterator();

        while (iterator.hasNext()) {
            Course course = iterator.next();

            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                iterator.remove();
                return;
            }
        }

        throw new NotFoundException("No course found with course code: " + courseCode);
    }

    public boolean isEqual(User user) {
        return id.equals(user.id) && password.equals(user.password);
    }

}
