package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanCourseNotFoundError extends Exception {
    public BolbolestanCourseNotFoundError() {
        super("CourseNotFound");
    }
}
