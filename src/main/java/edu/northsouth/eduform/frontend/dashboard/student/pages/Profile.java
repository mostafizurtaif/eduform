package edu.northsouth.eduform.frontend.dashboard.student.pages;

import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.users.UserStorage;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author Taif
 */
public class Profile {
    public static JPanel profilePanel(Student student, UserStorage<Student> crud) {

        JPanel panel = new JPanel(null);
        int padding = 10;
        
        JLabel headingLabel = new JLabel("My Profile");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headingLabel.setBounds(padding, padding, 200, 40);
        panel.add(headingLabel);

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(padding, headingLabel.getY() + headingLabel.getHeight() + 10, 100, 20);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setText(student.getName());

        nameField.setBounds(padding, nameLabel.getY() + nameLabel.getHeight() + 5, 275, 30);
        panel.add(nameField);

        JLabel userIdLabel = new JLabel("ID: ");
        userIdLabel.setBounds(padding, nameField.getY() + nameField.getHeight() + 5, 100, 30);
        panel.add(userIdLabel);

        JTextField userIdField = new JTextField();
        userIdField.setText(student.getId());
        userIdField.setBounds(padding, userIdLabel.getY() + userIdLabel.getHeight() + 5, 275, 30);
        userIdField.setEnabled(false);
        panel.add(userIdField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(padding, userIdField.getY() + userIdField.getHeight() + 5, 100, 30);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setText(student.getPassword());
        passwordField.setBounds(padding, passwordLabel.getY() + passwordLabel.getHeight() + 5, 200, 30);
        panel.add(passwordField);

        JToggleButton togglePasswordBtn = new JToggleButton("Show");
        togglePasswordBtn.setBounds(padding + passwordField.getWidth() + 5, passwordLabel.getY() + passwordLabel.getHeight() + 5, 70, 28);
        panel.add(togglePasswordBtn);

        togglePasswordBtn.addActionListener(e -> {
            if (togglePasswordBtn.isSelected()) {
                passwordField.setEchoChar((char) 0);
                togglePasswordBtn.setText("Hide");
            } else {
                passwordField.setEchoChar('•');
                togglePasswordBtn.setText("Show");
            }
        });

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(padding, passwordField.getY() + passwordField.getHeight() + 5, 70, 30);
        panel.add(saveBtn);

        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(padding, saveBtn.getY() + saveBtn.getHeight() + 5, 250, 20);
        panel.add(messageLabel);

        saveBtn.addActionListener(e -> {
            student.setName(nameField.getText());
            student.setPassword(new String(passwordField.getPassword()));
            
            try {
                crud.save(student);
                messageLabel.setText("Save complete.");
                messageLabel.setForeground(Color.green);
            } catch (IOException error) {
                messageLabel.setText("Save failed!");
                messageLabel.setForeground(Color.red);
            }
        });

        return panel;
    }
}
