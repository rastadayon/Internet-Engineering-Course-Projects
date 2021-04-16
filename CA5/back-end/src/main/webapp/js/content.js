function setSeats(cityName) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			$("#" + cityName + "AvailableSeats").html("Available Seats: " + this.responseText);
		}
	};
	xhttp.open("GET", "getAvailableSeats/" + cityName, true);
	xhttp.send();
}
function mainPageBody() {
	$("#mainbody").html(' \
	<div class="container"> \
		<div id="fdw-pricing-table"> \
			<div class="plan plan1"> \
				<div class="header">Beijing</div> \
				<div class="price">$59</div> \
				<div class="monthly">Round Trip</div> \
				<ul> \
					<li>Dinner and Breakfast</li> \
					<li id="BeijingAvailableSeats"></li> \
				</ul> \
				<a class="signup" href="#" onclick="ticketInfoPageBody(\'Beijing\')">Buy</a> \
			</div> \
			<div class="plan plan2 popular-plan"> \
				<div class="header">Toronto</div> \
				<div class="price">$890</div> \
				<div class="monthly">Round Trip</div> \
				<ul> \
					<li>Breakfast, Lunch, and Dinner</li> \
					<li id="TorontoAvailableSeats"></li> \
				</ul> \
				<a class="signup" href="#" onclick="ticketInfoPageBody(\'Toronto\')">Buy</a> \
			</div> \
			<div class="plan plan3"> \
				<div class="header">Barcelona</div> \
				<div class="price">$275</div> \
				<div class="monthly">Round Trip</div> \
				<ul> \
					<li>Dinner and Breakfast</li> \
					<li id="BarcelonaAvailableSeats"></li> \
				</ul> \
				<a class="signup" href="#" onclick="ticketInfoPageBody(\'Barcelona\')">Buy</a> \
			</div> \
			<div class="plan plan4"> \
				<div class="header">Paris</div> \
				<div class="price">$325</div> \
				<div class="monthly">Round Trip</div> \
				<ul> \
					<li>Breakfast and Lunch</li> \
					<li id="ParisAvailableSeats"></li> \
				</ul> \
				<a class="signup" href="#" onclick="ticketInfoPageBody(\'Paris\')">Buy</a> \
			</div> \
		</div> \
	</div>');
	setSeats('Beijing');
	setSeats('Paris');
	setSeats('Toronto');
	setSeats('Barcelona');
}

function ticketInfoPageBody(destination) {
	$("#mainbody").html(' \
		<div class="container"> \
		<form action="bookFlight"> \
			<div class="form-group col-md-8"> \
				<label for="exampleFormControlInput1">Destination</label> \
				<input type="text" readonly class="form-control" id="destination" value="">'
			+ '</div> \
			<div class="form-group col-md-4"> \
				<label for="exampleFormControlInput1">Available Seats</label> \
				<input type="text" readonly class="form-control" id="availableSeats" value=""> \
			</div> \
			<div class="form-row"> \
				<div class="form-group col-md-6"> \
					<label class="control-label" for="inputEmail4">First Name</label> <input type="text" \
						class="form-control" id="firstName" id="inputEmail4" placeholder="First Name"> \
			        	<span class="glyphicon glyphicon-remove form-control-feedback"></span> \
				</div> \
				<div class="form-group col-md-6"> \
					<label class="control-label" for="inputEmail4">Last Name</label> <input type="text" \
						class="form-control" id="lastName" id="inputEmail4" placeholder="Last Name"> \
			        	<span class="glyphicon glyphicon-remove form-control-feedback"></span> \
				</div> \
			</div> \
			<div class="form-group col-md-2"> \
				<label class="control-label" for="exampleFormControlSelect1">Number Of Tickets</label> <select \
					class="form-control" id="numberOfTickets"> \
					<option>1</option> \
					<option>2</option> \
					<option>3</option> \
					<option>4</option> \
					<option>5</option> \
				</select> \
			</div> \
			<div class="col-md-12 text-center"> \
				<button type="button" class="btn btn-primary" id="doBook">Book</button> \
			</div> \
		</form> \
	</div> \
	');
	
	$('#destination').val(destination);
	$('#doBook').click(function() {
		bookTheFlight(destination, $("#numberOfTickets option:selected").text(), 
			$("#firstName").val(), $("#lastName").val());
	});
	
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			$("#availableSeats").val(this.responseText);
		}
	};
	xhttp.open("GET", "getAvailableSeats/" + destination, true);
	xhttp.send();
}

function bookTheFlight(destination, numberOfTickets, firstName, lastName) {
	
	$.post("bookTheFlight",
		  {
		    'destination': destination,
		    'numberOfTickets' : numberOfTickets,
		    'firstName' : firstName,
		    'lastName' : lastName
		  },
		  function(data, status){
			  bookingIsConfirmed(destination, numberOfTickets, firstName, lastName);
		  });
}

function bookingIsConfirmed(destination, numberOfTickets, firstName, lastName) {
	$("#mainbody").html('<h1>The flight is booked successfully!</h1> \
	In flight to ' + destination + ', ' + numberOfTickets + ' seats are reserved for ' +
	firstName + ' ' + lastName + '. \
	<br> \
	<div class="col-md-12 text-center"> \
		<button type="button" class="btn btn-primary" onClick="mainPageBody()">Home</button> \
	</div>'
	);
}