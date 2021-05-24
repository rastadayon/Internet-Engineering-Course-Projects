package ir.ac.ut.ie.Bolbolestan07.controllers.models;

public class Selection {
    String studentId;
    String courseCode;
    String classCode;
    String status;

    public Selection(String studentId, String courseCode, String classCode,
                     String status) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.classCode = classCode;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public String getStatus() {
        return status;
    }
}
