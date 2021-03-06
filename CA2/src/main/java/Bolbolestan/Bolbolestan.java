package Bolbolestan;

import Bolbolestan.exeptions.*;

import java.util.*;

public class Bolbolestan {
    private Map<String, Student> students = new HashMap<>();
    private Map<String, Course> courses = new HashMap<>();

    public Map<String, Course> getCourses() { return courses; }
    public Map<String, Student> getStudents() { return students; }

    public Student getStudentById(String studentId) { return students.get(studentId); }


    public Course getCourseByIdentifier(String courseCode, String classCode) {
        return courses.get(courseCode + "-" + classCode);
    }

    public boolean doesStudentExist(String studentId) {
        return students.containsKey(studentId);
    }

    public boolean doesCourseExist(String courseCode, String classCode) {
        String identifier = courseCode + "-" + classCode;
        return courses.containsKey(identifier);
    }

    public String addCourse(Course course) throws Exception {
        if (doesCourseExist(course.getCode(), course.getClassCode()))
            throw new BolbolestanRulesViolationError(String.
                    format("Offering with the code %s already exists.", course.getCode()));
        courses.put(course.getCode()+"-"+course.getClassCode(), course);
        return "Offering successfully added.";
    }

    public String addStudent(Student student) throws Exception {
        if (students.containsKey(student.getId()))
            throw new BolbolestanRulesViolationError(String.format("Student with id %s already exists.", student.getId()));
        students.put(student.getId(), student);
        return "Student successfully added.";
    }

    public void addGradeToStudent(Student student, Grade grade) {
        student.addGrade(grade);
    }

    public String addToWeeklySchedule(String studentId, String offeringCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        if (!courses.containsKey(offeringCode))
            throw new BolbolestanCourseNotFoundError();
        Student student = students.get(studentId);
        Course course = courses.get(offeringCode);
        student.addToWeeklySchedule(course);
        return "Course successfully added to weekly schedule.";
    }

    public String removeFromWeeklySchedule(String studentId, String offeringCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        if (!courses.containsKey(offeringCode))
            throw new BolbolestanCourseNotFoundError();
        Student student = students.get(studentId);
        Course course = courses.get(offeringCode);
        student.removeFromWeeklySchedule(course);
        return "Course successfully removed from weekly schedule.";
    }

    public WeeklySchedule handleGetWeeklySchedule(String studentId) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        return student.getWeeklySchedule();
    }

    public String handleFinalize(String studentId) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        student.getWeeklySchedule().finalizeWeeklySchedule();
        return "Weekly schedule successfully finalized.";
    }

    public int getUnitsPassed(String studentId) throws Exception {
        int unitsPassed = 0;
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        ArrayList<Grade> studentGrades = student.getGrades();
        for (Grade gradeItem : studentGrades) {
            String courseCode = gradeItem.getCode();
            if (!courses.containsKey(courseCode + "-01"))
                throw new BolbolestanCourseNotFoundError();
            Course course = getCourseByIdentifier(gradeItem.getCode(), "01");
            if (gradeItem.getGrade() >= 10)
                unitsPassed += course.getUnits();
        }
        return unitsPassed;
    }

    public int getTotalUnits(String studentId) throws Exception{
        WeeklySchedule weeklySchedule = handleGetWeeklySchedule(studentId);
        int units = weeklySchedule.getTotalUnits();
        return units;
    }

    public ArrayList<Course> getClassTimeConflictingWithStudent(
            String studentId, String courseCode, String classCode) throws Exception {
        ArrayList<Course> conflictingCourses = null;
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = getStudentById(studentId);
        if (!courses.containsKey(courseCode + "-" + classCode))
            throw new BolbolestanCourseNotFoundError();
        Course course = getCourseByIdentifier(courseCode, classCode);
        if (student.getWeeklySchedule() != null) {
            List<Course> studentWeeklySchedule = student.getWeeklySchedule().getOfferings();
            for (Course weekCourse : studentWeeklySchedule) {
                if (course.doesClassTimeCollide(weekCourse))
                    if (conflictingCourses == null) {
                        conflictingCourses = new ArrayList<Course>();
                        conflictingCourses.add(weekCourse);
                    }
            }
        }
        return conflictingCourses;
    }

    public ArrayList<Course> getExamTimeConflictingWithStudent(
            String studentId, String courseCode, String classCode) throws Exception {
        ArrayList<Course> conflictingCourses = null;
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = getStudentById(studentId);
        if (!courses.containsKey(courseCode + "-" + classCode))
            throw new BolbolestanCourseNotFoundError();
        Course course = getCourseByIdentifier(courseCode, classCode);
        if (student.getWeeklySchedule() != null){
            List<Course> studentWeeklySchedule = student.getWeeklySchedule().getOfferings();
            for (Course weekCourse : studentWeeklySchedule) {
                if (course.doesExamTimeCollide(weekCourse))
                    if (conflictingCourses == null) {
                        conflictingCourses = new ArrayList<Course>();
                        conflictingCourses.add(weekCourse);
                    }
            }
        }
        return conflictingCourses;
    }

    public boolean courseHasCapacity(String courseCode, String classCode) throws Exception{
        if (!courses.containsKey(courseCode + "-" + classCode))
            throw new BolbolestanCourseNotFoundError();
        return getCourseByIdentifier(courseCode, classCode).hasCapacity();
    }

    public ArrayList<String> getPrerequisitesNotPassed(
            String studentId, String courseCode, String classCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        if (!courses.containsKey(courseCode + "-" + classCode))
            throw new BolbolestanCourseNotFoundError();
        Course course = getCourseByIdentifier(courseCode, classCode);
        return student.getPrerequisitesNotPassed(course);
    }

    public void addCourseToStudent(String studentId, String courseCode, String classCode) throws Exception {
        if (!students.containsKey(studentId))
            throw new BolbolestanStudentNotFoundError();
        Student student = students.get(studentId);
        if (!courses.containsKey(courseCode + "-" + classCode))
            throw new BolbolestanCourseNotFoundError();
        Course course = getCourseByIdentifier(courseCode, classCode);
        ArrayList<Course> conflictingClassTimes = getClassTimeConflictingWithStudent(studentId, courseCode, classCode);
        ArrayList<Course> conflictingExamTimes = getExamTimeConflictingWithStudent(studentId, courseCode, classCode);
        ArrayList<String> prerequisitesNotPassed = getPrerequisitesNotPassed(studentId, courseCode, classCode);
        boolean hasCapacity = courseHasCapacity(courseCode, classCode);
        System.out.println("has capacity : " + hasCapacity);
        if (conflictingClassTimes == null && conflictingExamTimes == null && prerequisitesNotPassed == null && hasCapacity)
            student.addToWeeklySchedule(course);
    }
}
