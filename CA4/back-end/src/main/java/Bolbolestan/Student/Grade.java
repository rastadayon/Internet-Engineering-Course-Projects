package Bolbolestan.Student;

public class Grade {
    private final String code;
    private final float grade;
    private int units = 0;

    public Grade(String code, float grade) {
        this.code = code;
        this.grade = grade;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public int getUnits() {
        return units;
    }

    public String getCode() { return code; }

    public float getGrade() { return grade; }

    public void print() {
        System.out.println(String.format("grade : %s", grade));
        System.out.println(String.format("course code : %s", code));
    }
}
