package ir.ac.ut.ie.Bolbolestan07.controllers.models;

public class SignUp {
    private final String name;
    private final String secondName;
    private final String birthDate;
    private final String studentId;
    private final String field;
    private final String faculty;
    private final String level;
    private final String email;
    private final String password;
    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getBirthDate() { return birthDate; }

    public String getStudentId() {
        return studentId;
    }

    public String getField() {
        return field;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getLevel() {
        return level;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public SignUp(String name, String secondName, String birthDate,
                  String studentId, String field, String faculty, String level,
                  String email, String password) {
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.studentId = studentId;
        this.field = field;
        this.faculty = faculty;
        this.level = level;
        this.email = email;
        this.password = password;
    }

}
