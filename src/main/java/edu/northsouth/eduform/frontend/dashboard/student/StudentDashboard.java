package edu.northsouth.eduform.frontend.dashboard.student;

import edu.northsouth.eduform.backend.users.Student;
import edu.northsouth.eduform.backend.users.User;
import edu.northsouth.eduform.backend.users.UserStorage;
import edu.northsouth.eduform.frontend.dashboard.student.pages.Courses;
import edu.northsouth.eduform.frontend.dashboard.student.pages.Profile;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Taif
 */
public class StudentDashboard {
    public static void load(User user) {
        UserStorage<Student> crud = new UserStorage(Student.class);
        Student student = loadStudent(user, crud);
        
        JFrame frame = new JFrame("Dashboard (" + student.getId() + ")");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(615, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Profile", new JScrollPane(Profile.profilePanel(student, crud)));
        tabbedPane.addTab("Courses", new JScrollPane(Courses.coursesPanel(student, crud, tabbedPane)));
        
        frame.add(tabbedPane);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static Student loadStudent(User user, UserStorage<Student> crud) {
        try {
            return crud.load(user.getId());
        } catch (IOException | ClassNotFoundException error) {
            return new Student();
        }
    }
}
