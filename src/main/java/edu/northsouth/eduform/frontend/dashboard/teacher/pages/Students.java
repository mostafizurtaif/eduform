package edu.northsouth.eduform.frontend.dashboard.teacher.pages;

import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
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
                studentRowPanel.setBounds(padding, yPosition, 570, 30);

                JLabel studentLabel = new JLabel(student.getName());
                studentLabel.setBounds(5, 5, 250, 20); 
                studentRowPanel.add(studentLabel);
                contentPanel.add(studentRowPanel);

                yPosition += 40; 
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
