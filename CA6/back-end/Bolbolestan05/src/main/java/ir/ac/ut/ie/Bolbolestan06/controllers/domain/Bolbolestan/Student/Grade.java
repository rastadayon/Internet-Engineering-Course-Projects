package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student;

public class Grade {
    private String studentId;
    private final String code;
    private String courseName;
    private final int grade;
    private final int term;
    private int units = 0;

    public Grade(String studentId, String code, int grade, int term) {
        this.studentId = studentId;
        this.code = code;
        this.grade = grade;
        this.term  = term;
    }

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId) { this.studentId = studentId; }

    public void setUnits(int units) {
        this.units = units;
    }

    public void setCourseName (String courseName) { this.courseName = courseName; }

    public int getUnits() {
        return units;
    }

    public String getCode() { return code; }

    public String getCourseName() { return courseName; }

    public int getGrade() { return grade; }

    public int getTerm() { return term; }

    public void print() {
        System.out.println(String.format("grade : %s", grade));
        System.out.println(String.format("course code : %s", code));
    }
}
