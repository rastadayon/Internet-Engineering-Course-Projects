package ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.exeptions;

public class BolbolestanMinimumUnitsError extends Exception{
    public BolbolestanMinimumUnitsError() {
        super("حداقل تعداد واحد مجاز رعایت نشده است.");
    }
}
