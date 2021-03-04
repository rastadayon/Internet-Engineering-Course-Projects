package Bolbolestan;

public class Grade {
    private final String code;
    private final int grade;

    public Grade(String code, int grade) {
        this.code = code;
        this.grade = grade;
    }

    public void print() {
        System.out.println(String.format("grade : %s", grade));
        System.out.println(String.format("course code : %s", code));
    }
}
