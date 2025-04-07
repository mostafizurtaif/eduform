package edu.northsouth.eduform.frontend.dashboard.teacher;

import edu.northsouth.eduform.backend.users.Teacher;
import edu.northsouth.eduform.backend.users.User;
import edu.northsouth.eduform.backend.users.UserStorage;
import edu.northsouth.eduform.frontend.dashboard.teacher.pages.*;

import javax.swing.*;
import java.io.IOException;

public class TeacherDashboard {
    public static void load(User user) {
        UserStorage<Teacher> crud = new UserStorage(Teacher.class);
        Teacher teacher = loadTeacher(user, crud);
        
        JFrame frame = new JFrame("Dashboard (" + teacher.getId() + ")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(615, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Profile", new JScrollPane(Profile.profilePanel(teacher, crud)));
        tabbedPane.addTab("Courses", new JScrollPane(Courses.coursesPanel(teacher, crud, tabbedPane)));
        
        frame.add(tabbedPane);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static Teacher loadTeacher(User user, UserStorage<Teacher> crud) {
        try {
            return crud.load(user.getId());
        } catch (IOException | ClassNotFoundException error) {
            return new Teacher();
        }
    }
}
