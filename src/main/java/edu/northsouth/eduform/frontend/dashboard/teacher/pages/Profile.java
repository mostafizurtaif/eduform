package edu.northsouth.eduform.frontend.dashboard.teacher.pages;

import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.UserStorage;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Profile {

    public static JPanel profilePanel(Teacher teacher, UserStorage<Teacher> crud) {

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
        nameField.setText(teacher.getName());

        nameField.setBounds(padding, nameLabel.getY() + nameLabel.getHeight() + 5, 275, 30);
        panel.add(nameField);

        JLabel userIdLabel = new JLabel("ID: ");
        userIdLabel.setBounds(padding, nameField.getY() + nameField.getHeight() + 5, 100, 30);
        panel.add(userIdLabel);

        JTextField userIdField = new JTextField();
        userIdField.setText(teacher.getId());
        userIdField.setBounds(padding, userIdLabel.getY() + userIdLabel.getHeight() + 5, 275, 30);
        userIdField.setEnabled(false);
        panel.add(userIdField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(padding, userIdField.getY() + userIdField.getHeight() + 5, 100, 30);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setText(teacher.getPassword());
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
            teacher.setName(nameField.getText());
            teacher.setPassword(new String(passwordField.getPassword()));
            
            try {
                crud.save(teacher);
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
