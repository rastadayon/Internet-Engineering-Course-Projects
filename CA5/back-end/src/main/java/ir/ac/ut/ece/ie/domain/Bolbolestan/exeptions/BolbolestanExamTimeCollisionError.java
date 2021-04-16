package ir.ac.ut.ece.ie.domain.Bolbolestan.exeptions;

public class BolbolestanExamTimeCollisionError extends Exception{
    public BolbolestanExamTimeCollisionError(String code1, String code2) {
        super("ExamTimeColisionError " + code1 + " " + code2);
    }
}