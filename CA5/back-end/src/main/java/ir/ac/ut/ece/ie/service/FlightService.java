package ir.ac.ut.ece.ie.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ir.ac.ut.ece.ie.FlightManager;
import ir.ac.ut.ece.ie.repository.FlightRepository;

@RestController
public class FlightService {

	@RequestMapping(value = "/getAvailableSeats/{destination}", method = RequestMethod.GET)
	public Integer getAvailableSeats(@PathVariable(value = "destination") String destination) {
		return FlightRepository.getInstance().getAvailableSeats(destination);
	}

	@RequestMapping(value = "/bookTheFlight", method = RequestMethod.POST)
	public void bookTheFlight(
			@RequestParam(value = "destination") String destination,
			@RequestParam(value = "numberOfTickets") int numberOfTickets,
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName) {
    	FlightManager.getInstance().bookFlight(destination, numberOfTickets);
	}
}
