package ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Utilities.Utils;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.StudentInfo;
import ir.ac.ut.ie.Bolbolestan07.repository.BolbolestanRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private final String id;
    private final String email;
    private String password;
    private final String name;
    private final String secondName;
    private final String birthDate;
    private final String field;
    private final String faculty;
    private final String level;
    private final String status;
    private final String img;
    private ArrayList<ReportCard> reportCards = new ArrayList<>();
    private String searchString = null;
    private CourseSelection courseSelection;

    public Student() {
        courseSelection = new CourseSelection(getCurrentTerm());
        this.id = null;
        this.email = null;
        this.password = null;
        this.name = null;
        this.secondName = null;
        this.birthDate = null;
        this.field = null;
        this.faculty = null;
        this.level = null;
        this.status = null;
        this.img = null;
    }

    public Student(String id, String email, String password,
                   String name, String secondName, String birthDate,
                   String field, String faculty, String level, String status,
                   String img) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.secondName = secondName;
        this.birthDate = birthDate;
        this.field = field;
        this.faculty = faculty;
        this.level = level;
        this.status = status;
        this.img = img;
        courseSelection = new CourseSelection(getCurrentTerm());
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getName() { return name; }

    public String getSecondName() { return secondName; }

    public String getBirthDate() { return birthDate; }

    public String getField() { return field; }

    public String getFaculty() { return faculty; }

    public String getLevel() { return level; }

    public String getStatus() { return status; }

    public String getImg() { return img; }

    public ArrayList<ReportCard> getReportCards() {
        ArrayList<ReportCard> reportCards = new ArrayList<>();
        ArrayList<Grade> grades = BolbolestanRepository.getInstance().getStudentGrades(this.id);
        if(grades == null) return null;
        for (Grade grade : grades) {
            int semester = grade.getTerm();
            boolean found = false;
            for (ReportCard reportcard : reportCards) {
                if (reportcard.getSemester() == semester) {
                    reportcard.addGrade(grade);
                    found = true;
                }
            }
            if(!found) {
                ReportCard reportCard = new ReportCard(semester);
                reportCard.addGrade(grade);
                reportCards.add(reportCard);
            }
        }
        for (ReportCard reportCard : reportCards) {
            reportCard.setGPA();
        }
        Collections.sort(reportCards);
        return reportCards;
    }

    public WeeklySchedule getSelectedOfferings() {
        return courseSelection.getSelectedOfferings();
    }

    public WeeklySchedule getSubmittedOfferings() {
        return courseSelection.getSubmittedOfferings();
    }

    public WeeklySchedule getWaitingOfferings() {
        return courseSelection.getWaitingOfferings();
    }

    public String getSearchString() { return searchString; }

    public void print() {
        System.out.println(String.format("student id : %s", id));
        System.out.println(String.format("student name : %s %s", name, secondName));
        System.out.println(String.format("birth date : %s", birthDate));
        System.out.println(String.format("field : %s", field));
        System.out.println(String.format("faculty : %s", faculty));
        System.out.println(String.format("level : %s", level));
        System.out.println(String.format("status : %s", status));
        System.out.println(String.format("image : %s", img));
    }

    public int getTotalPassedUnits() {
        ArrayList<Grade> grades = BolbolestanRepository.getInstance().getStudentGrades(this.id);
        int totalPassedUnits = 0;
        if (grades != null)
            for (Grade grade : grades) {
                if(grade.getGrade() >= 10)
                    totalPassedUnits += grade.getUnits();
            }
        return totalPassedUnits;
    }

    public float getGPA() {
        ArrayList<Grade> grades = BolbolestanRepository.getInstance().getStudentGrades(this.id);
        if(grades == null)
            return 0;
        int count = 0;
        float gradeSum = 0;
        Utils utils = Utils.getInstance();
        for (Grade grade : grades) {
            gradeSum += (grade.getGrade() * grade.getUnits());
            count += grade.getUnits();
        }
        if (count != 0)
            return utils.round(gradeSum/count, 2);
        else
            return 0;
    }

    public String getId() {
        return this.id;
    }

    public void addToSelectedOfferings(Offering offering) {
        courseSelection.addToSelectedOfferings(offering);
    }

    public void addToWaitingOfferings(Offering offering) throws Exception {
        courseSelection.addToWaitingOfferings(offering);
    }

    public boolean isEqual(Student student) {
        if (student == null)
            return false;
        return this.id.equals(student.getId());
    }

    public void removeFromWeeklySchedule(Offering offering) throws Exception {
        courseSelection.removeFromWeeklySchedule(offering);
    }

    public String getCourseNameByClassTime(String day, String startTime) {
        String courseName = courseSelection.getCourseNameByClassTime(day, startTime);
        return courseName;
    }

    public ArrayList<String> getPrerequisitesNotPassed(Offering offering) {
        ArrayList<String> notPassed = new ArrayList<>();
        ArrayList<String> prerequisites = offering.getPrerequisites();
        for (String prerequisite : prerequisites) {
            boolean found = false;
            for (ReportCard reportCard : reportCards) {
                for (Grade grade : reportCard.getGrades()) {
                    if (grade.getCode().equals(prerequisite) && grade.getGrade() >= 10) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                notPassed.add(prerequisite);
            }
        }
        return notPassed;
    }

    public boolean notPassedBefore(Offering offering) {
        for (ReportCard reportCard : reportCards) {
            for (Grade grade : reportCard.getGrades()) {
                if (grade.getCode().equals(offering.getCourseCode()) && grade.getGrade() >= 10) {
                    return false;
                }
            }
        }
        return true;
    }

    public void searchFor(String courseName) {
        searchString = courseName;
    }

    public void clearSearch() {
        searchString = null;
    }

    public void resetSelectedOfferings() {
        courseSelection.resetSelectedOfferings();
    }

    public String makePassedMessage(Offering offering) {
        String message = "درس با کد " + offering.getCourseCode() +
                " قبلا گذرانده شده است.";
        return message;
    }

    private List<String> checkNotPassedBefore() {
        List<String> errors = new ArrayList<String>();
        List<Offering> offerings = getSelectedOfferings().getOfferings();
        for (Offering offering: offerings)
            errors.addAll(coursePassedBeforeErrors(offering));
        return errors;
    }

    private List<String> coursePassedBeforeErrors(Offering offering) {
        List<String> errors = new ArrayList<String>();
        if (notPassedBefore(offering) == false)
            errors.add(makePassedMessage(offering));
        return errors;
    }

    public String makePrerequisitesMessage(List<String> notPassed, Offering offering) {
        String message = "پیشنیازی‌ها برای درس با کد " + offering.getCourseCode() +
        " هنوز گذرانده نشده است. {";
        for (int i=0; i<notPassed.size(); i++) {
            message += notPassed.get(i);
            if (i == (notPassed.size()-1))
                message += "}";
            else
                message += ", ";
        }
        return message;
    }

    private List<String> checkHasPrerequisites() {
        List<String> errors = new ArrayList<String>();
        List<Offering> offerings = getSelectedOfferings().getOfferings();
        for (Offering offering: offerings)
            errors.addAll(coursePrerequisitesErrors(offering));
        return errors;
    }

    private List<String> coursePrerequisitesErrors(Offering offering) {
        List<String> errors = new ArrayList<String>();
        if (getPrerequisitesNotPassed(offering).size() != 0)
            errors.add(makePrerequisitesMessage(getPrerequisitesNotPassed(offering),
                    offering));
        return errors;
    }

    public void setSubmissionErrors() {
        List<String> errors = new ArrayList<String>();
        errors.addAll(checkHasPrerequisites());
        errors.addAll(checkNotPassedBefore());
        System.out.println(errors.size());
        courseSelection.setSubmissionErrors(errors);
    }

    public void setWaitingErrors(Offering offering) {
        List<String> errors = new ArrayList<String>();
        errors.addAll(coursePrerequisitesErrors(offering));
        errors.addAll(coursePassedBeforeErrors(offering));
        courseSelection.setWaitingErrors(errors);
    }

    public List<String> getSubmissionErrors() { return courseSelection.getSubmissionErrors();}

    public List<String> getWaitingErrors() { return courseSelection.getWaitingErrors();}

    public void finalizeSchedule() {
        courseSelection.makeFinalized();
    }

    public int getTotalSelectedUnits() {
        return courseSelection.getTotalSelectedUnits();
    }

    public void checkWaitingCourses() {
        courseSelection.checkWaitingCourses();
    }

    public StudentInfo getInfo() {
        return new StudentInfo(this.id, this.name, this.secondName,
                this.birthDate, this.getGPA(), this.getTotalPassedUnits(),
                this.field, this.faculty, this.level, this.status, this.img);
    }

    private ReportCard getSemesterReportCard(int semester) {
        for (ReportCard reportCard : reportCards) {
            if (reportCard.getSemester() == semester)
                return reportCard;
        }
        return null;
    }

    public void setReportCards() {
        reportCards = getReportCards();
    }

    public void setReportCards(ArrayList<Grade> grades) {
        System.out.println("in student setting report cards");
        for (Grade grade : grades) {
            System.out.print("ghazal");
            int semester = grade.getTerm();
            ReportCard reportCard = getSemesterReportCard(semester);
            if(reportCard == null) {
                reportCard = new ReportCard(semester);
                reportCard.addGrade(grade);
                reportCards.add(reportCard);
            }
            else{
                reportCard.addGrade(grade);
            }
        }
        for (ReportCard reportCard : reportCards) {
            reportCard.setGPA();
        }
        Collections.sort(reportCards);
    }

    public int getCurrentTerm() {
        return (reportCards.size() + 1);
    }

    public CourseSelection getCourseSelection() {
        return courseSelection;
    }

    public void setCourseSelection(CourseSelection courseSelection) {
        this.courseSelection = courseSelection;
    }

    public void setPasswordHash() {
        this.password = DigestUtils.sha256Hex(this.password.getBytes());
    }
}
