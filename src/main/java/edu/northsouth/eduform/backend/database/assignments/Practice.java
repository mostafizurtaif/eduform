/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practice;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author Taif
 */
public class Practice {

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("My Swing App");
//        frame.setLayout(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setBounds(100, 100, 400, 500);
//
//        JPanel formPanel = new JPanel();
//        formPanel.setLayout(null);
//        formPanel.setBounds(0, 0, 400, 230);
//
//        JLabel userNameLabel = new JLabel("Enter username: ");
//        userNameLabel.setBounds(10, 10, 150, 20);
//        formPanel.add(userNameLabel);
//
//        JTextField userNameField = new JTextField();
//        userNameField.setBounds(10, 35, 150, 30);
//        formPanel.add(userNameField);
//
//        JLabel passwordLabel = new JLabel("Enter password: ");
//        passwordLabel.setBounds(10, 70, 150, 20);
//        formPanel.add(passwordLabel);
//
//        JTextField passwordTextField = new JTextField();
//        passwordTextField.setBounds(10, 95, 150, 30);
//        formPanel.add(passwordTextField);
//
//        ButtonGroup genderGroup = new ButtonGroup();
//
//        JRadioButton maleRadioBtn = new JRadioButton("Male");
//        maleRadioBtn.setBounds(5, 130, 60, 20);
//        genderGroup.add(maleRadioBtn);
//        formPanel.add(maleRadioBtn);
//
//        JRadioButton femaleRadioBtn = new JRadioButton("Female");
//        femaleRadioBtn.setBounds(70, 130, 70, 20);
//        genderGroup.add(femaleRadioBtn);
//        formPanel.add(femaleRadioBtn);
//
//        JCheckBox keepLoggedInBtn = new JCheckBox("Stay logged in?");
//        keepLoggedInBtn.setBounds(6, 160, 150, 20);
//        formPanel.add(keepLoggedInBtn);
//
//        JButton registerBtn = new JButton("Register");
//        registerBtn.setBounds(10, 190, 100, 30);
//        formPanel.add(registerBtn);
//
//        JPanel infoPanel = new JPanel();
//        infoPanel.setLayout(null);
//        infoPanel.setBounds(0, 230, 400, 270);
//
//        JLabel formInfo = new JLabel();
//        formInfo.setBounds(10, 0, 380, 80);
//        infoPanel.add(formInfo);
//
//        frame.add(formPanel);
//        frame.add(infoPanel);
//
//        registerBtn.addActionListener((ActionEvent e) -> {
//            String username = userNameField.getText();
//            String password = passwordTextField.getText();
//            String gender = maleRadioBtn.isSelected() ? "Male"
//                    : (femaleRadioBtn.isSelected() ? "Female" : "N/A");
//            String keepLoggedIn = keepLoggedInBtn.isSelected() ? "Yes" : "No";
//            String info = String.format(""
//                    + "<html>"
//                    + "<p>"
//                    + "Username: %s <br />"
//                    + "Password: %s <br />"
//                    + "Gender: %s <br />"
//                    + "Remember login?: %s"
//                    + "</p>"
//                    + "</html>", 
//                    username, password, gender, keepLoggedIn);
//
//            formInfo.setText(info);
//        });
//
//        frame.setVisible(true);
//    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);

        String[][] data = {
            {"1", "Taif", "22"},
            {"2", "Hridoy", "22"},
            {"3", "Bau", "22"}
        };
        
        String[] cols = {"ID.", "Name", "Age"};
        
        JTable table = new JTable(data, cols);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 50, 100, 100);
        
        frame.add(sp);
        frame.setVisible(true);
    }
}
