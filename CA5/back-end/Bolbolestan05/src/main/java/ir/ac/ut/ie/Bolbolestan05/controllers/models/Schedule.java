package ir.ac.ut.ie.Bolbolestan05.controllers.models;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Offering> saturday = new ArrayList<Offering>();
    private List<Offering> sunday = new ArrayList<Offering>();
    private List<Offering> monday = new ArrayList<Offering>();
    private List<Offering> tuesday = new ArrayList<Offering>();
    private List<Offering> wednesday = new ArrayList<Offering>();

    public Schedule(List<Offering> offerings) {
        for(Offering offering: offerings) {

        }
    }
}
