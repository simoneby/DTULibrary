<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" type="text/css" href="./css/custom.css">
	<link rel="stylesheet" href="./kendo-ui-core/styles/kendo.common.min.css">
    <link rel="stylesheet" href="./kendo-ui-core/styles/kendo.default.min.css">
	
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DTU CampusNet</title>
</head>
<body>

		<!-- Scripts -->
	<script src="/js/jquery.min.js"></script>
	<script src="/js/jquery.poptrox.min.js"></script>
	<script src="/js/jquery.scrolly.min.js"></script>
	<script src="/js/skel.min.js"></script>
	<script src="/js/util.js"></script>
	<script src="/js/main.js"></script>
	<script src="/kendo-ui-core/js/kendo.core.min.js"></script>
    <div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="#" class="active"><span class="icon fa-home"></span></a></li>
				<li><a href=""><span class="icon fas fa-map"></span></a></li>
				<li><a href="friendlist"><span class="icon fas fa-users"></span></a></li>
				<li><a href="save_survey"><span class="icon fas fa-poll-h"></span></a></li>
				<li><a href="login"><span class="icon fas fa-sign-in"></span></a></li>
				<li><a href="logout"><span class="icon fas fa-sign-out"></span></a></li>
			</ul>
		</nav>


		<!-- Main -->
		<section id="main">
			<!-- Banner -->
			<section id="banner">
				<div class="inner">
				<div class="column">
					<h1>Welcome to DTU Hub</h1>
					<p>Your guide to life on campus</p>
					</div>
					<div class="column">
					<img alt="DTU logo" src="images/dtu_logo_white.png" class="logo">
					</div>
				</div>
			</section>

			<!--Share Location to All-->
			
			
			<!--CREATING A BUTTON FOR CREATE EVENT-->
			<section>
			<h1>Create an event by clicking below</h1>
			<a id="button" class="button">CLICK ME BABA</a>
			</section>
			
			<section id="createEvent">
			<div id="feedback"></div>
			<!-- The popup modal -->
					<div class="bg-modal">
						<div class="modal-content">
							<div id="close" class="close">+</div>
							<form id="createEventForm">
								<input type="text" id="description" name="eventDescr" placeholder="Event Description">
								<input type="date" id="date" name="date" placeholder="Event date">
								<input type="text" id="name" name="name" placeholder="Event Name">
								<input type="time" id="time" name="time" placeholder="Event hour">
								<input type="number" step=0.01 id="eventLng" name="lng" placeholder="Event Longitude">
								<input type="number" step=0.01 id="eventLat" name="lat" placeholder="Event Latitude">
								<button type="submit" id="submitEventForm" onclick="fire_ajax_submit()">Tester</button>
								
								<!-- <a href="" onclick="submitButton()" class="button">Submit</a> -->
							</form>
						</div>
						<script>
							
							document.getElementById("button").addEventListener("click", function() {
								document.querySelector(".bg-modal").style.display="flex";
							});
							
							document.getElementById("close").addEventListener("click", function() {
								document.querySelector(".bg-modal").style.display="none";
							});
							
							function fire_ajax_submit() {

							    var event = {}
							    event["description"] = $("#description").val();
							    event["name"] = $("#name").val();
							    event["date"] = $("#date").val();
							    event["time"] = $("#time").val();
							    event["lng"] = $("#eventLng").val();
							    event["lat"] = $("#eventLat").val();
							    

							    $("#submitEventForm").prop("disabled", true);

							    $.ajax({
							        type: "POST",
							        contentType: "application/json",
							        url: "/events/createevent",
							        data: JSON.stringify(event),
							        dataType: 'json',
							        cache: false,
							        timeout: 600000,
							        success: function (data) {

							            var json = "<h4>Ajax Response</h4><pre>"
							                + JSON.stringify(data, null, 4) + "</pre>";
							            $('#feedback').html(json);

							            console.log("SUCCESS : ", data);
							            $("#submitEventForm").prop("disabled", false);
							            document.querySelector(".bg-modal").style.display="none";

							        },
							        error: function (e) {

							            var json = "<h4>Ajax Response</h4><pre>"
							                + e.responseText + "</pre>";
							            $('#feedback').html(json);

							            console.log("ERROR : ", e);
							            $("#submitEventForm").prop("disabled", false);

							        }
							    });

							}
							
							//var tester = []
							//function submitButton() {
								
								//var event = [
								//	document.getElementById("eventName").value,
								//	document.getElementById("eventPlace").value,
								//	document.getElementByID("eventDate").value	
								//]
								//alert(document.getElementById("eventName").value);
								//tester.push.apply(tester, event)
								//alert(event);

							//}
							

						</script>
					</div>
			</section>
			
			
			
			<!-- STOP HERE TEST -->
			
			
			
			
			<section id="map">
			
				<div class="inner column">
					<h2> Upcoming Events: </h2>
					<ul>
					<li>
					Concert happening on <b> 20 Sept 2019 18:00</b> 
					at <b> Sports Hall </b>
					</li>
					</ul>
				</div>
				<div class="resp-container">
					<iframe class="resp-iframe" data-src="app.html" src="mazemap.html">
					</iframe>
				</div>
			</section>
			<section id="shareLocation">

				<button onclick="broadcastToAll()">
					<!-- <input type="button" onclick="window.alert('Hi!')"> -->
				<script>

					function broadcastToAll(){
						var locMessage = prompt("Tell your friends where you are: ");
						var stLoc = JSON.parse(localStorage.getItem("storedLocation"));
						alert("Broadcasting my Location:" + stLoc.lat + "," + stLoc.long +"\n" + locMessage);
						var loc = {
							coordinateX: stLoc.lat,
							coordinateY: stLoc.long,
							locationMessage: locMessage
						}
						//add location onto database
						console.log(loc);
						$.ajax({
					contentType: 'application/json',
					data: JSON.stringify(loc),
					dataType: 'json',
					success: function (data, status) {
						console.log(data);
						$("#result").text("<p>"+data+"</p>");
					},
					error: function () {
						console.log("Stuff happened");
					},
					processData: false,
					type: 'POST',
					url: 'http://localhost:8080/location/addMessage'
				});

					}
				</script>
   						Broadcast Location
				</button>
			</section>

			

			<!-- Contact -->
			<section id="contact">
				<!-- Social -->
					<div class="social column">
						<h3>About DTU Hub</h3>
						<p>Here you can look at upcoming events, follow your friends around and find classrooms and shit! Welcome to the DTU Hub, your new favourite website while on campus :).</p>
						<p>Click on events to see their description and add them to your calendar! </p>
						<h3>Social Media</h3>
						<ul class="icons">
							<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
							<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
							<li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
						</ul>
					</div>
			</section>

			<!-- Footer -->
			<footer id="footer">
				<div class="copyright">
					&copy; Group E: <a href="https://se2-webapp05.compute.dtu.dk/">DTU Snapmap</a>. Images: <a href="http://inside.dtu.dk">DTU Inside</a>.
				</div>
			</footer>
		</section>
	</div>


	<!-- Scripts -->
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.poptrox.min.js"></script>
	<script src="./js/jquery.scrolly.min.js"></script>
	<script src="./js/skel.min.js"></script>
	<script src="./js/util.js"></script>
	<script src="./js/main.js"></script>
	<script src="./kendo-ui-core/js/kendo.core.min.js"></script>

	
</body>
</html>
