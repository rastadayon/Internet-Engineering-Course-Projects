package Bolbolestan.exeptions;

public class BolbolestanMinimumUnitsError extends Exception{
    public BolbolestanMinimumUnitsError() {
        super("The minimum number of units has not been met");
    }
}
