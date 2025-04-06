/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.northsouth.eduform.backend;

import java.io.*;
import edu.northsouth.eduform.backend.users.*;

/**
 *
 * @author Taif
 */
public class Authentication {

public static boolean verifyUser(User user) {
    try {
        UserStorage<Student> ss = new UserStorage<>(Student.class);
        Student storedStudent = ss.load(user.getId());
        if (storedStudent.isEqual(user)) return true;
    } catch (IOException | ClassNotFoundException ignored) {}

    try {
        UserStorage<Teacher> ts = new UserStorage<>(Teacher.class);
        Teacher storedTeacher = ts.load(user.getId());
        if (storedTeacher.isEqual(user)) return true;
    } catch (IOException | ClassNotFoundException ignored) {}

    return false;
}
}
