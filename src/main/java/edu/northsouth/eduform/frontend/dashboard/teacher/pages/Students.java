/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.frontend.dashboard.teacher.pages;

import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.exceptions.NotFoundException;
import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import static edu.northsouth.eduform.frontend.dashboard.teacher.pages.Courses.coursesPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Taif
 */
public class Students {
    public static JPanel studentsPanel(Teacher teacher, UserStorage<Teacher> crud, Course course, JTabbedPane tabbedPane) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(null);
        headerPanel.setPreferredSize(new Dimension(400, 50));

        int padding = 10;

        JLabel headingLabel = new JLabel("Students (" + course.getCourseCode().toUpperCase() + ")");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setBounds(padding, padding, 300, 40);
        headerPanel.add(headingLabel);

        JPanel contentPanel = new JPanel(null);

        int yPosition = padding;

        if (course.getStudents().isEmpty()) {
            JLabel noStudentsLabel = new JLabel("No students for " + course.getCourseCode().toUpperCase() + " :(");
            noStudentsLabel.setBounds(padding, yPosition, 250, 20);
            contentPanel.add(noStudentsLabel);
            yPosition += noStudentsLabel.getHeight() + padding;
        } else {
            for (Student student : course.getStudents()) {
                JPanel studentRowPanel = new JPanel(null);
                studentRowPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                studentRowPanel.setBounds(padding, yPosition, 570, 30); // Adjust width as needed

                JLabel studentLabel = new JLabel(student.getName());
                studentLabel.setBounds(5, 5, 250, 20); // Position relative to courseRowPanel
                studentRowPanel.add(studentLabel);

                JButton gradeBtn = new JButton("Grade");
                gradeBtn.setBounds(260, 5, 100, 20); // Adjust positions
                studentRowPanel.add(gradeBtn);

                
                gradeBtn.addActionListener(e -> {
                    String tabTitle = course.getCourseCode() + " - Students";

                    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                        if (tabbedPane.getTitleAt(i).equals(tabTitle)) {
                            tabbedPane.setSelectedIndex(i);
                            return;
                        }
                    }

                    JPanel studentsPanel = new JPanel();
                    studentsPanel.add(new JLabel("Students for " + course.getCourseCode()));

                    // Add the tab with a close button
                    tabbedPane.addTab(tabTitle, studentsPanel);
                    int tabIndex = tabbedPane.indexOfTab(tabTitle);

                    // Create tab header with close button
                    JPanel tabHeader = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                    tabHeader.setOpaque(false);
                    JLabel tabLabel = new JLabel(tabTitle);
                    JButton closeButton = new JButton("x");
                    closeButton.setMargin(new Insets(0, 5, 0, 0));
                    closeButton.setFont(new Font("Arial", Font.PLAIN, 10));
                    closeButton.addActionListener(evt -> {
                        tabbedPane.remove(tabIndex);
                    });

                    tabHeader.add(tabLabel);
                    tabHeader.add(closeButton);

                    tabbedPane.setTabComponentAt(tabIndex, tabHeader);
                    tabbedPane.setSelectedIndex(tabIndex);
                });

                // Add the bordered row panel to contentPanel
                contentPanel.add(studentRowPanel);

                yPosition += 40; // Increase yPosition by row height + padding
            }
        }
        
        contentPanel.setPreferredSize(new Dimension(400, yPosition));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }
}
