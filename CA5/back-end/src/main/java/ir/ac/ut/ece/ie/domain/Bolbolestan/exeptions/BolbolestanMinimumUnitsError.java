package ir.ac.ut.ece.ie.domain.Bolbolestan.exeptions;

public class BolbolestanMinimumUnitsError extends Exception{
    public BolbolestanMinimumUnitsError() {
        super("The minimum number of units has not been met");
    }
}
