package Bolbolestan.Offering;

import Bolbolestan.Student.Student;
import Bolbolestan.exeptions.BolbolestanCourseNotFoundError;
import Bolbolestan.exeptions.BolbolestanRulesViolationError;

import java.util.ArrayList;
import java.util.List;

public class OfferingManager {
    List<Offering> offerings = new ArrayList<>();

    public List<Offering> getOfferings() {
        return offerings;
    }

    public Offering getOfferingById(String courseCode, String classCode) throws Exception {
        for (Offering offering : offerings)
            if (offering.getCourseCode().equals(courseCode) && offering.getClassCode().equals(classCode))
                return offering;
        throw new BolbolestanCourseNotFoundError();
    }

    public boolean offeringHasCapacity(String courseCode, String classCode) throws Exception {
        Offering offering = getOfferingById(courseCode, classCode);
        return offering.hasCapacity();
    }

    public boolean doesOfferingExist(Offering offering) {
        if (offering == null)
            return false;
        for (Offering offeringItem : offerings)
            if (offeringItem.isEqual(offering))
                return true;
        return false;
    }

    public void addOffering(Offering offering) throws Exception {
        if (doesOfferingExist(offering))
            throw new BolbolestanRulesViolationError(String.format("Offering.java with the code %s-%s already exists.",
                    offering.getCourseCode(), offering.getClassCode()));
        offerings.add(offering);
    }
}
