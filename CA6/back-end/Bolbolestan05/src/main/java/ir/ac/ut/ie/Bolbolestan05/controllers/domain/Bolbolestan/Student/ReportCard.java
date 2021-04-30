package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Student;

import java.util.ArrayList;

public class ReportCard implements Comparable {
    int semester;
    float GPA;
    ArrayList<Grade> grades;

    public ReportCard(int semester) {
        this.semester = semester;
        grades = new ArrayList<>();
    }

    public int getSemester() {
        return semester;
    }

    public float getGPA() {
        return GPA;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public int getUnits() {
        int units = 0;
        if (grades != null)
            for (Grade grade: grades) {
                units += grade.getUnits();
            }
        return units;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public void setGPA() {
        int units = 0;
        int gradeSum = 0;
        for (Grade grade : grades) {
            units += grade.getUnits();
            gradeSum += grade.getGrade() * grade.getUnits();
        }
        if(units != 0)
            GPA = (float)gradeSum/units;
        else
            GPA = 0;
    }

    public void print() {
        System.out.println(String.format("semester: %d", semester));
        System.out.println(String.format("GPA: %f", GPA));
        for (Grade grade : grades)
            grade.print();
        System.out.println("-------------------------");
    }

    @Override
    public int compareTo(Object compareTo) {
        int compareSemester =((ReportCard)compareTo).getSemester();
        return this.semester - compareSemester;
    }
}
