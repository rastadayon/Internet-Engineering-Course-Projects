package ir.ac.ut.ece.ie.domain.Bolbolestan.exeptions;

public class BolbolestanMaximumUnitsError extends Exception{
    public BolbolestanMaximumUnitsError() {
        super("The maximum number of units has not been met");
    }
}