package ir.ac.ut.ie.Bolbolestan07.controllers.models;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Offering.ExamTime;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Utilities.Utils;

public class ExamTimeData {
    private String date;
    private String examDuration;

    public ExamTimeData(ExamTime examTime) {
        this.date = Utils.informalDate(examTime);
        this.examDuration = Utils.informalTimeSpan(examTime);
    }

    public String getDate() {
        return date;
    }

    public String getExamDuration() {
        return examDuration;
    }
}
