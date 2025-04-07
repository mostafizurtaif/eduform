package edu.northsouth.eduform.frontend.dashboard.teacher.pages;

import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.exceptions.NotFoundException;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Taif
 */
public class Courses {

    public static JPanel coursesPanel(Teacher teacher, UserStorage<Teacher> crud, JTabbedPane tabbedPane) {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(null);
        headerPanel.setPreferredSize(new Dimension(400, 50));

        int padding = 10;

        JLabel headingLabel = new JLabel("My Courses");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setBounds(padding, padding, 200, 40);
        headerPanel.add(headingLabel);

        JButton addCourseBtn = new JButton("Add");
        addCourseBtn.setBounds(padding + headingLabel.getWidth() + 5, padding + 5, 70, 30);
        headerPanel.add(addCourseBtn);

        JPanel contentPanel = new JPanel(null);

        int yPosition = padding;

        if (teacher.getCourses().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("You currently have no courses :(");
            noCoursesLabel.setBounds(padding, yPosition, 250, 20);
            contentPanel.add(noCoursesLabel);
            yPosition += noCoursesLabel.getHeight() + padding;
        } else {
            for (Course course : teacher.getCourses()) {
                JPanel courseRowPanel = new JPanel(null);
                courseRowPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                courseRowPanel.setBounds(padding, yPosition, 570, 30); 

                JLabel courseLabel = new JLabel(course.getCourseCode().toUpperCase());
                courseLabel.setBounds(5, 5, 250, 20);
                courseRowPanel.add(courseLabel);

                JButton studentsBtn = new JButton("Students");
                studentsBtn.setBounds(260, 5, 100, 20);
                courseRowPanel.add(studentsBtn);

                JButton assignmentsBtn = new JButton("Assignments");
                assignmentsBtn.setBounds(365, 5, 120, 20);
                courseRowPanel.add(assignmentsBtn);

                JButton dropBtn = new JButton("Drop");
                dropBtn.setBounds(490, 5, 70, 20);
                courseRowPanel.add(dropBtn);

                dropBtn.addActionListener(e -> {
                    try {
                        teacher.dropCourseById(course.getCourseCode());

                        try {
                            crud.save(teacher);
                        } catch (IOException error) {
                            System.out.println(error.getMessage());
                        }

                        Container parent = mainPanel.getParent();
                        parent.removeAll();
                        parent.add(coursesPanel(teacher, crud, tabbedPane));
                        parent.revalidate();
                        parent.repaint();
                    } catch (NotFoundException error) {
                        System.out.println(error.getMessage());
                    }

                });

                studentsBtn.addActionListener(e -> {
                    String tabTitle = course.getCourseCode().toUpperCase() + " - Students";

                    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                        if (tabbedPane.getTitleAt(i).equals(tabTitle)) {
                            tabbedPane.setSelectedIndex(i);
                            return;
                        }
                    }

                    JPanel studentsPanel = Students.studentsPanel(teacher, crud, course, tabbedPane);

                    tabbedPane.addTab(tabTitle, studentsPanel);
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
                });
                
                assignmentsBtn.addActionListener(e -> {
                    String tabTitle = course.getCourseCode().toUpperCase() + " - Assignments";

                    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                        if (tabbedPane.getTitleAt(i).equals(tabTitle)) {
                            tabbedPane.setSelectedIndex(i);
                            return;
                        }
                    }

                    JPanel assignmentsPanel = Assignments.assignmentsPanel(teacher, crud, course, tabbedPane);

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
                });

                contentPanel.add(courseRowPanel);

                yPosition += 40; 
            }
        }
        contentPanel.setPreferredSize(new Dimension(400, yPosition));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        addCourseBtn.addActionListener(e -> {
            String courseCode = JOptionPane.showInputDialog(mainPanel, "Enter course code:");
            if (courseCode != null && !courseCode.trim().isEmpty()) {
                Course course = new Course(courseCode);
                teacher.addCourse(course);

                try {
                    crud.save(teacher);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Container parent = mainPanel.getParent();
                parent.removeAll();
                parent.add(coursesPanel(teacher, crud, tabbedPane));
                parent.revalidate();
                parent.repaint();
            }
        });

        return mainPanel;
    }
}
