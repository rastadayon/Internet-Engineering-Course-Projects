package ir.ac.ut.ie.Bolbolestan05.domain.Bolbolestan.exeptions;

public class BolbolestanMinimumUnitsError extends Exception{
    public BolbolestanMinimumUnitsError() {
        super("The minimum number of units has not been met");
    }
}
