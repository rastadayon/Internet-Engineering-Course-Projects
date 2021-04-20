package ir.ac.ut.ie.Bolbolestan05.controllers.models;

public class StudentInfo {
    private final String id;
    private final String name;
    private final String secondName;
    private final String birthDate;
    private final String field;
    private final String faculty;
    private final String level;
    private final String status;
    private final String img;

    public StudentInfo(String id, String name, String secondName,
                       String birthDate, String field, String faculty,
                       String level, String status, String img) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.field = field;
        this.faculty = faculty;
        this.level = level;
        this.status = status;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getBirthDate() {
        return birthDate;
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

    public String getStatus() {
        return status;
    }

    public String getImg() {
        return img;
    }
}
