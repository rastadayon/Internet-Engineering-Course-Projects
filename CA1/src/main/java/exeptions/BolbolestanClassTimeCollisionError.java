package exeptions;

public class BolbolestanClassTimeCollisionError extends Exception{
    public BolbolestanClassTimeCollisionError(String code1, String code2) {
        super("ClassTimeCollisionError " + code1 + " " + code2);
    }
}