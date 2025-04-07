package edu.northsouth.eduform.backend;

import edu.northsouth.eduform.backend.exceptions.InvalidGradeException;
import java.io.Serializable;

/**
 *
 * @author Taif
 */
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Course course;
    private float gradePoints;
    private static final float[] ALLOWED_GRADE_POINTS
            = {4.0f, 3.7f, 3.3f, 3.0f, 2.7f, 2.3f, 2.0f, 1.7f, 1.3f, 1.0f, 0.0f};

    public Grade(Course course, float gradePoints) {
        this.course = course;
        this.gradePoints = gradePoints;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public float getGradePoints() {
        return gradePoints;
    }

    public void setGradePoints(float gradePoints) throws InvalidGradeException {
        for (float allowedGradePoints : ALLOWED_GRADE_POINTS) {
            if (gradePoints == allowedGradePoints) {
                this.setGradePoints(gradePoints);
                return;
            }
        }

        throw new InvalidGradeException("Invalid grade points!");
    }
    
}
