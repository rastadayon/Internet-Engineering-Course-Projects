package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanMaximumUnitsError extends Exception{
    public BolbolestanMaximumUnitsError() {
        super("حداکثر تعداد واحد مجاز رعایت نشده است.");
    }
}