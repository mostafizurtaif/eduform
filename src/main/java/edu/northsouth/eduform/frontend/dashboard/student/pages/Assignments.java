package edu.northsouth.eduform.frontend.dashboard.student.pages;

import edu.northsouth.eduform.backend.Assignment;
import edu.northsouth.eduform.backend.Course;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.BorderFactory;
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

        JPanel contentPanel = new JPanel(null);

        int yPosition = padding;

        if (course.getAssignments().isEmpty()) {
            JLabel noAssignmentsLabel = new JLabel("No assignments available for " + course.getCourseCode().toUpperCase() + " :(");
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

                JButton assignmentDownloadBtn = new JButton("Download");
                assignmentDownloadBtn.setBounds(260, 5, 100, 20);
                assignmentRowPanel.add(assignmentDownloadBtn);

                assignmentDownloadBtn.addActionListener(e -> {
                    String assignmentName = assignment.getAssignmentName();
                    File sourceDir = new File("./src/main/java/edu/northsouth/eduform/backend/database/assignments/");

                    File[] matchingFiles = sourceDir.listFiles((dir, name)
                            -> name.startsWith(assignmentName)
                    );

                    if (matchingFiles == null || matchingFiles.length == 0) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Assignment file not found: " + assignmentName,
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    File sourceFile = matchingFiles[0];

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File(sourceFile.getName()));
                    fileChooser.setDialogTitle("Choose download location");

                    int userSelection = fileChooser.showSaveDialog(mainPanel);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File targetFile = fileChooser.getSelectedFile();

                        try {
                            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            JOptionPane.showMessageDialog(mainPanel,
                                    "Assignment downloaded successfully to:\n" + targetFile.getAbsolutePath());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(mainPanel,
                                    "Failed to download assignment: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
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

        return mainPanel;
    }
}
