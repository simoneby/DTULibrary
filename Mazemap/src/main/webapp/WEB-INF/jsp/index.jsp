<!DOCTYPE html>
<html lang="en">
	<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
	<%@page import="com.models.User"%>
	<%@page import="com.helpers.ServerUrl"%>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" type="text/css" href="./css/custom.css">
	<link rel="stylesheet" href="./kendo-ui-core/styles/kendo.common.min.css">
	<link rel="stylesheet" href="./kendo-ui-core/styles/kendo.default.min.css">
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.poptrox.min.js"></script>
	<script src="./js/jquery.scrolly.min.js"></script>
	<script src="./js/skel.min.js"></script>
	<script src="./js/util.js"></script>
	<script src="./js/main.js"></script>
	<script src="./kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="https://kit.fontawesome.com/7510661d31.js" crossorigin="anonymous"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DTU CampusNet</title>
</head>
<body>

	<input type="hidden" id="baseUrl" name="baseUrl" value = '<%= ServerUrl.baseUrl %>' > 

    <div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="#" class="active"><span class="icon fa-home"></span></a></li>
				<% if(session.getAttribute("user")!=null) {%>
				<li><a href="friendlist"><span class="icon fas fa-users"></span></a></li>
				<li><a href="survey_main"><i class="fas fa-poll-h"></i></a></li>
				<li><a href="events"><i class="fa fa-calendar"></i></a></li>
				<% } else { %>

				<li><a href="login"><span class="icon fas fa-sign-in"></span></a></li>
				<%}%>
				<% if(session.getAttribute("user")!=null) {%>
				<li><a href="logout"><span class="icon fas fa-sign-out"></span></a></li>
				<% } %>
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
			<!-- s191545 -->
			<section id="shareLocation">
				<% if(session.getAttribute("user")!=null) {%>
				<button onclick="broadcastToAll()" >
					<!-- <input type="button" onclick="window.alert('Hi!')"> -->
				<script>
					var baseUrl = $("#baseUrl").val();
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
					url: baseUrl+'/location/addMessage'
				});

					}
				</script>
   						Broadcast Location
				</button>
				<%} %>

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

	
</body>
</html>
