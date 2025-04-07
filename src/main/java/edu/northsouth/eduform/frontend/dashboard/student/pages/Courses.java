/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.frontend.dashboard.student.pages;

import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.exceptions.NotFoundException;
import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import edu.northsouth.eduform.frontend.dashboard.teacher.pages.Students;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Taif
 */
public class Courses {

    public static JPanel coursesPanel(Student student, UserStorage<Student> crud, JTabbedPane tabbedPane) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(null);
        headerPanel.setPreferredSize(new Dimension(400, 50));

        int padding = 10;

        JLabel headingLabel = new JLabel("My Courses");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setBounds(padding, padding, 200, 40);
        headerPanel.add(headingLabel);

        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.setBounds(padding + headingLabel.getWidth() + 5, padding + 5, 100, 30);
        headerPanel.add(addCourseBtn);

        JPanel contentPanel = new JPanel(null);

        int yPosition = padding;

        if (student.getCourses().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("You currently have no courses :(");
            noCoursesLabel.setBounds(padding, yPosition, 250, 20);
            contentPanel.add(noCoursesLabel);
            yPosition += noCoursesLabel.getHeight() + padding;
        } else {
            for (Course course : student.getCourses()) {
                // Create a panel for each course row with border
                JPanel courseRowPanel = new JPanel(null);
                courseRowPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                courseRowPanel.setBounds(padding, yPosition, 570, 30); // Adjust width as needed

                JLabel courseLabel = new JLabel(course.getCourseCode().toUpperCase());
                courseLabel.setBounds(5, 5, 250, 20); // Position relative to courseRowPanel
                courseRowPanel.add(courseLabel);

                JButton assignmentsBtn = new JButton("Assignments");
                assignmentsBtn.setBounds(260, 5, 120, 20);
                courseRowPanel.add(assignmentsBtn);

                JButton dropBtn = new JButton("Drop");
                dropBtn.setBounds(365, 5, 120, 20);
                courseRowPanel.add(dropBtn);

                dropBtn.addActionListener(e -> {
                    try {
                        // Remove from student first
                        student.dropCourseById(course.getCourseCode());

                        // Remove from teacher's course
                        UserStorage<Teacher> teacherCrud = new UserStorage<>(Teacher.class);
                        Teacher teacher = teacherCrud.load(course.getFaculty());

                        Course teacherCourse = teacher.getCourseById(course.getCourseCode());
                        teacherCourse.removeStudentById(student.getId()); // You'll need this method

                        // Save both
                        crud.save(student);
                        teacherCrud.save(teacher);

                        // Refresh UI
                        Container parent = mainPanel.getParent();
                        parent.removeAll();
                        parent.add(coursesPanel(student, crud, tabbedPane));
                        parent.revalidate();
                        parent.repaint();

                    } catch (NotFoundException | IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                });

                assignmentsBtn.addActionListener(e -> {
                    String tabTitle = course.getCourseCode().toUpperCase() + " - Assignments";

                    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                        if (tabbedPane.getTitleAt(i).equals(tabTitle)) {
                            tabbedPane.setSelectedIndex(i);
                            return;
                        }
                    }

                    try {
                        UserStorage<Teacher> crudTeacher = new UserStorage<>(Teacher.class);
                        Teacher teacher = crudTeacher.load(course.getFaculty());

                        Course teacherCourse = null;
                        for (Course c : teacher.getCourses()) {
                            if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                                teacherCourse = c;
                                break;
                            }
                        }

                        if (teacherCourse == null) {
                            throw new NotFoundException("Course not found in teacher's list");
                        }

                        JPanel assignmentsPanel = Assignments.assignmentsPanel(teacher, crudTeacher, teacherCourse, tabbedPane);

                        tabbedPane.addTab(tabTitle, assignmentsPanel);
                        int tabIndex = tabbedPane.indexOfTab(tabTitle);

                        JPanel tabHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                        tabHeader.setOpaque(false);
                        JLabel tabLabel = new JLabel(tabTitle);
                        JButton closeButton = new JButton("x");
                        closeButton.setMargin(new Insets(0, 0, 0, 0));
                        closeButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        closeButton.addActionListener(evt -> {
                            tabbedPane.remove(tabIndex);
                        });

                        tabHeader.add(tabLabel);
                        tabHeader.add(closeButton);

                        tabbedPane.setTabComponentAt(tabIndex, tabHeader);
                        tabbedPane.setSelectedIndex(tabIndex);

                    } catch (NotFoundException ex) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Course not found: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(mainPanel,
                                "Error loading course data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                // Add the bordered row panel to contentPanel
                contentPanel.add(courseRowPanel);

                yPosition += 40; // Increase yPosition by row height + padding
            }
        }
        contentPanel.setPreferredSize(new Dimension(400, yPosition));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        addCourseBtn.addActionListener(e -> {
            // Create a panel with two fields
            JPanel inputPanel = new JPanel(new GridLayout(2, 2));

            JLabel courseCodeLabel = new JLabel("Course Code:");
            JTextField courseCodeField = new JTextField();

            JLabel facultyInitialsLabel = new JLabel("Faculty Initials:");
            JTextField facultyInitialsField = new JTextField();

            inputPanel.add(courseCodeLabel);
            inputPanel.add(courseCodeField);
            inputPanel.add(facultyInitialsLabel);
            inputPanel.add(facultyInitialsField);

            int result = JOptionPane.showConfirmDialog(
                    mainPanel,
                    inputPanel,
                    "Add New Course",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String courseCode = courseCodeField.getText().trim();
                String facultyInitials = facultyInitialsField.getText().trim();

                if (!courseCode.isEmpty() && !facultyInitials.isEmpty()) {
                    try {
                        UserStorage<Teacher> teacherCrud = new UserStorage<>(Teacher.class);
                        Teacher teacher = teacherCrud.load(facultyInitials);

                        // Find the course in teacher's list
                        Course teacherCourse = teacher.getCourseById(courseCode);

                        // Create a NEW course for the student with the same details
                        Course studentCourse = new Course(teacherCourse.getCourseCode());
                        studentCourse.setFaculty(facultyInitials);

                        // Add student to teacher's course
                        teacherCourse.addStudent(student);

                        // Add course to student
                        student.addCourse(studentCourse);

                        // Save both
                        crud.save(student);
                        teacherCrud.save(teacher);

                        // Refresh UI
                        Container parent = mainPanel.getParent();
                        parent.removeAll();
                        parent.add(coursesPanel(student, crud, tabbedPane));
                        parent.revalidate();
                        parent.repaint();

                    } catch (NotFoundException ex) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Course not found with faculty", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        return mainPanel;
    }
}
