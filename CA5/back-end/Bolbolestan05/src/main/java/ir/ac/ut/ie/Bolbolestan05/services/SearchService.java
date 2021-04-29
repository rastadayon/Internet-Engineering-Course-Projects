package ir.ac.ut.ie.Bolbolestan05.services;

import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan05.controllers.domain.Bolbolestan.Offering.Offering;
import ir.ac.ut.ie.Bolbolestan05.controllers.models.SearchData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchService {
    private static final ArrayList<String> types = new ArrayList<String>(
            Arrays.asList("Umumi", "Asli", "Paaye", "Takhasosi"));

    private static ArrayList<Offering> sortSearchedOfferings(List<Offering> offerings, String filter) {
        ArrayList<Offering> sortedSearchResults = new ArrayList<>();
        for (String type: types) {
            for (Offering offering : offerings) {
                if (offering.getType().equals(filter) || filter.equals("All")) {
                    if (offering.getType().equals(type))
                        sortedSearchResults.add(offering);
                }
            }
        }
        return sortedSearchResults;
    }

    public static ArrayList<Offering> searchKeyword(SearchData searchData) throws Exception{
        Bolbolestan.getInstance().searchForCourses(searchData.getKeyword());
        List<Offering> searchResult = Bolbolestan.getInstance().getSearchedOfferings();
        return sortSearchedOfferings(searchResult, searchData.getType());
    }
}
