package Bolbolestan.exeptions;

public class BolbolestanCapacityError extends Exception{
    public BolbolestanCapacityError(String code) {
        super("CapacityError " + code);
    }
}
