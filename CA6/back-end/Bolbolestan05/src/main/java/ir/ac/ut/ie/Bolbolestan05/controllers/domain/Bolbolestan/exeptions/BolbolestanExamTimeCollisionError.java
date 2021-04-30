package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanExamTimeCollisionError extends Exception{
    public BolbolestanExamTimeCollisionError(String code1, String code2) {
        super("ExamTimeColisionError " + code1 + " " + code2);
    }
}