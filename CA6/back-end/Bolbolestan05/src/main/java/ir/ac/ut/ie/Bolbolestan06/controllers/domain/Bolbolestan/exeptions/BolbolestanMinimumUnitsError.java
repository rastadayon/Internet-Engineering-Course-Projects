package ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanMinimumUnitsError extends Exception{
    public BolbolestanMinimumUnitsError() {
        super("حداقل تعداد واحد مجاز رعایت نشده است.");
    }
}
