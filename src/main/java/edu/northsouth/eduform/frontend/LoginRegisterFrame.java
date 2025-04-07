package edu.northsouth.eduform.frontend;

import javax.swing.*;
import java.awt.*;
import edu.northsouth.eduform.backend.Authentication;
import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.User;
import edu.northsouth.eduform.backend.users.UserStorage;
import edu.northsouth.eduform.frontend.dashboard.student.StudentDashboard;
import edu.northsouth.eduform.frontend.dashboard.teacher.TeacherDashboard;
import java.io.IOException;

/**
 *
 * @author Taif
 */
public class LoginRegisterFrame {

    public static void load() {
        JFrame frame = new JFrame("Log In");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        int padding = 10;

        JLabel userIdLabel = new JLabel("User ID: ");
        userIdLabel.setBounds(padding, padding, 100, 20);
        frame.add(userIdLabel);

        JTextField userIdField = new JTextField();
        userIdField.setBounds(padding, userIdLabel.getY() + userIdLabel.getHeight() + 5, 250, 30);
        frame.add(userIdField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(padding, userIdField.getY() + userIdField.getHeight() + 5, 100, 20);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(padding, passwordLabel.getY() + passwordLabel.getHeight() + 5, 250, 30);
        frame.add(passwordField);

        JRadioButton studentRadioBtn = new JRadioButton("Student");
        studentRadioBtn.setBounds(padding - 3, passwordField.getY() + passwordField.getHeight() + 5, 80, 30);
        frame.add(studentRadioBtn);

        JRadioButton teacherRadioBtn = new JRadioButton("Teacher");
        teacherRadioBtn.setBounds(padding - 3 + studentRadioBtn.getWidth(), passwordField.getY() + passwordField.getHeight() + 5, 80, 30);
        frame.add(teacherRadioBtn);

        ButtonGroup studentTeacherRadioGroup = new ButtonGroup();
        studentTeacherRadioGroup.add(studentRadioBtn);
        studentTeacherRadioGroup.add(teacherRadioBtn);

        JButton loginBtn = new JButton("Log In");
        loginBtn.setBounds(padding, teacherRadioBtn.getY() + teacherRadioBtn.getHeight() + 5, 70, 30);
        frame.add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(padding + 80, teacherRadioBtn.getY() + teacherRadioBtn.getHeight() + 5, 100, 30);
        frame.add(registerBtn);

        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(padding, registerBtn.getY() + registerBtn.getHeight() + 10, 200, 20);
        frame.add(messageLabel);

        int contentWidth = padding * 2 + 250;
        int contentHeight = messageLabel.getY() + messageLabel.getHeight() + padding;

        frame.getContentPane().setPreferredSize(new java.awt.Dimension(contentWidth, contentHeight));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());
            User user = new User(userId, password);

            if (!Authentication.verifyUser(user)) {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Incorrect credentials!");
            } else {
                frame.dispose();
                
                // Open dashboard frame
                if (teacherRadioBtn.isSelected()) {
                    TeacherDashboard.load(user);
                } else if (studentRadioBtn.isSelected()) {
                    StudentDashboard.load(user);
                }
            }
        });

        registerBtn.addActionListener(e -> {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());

            if (studentRadioBtn.isSelected()) {
                Student student = new Student(userId, password);
                UserStorage<Student> crud = new UserStorage<>(Student.class);

                try {
                    crud.save(student);
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Registration complete!");
                } catch (IOException error) {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Registration failed!");
                }
            } else if (teacherRadioBtn.isSelected()) {
                Teacher teacher = new Teacher(userId, password);
                UserStorage<Teacher> crud = new UserStorage<>(Teacher.class);

                try {
                    crud.save(teacher);
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Registration complete!");
                } catch (IOException error) {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Registration failed!");
                }
            }
        });
    }
}
