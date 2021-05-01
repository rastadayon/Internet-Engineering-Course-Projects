package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanMaximumUnitsError extends Exception{
    public BolbolestanMaximumUnitsError() {
        super("حداکثر تعداد واحد مجاز رعایت نشده است.");
    }
}