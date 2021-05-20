package ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanStudentNotFoundError extends Exception {
    @Override
    public String getMessage() {
        return "Student not found.";
    }
}

