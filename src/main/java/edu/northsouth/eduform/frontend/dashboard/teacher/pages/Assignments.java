/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.frontend.dashboard.teacher.pages;

import edu.northsouth.eduform.backend.Assignment;
import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import static edu.northsouth.eduform.frontend.dashboard.teacher.pages.Courses.coursesPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Taif
 */
public class Assignments {

    public static JPanel assignmentsPanel(Teacher teacher, UserStorage<Teacher> crud, Course course, JTabbedPane tabbedPane) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(null);
        headerPanel.setPreferredSize(new Dimension(400, 50));

        int padding = 10;

        JLabel headingLabel = new JLabel("Asssignments (" + course.getCourseCode().toUpperCase() + ")");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setBounds(padding, padding, 370, 40);
        headerPanel.add(headingLabel);

        JButton addAssignmentBtn = new JButton("Add");
        addAssignmentBtn.setBounds(padding + headingLabel.getWidth() + 5, padding + 5, 80, 30);
        headerPanel.add(addAssignmentBtn);

        JPanel contentPanel = new JPanel(null);

        int yPosition = padding;

        if (course.getAssignments().isEmpty()) {
            JLabel noAssignmentsLabel = new JLabel("No assignments given for " + course.getCourseCode().toUpperCase() + " :(");
            noAssignmentsLabel.setBounds(padding, yPosition, 250, 20);
            contentPanel.add(noAssignmentsLabel);
            yPosition += noAssignmentsLabel.getHeight() + padding;
        } else {
            for (Assignment assignment : course.getAssignments()) {
                JPanel assignmentRowPanel = new JPanel(null);
                assignmentRowPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                assignmentRowPanel.setBounds(padding, yPosition, 570, 30);

                JLabel assignmentLabel = new JLabel(assignment.getAssignmentName());
                assignmentLabel.setBounds(5, 5, 250, 20);
                assignmentRowPanel.add(assignmentLabel);

                JButton assingmentDetailsBtn = new JButton("Details");
                assingmentDetailsBtn.setBounds(260, 5, 100, 20);
                assignmentRowPanel.add(assingmentDetailsBtn);

                assingmentDetailsBtn.addActionListener(e -> {
                    JOptionPane.showMessageDialog(contentPanel,
                            "Title: " + assignment.getAssignmentName() + "\n"
                            + "Assignment ID: " + assignment.getAssignmentId().toUpperCase() + "\n"
                            + "Graded: " + (assignment.isIsGraded() ? "Yes" : "No"));
                });

                contentPanel.add(assignmentRowPanel);

                yPosition += 40;
            }
        }

        contentPanel.setPreferredSize(new Dimension(400, yPosition));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

//        addAssignmentBtn.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            int result = fileChooser.showOpenDialog(mainPanel);
//
//            if (result == JFileChooser.APPROVE_OPTION) {
//                File selectedFile = fileChooser.getSelectedFile();
//                File targetDir = new File("./src/main/java/edu/northsouth/eduform/backend/database/assignments/");
//
//                if (!targetDir.exists()) {
//                    targetDir.mkdirs();
//                }
//
//                File targetFile = new File(targetDir, selectedFile.getName());
//
//                try {
//                    Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                    JOptionPane.showMessageDialog(mainPanel, "Assignment uploaded!");
//
//                    Assignment assignment = new Assignment(selectedFile.getName(), "Assignment#" + course.getAssignments().size() + 1);
//                    course.addAssignment(assignment);
//
//                    ListIterator<Course> iterator = teacher.getCourses().listIterator();
//
//                    while (iterator.hasNext()) {
//                        Course currentCourse = iterator.next();
//
//                        if (currentCourse.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
//                            iterator.set(course);
//                        }
//                    }
//
//                    try {
//                        crud.save(teacher);
//
//                    } catch (IOException error) {
//                        error.printStackTrace();
//                    }
//
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(mainPanel, "Failed to upload assignment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
        addAssignmentBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(mainPanel);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                File targetDir = new File("./src/main/java/edu/northsouth/eduform/backend/database/assignments/");

                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }

                File targetFile = new File(targetDir, selectedFile.getName());

                try {
                    Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(mainPanel, "Assignment uploaded!");

                    Assignment assignment = new Assignment(selectedFile.getName(), "ASSIGN_" + (course.getAssignments().size() + 1));
                    course.addAssignment(assignment);

                    // Update the course in teacher's list
                    for (int i = 0; i < teacher.getCourses().size(); i++) {
                        if (teacher.getCourses().get(i).getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                            teacher.getCourses().set(i, course);
                            break;
                        }
                    }
                    
                    try {
                        crud.save(teacher);

                    } catch (IOException error) {
                        error.printStackTrace();
                    }

                    // Refresh just this panel instead of all tabs
                    int tabIndex = tabbedPane.indexOfComponent(mainPanel);
                    tabbedPane.setComponentAt(tabIndex, assignmentsPanel(teacher, crud, course, tabbedPane));

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(mainPanel, "Failed to upload assignment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return mainPanel;
    }
}
