package ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanCourseNotFoundError extends Exception {
    public BolbolestanCourseNotFoundError() {
        super("CourseNotFound");
    }
}
