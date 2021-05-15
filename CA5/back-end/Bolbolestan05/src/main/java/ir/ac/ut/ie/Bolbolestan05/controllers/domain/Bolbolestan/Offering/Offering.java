package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering;


import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Course.Course;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Utilities.Utils;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.ClassTimeData;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.ExamTimeData;

import java.util.ArrayList;
import java.util.List;


public class Offering {
    private final String classCode;
    private final String instructor;
    private Course course;
    private int capacity;
    private ClassTime classTime;
    private ExamTime examTime;
    private int signedUp = 0;

    public Offering(String classCode, String instructor,
                    int capacity, ClassTime classTime, ExamTime examTime) {
        this.classCode = classCode;
        this.instructor = instructor;
        this.capacity = capacity;
        this.classTime = classTime;
        this.examTime = examTime;
        this.signedUp = 0;
    }

    public Offering(Offering that) {
        this(that.getClassCode(), that.getInstructor(), that.getCapacity(),
                new ClassTime(that.getClassTime()), new ExamTime(that.getExamTime()));
        this.course = new Course(that.getCourse());
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setFarsiPrerequisites(ArrayList<String> farsiPrerequisites) {
        this.course.setFarsiPrerequisites(farsiPrerequisites);
    }

    public String getType() { return course.getType(); }

    public ExamTime getExamTime() { return examTime; }

    public String getClassCode() { return classCode; }

    public int getUnits() { return course.getUnits(); }

    public int getCapacity() { return capacity; }

    public String getCourseCode() {
        return course.getCourseCode();
    }

    public Course getCourse() { return course; }

    public int getSignedUp() { return signedUp; }

    public String getInstructor() { return instructor; }

    public void print() {
        System.out.println(String.format("course code : %s-%s", course.getCourseCode(), classCode));
        System.out.println(String.format("course name : %s", course.getName()));
        System.out.println(String.format("units : %d", course.getUnits()));
        System.out.println(String.format("type : %s", course.getType()));
        System.out.println(String.format("instructor : %s", instructor));
        System.out.println(String.format("capacity : %d", capacity));
        System.out.print("prerequisites : [ ");
        for (int i = 0; i < course.getPrerequisites().size(); i++) {
            if (i != 0)
                System.out.print(", ");
            System.out.print(course.getPrerequisites().get(i));
        }
        System.out.println(" ]");
        classTime.print();
        examTime.print();
    }

    public boolean isEqual(Offering offering) {
        return this.course.getCourseCode().equals(offering.getCourseCode()) &&
                this.classCode.equals(offering.getCourseCode());
    }

    public void decreaseSignedUp() {
        signedUp -= 1;
    }

    public void increaseSignedUp() {
        signedUp += 1;
    }

    public void increaseCapacity() {
        capacity += 1;
    }

    public ClassTime getClassTime() { return classTime; }

    public boolean hasCapacity() { return capacity - signedUp > 0; }

    public ArrayList<String> getPrerequisites() { return course.getPrerequisites(); }

    public String getClassDayString(String delimiter) {
        List<String> days = classTime.getDays();
        String daysString = "";
        for (int i = 0; i < days.size(); i++) {
            if (i > 0)
                daysString += delimiter;
            daysString += days.get(i);
        }
        return daysString;
    }

    public String getPrerequisitesString() {
        String prerequisitesString = "";
        for (int i = 0; i < course.getPrerequisites().size(); i++) {
            if (i > 0)
                prerequisitesString += "|";
            prerequisitesString += course.getPrerequisites().get(i);
        }
        return prerequisitesString;
    }

    public String getName() {return course.getName();}

    public boolean hasClassTime(String day, String startTime) {
        return classTime.hasTime(day, startTime);
    }

    public boolean doesClassTimeCollide (Offering c) {
        ArrayList<String> cDays = new ArrayList<>(c.getClassTime().getDays());
        cDays.retainAll(classTime.getDays());
        if (cDays.isEmpty()){
            return false;
        }

        Utils utils = Utils.getInstance();
        ArrayList<String> courseTime = utils.correctTimeFormat(this.getClassTime().getTime().split("-"));
        ArrayList<String> cTime = utils.correctTimeFormat(c.getClassTime().getTime().split("-"));
        assert courseTime.size() == 2 && cTime.size() == 2;
        String courseStart = courseTime.get(0);
        String courseEnd = courseTime.get(1);
        String cStart = cTime.get(0);
        String cEnd = cTime.get(1);
        return utils.doTimesCollide(cStart, cEnd, courseStart, courseEnd, "kk:mm");
    }

    public boolean doesExamTimeCollide (Offering c) {
        String cStart = c.getExamTime().getStart();
        String cEnd = c.getExamTime().getEnd();
        return Utils.getInstance().doDateTimesCollide(cStart, cEnd, this.getExamTime().getStart(),
                this.getExamTime().getEnd(), "yyyy-MM-dd'T'kk:mm:ss");
    }

    public boolean equals(Offering o) {
        if (o == null) {
            return false;
        }

        if (o.getCourseCode() != this.getCourseCode()) {
            return false;
        }
        return true;
    }

    public void setFarsiData() {
        ClassTimeData classTimeData = new ClassTimeData(this.getClassTime().getTime(), this.getClassTime().getDays());
        this.classTime = new ClassTime(classTimeData.getTime(), classTimeData.getFarsiDays());

        ExamTimeData examTimeData = new ExamTimeData(this.examTime);
        this.examTime = new ExamTime(examTimeData.getDate(), examTimeData.getExamDuration());

        ArrayList<String> farsiPrerequisite = new ArrayList<>();
        try {
            for (String prerequisite : this.getPrerequisites())
                farsiPrerequisite.add(Bolbolestan.getInstance().getCourseNameById(prerequisite));
            this.setFarsiPrerequisites(farsiPrerequisite);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
