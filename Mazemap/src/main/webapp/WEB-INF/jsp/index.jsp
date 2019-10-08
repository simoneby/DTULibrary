<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/custom.css">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DTU CampusNet</title>
</head>
<body>
    <div class="page-wrap">

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="index.jsp" class="active"><span class="icon fa-home"></span></a></li>
						<li><a href=""><span class="icon fas fa-map"></span></a></li>
						<li><a href=""><span class="icon fas fa-users"></span></a></li>
					</ul>
				</nav>

			<!-- Main -->
				<section id="main">

					<!-- Banner -->
						<section id="banner">
							<div class="inner">
							<div class="column">
								<h1>Welcome to DTU Map</h1>
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

					<!-- Contact -->
						<section id="contact">
							<!-- Social -->
								<div class="social column">
									<h3>About DTU Map</h3>
									<p>Here you can look at upcoming events, follow your friends around and find classrooms and shit! Welcome to the DTU Map, your new favourite website while on campus :).</p>
									<p>Click on events to see their description and add them to your calendar! </p>
									<h3>Social Media</h3>
									<ul class="icons">
										<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
										<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
										<li><a href="#" class="icon fa-instagram"><span class="label">Instagram</span></a></li>
									</ul>
								</div>

							<!-- Form -->
								<div class="column">
									<h3>Sign in with your DTU Inside account</h3>
									<form action="#" method="post">
										<div class="field half first">
											<label for="studnum">Student number</label>
											<input name="studnum" id="studnum" type="text" placeholder="Name">
										</div>
										<div class="field half">
											<label for="password">Password</label>
											<input name="password" id="password" type="password" placeholder="Password">
										</div>
										<ul class="actions">
											<li><input value="Log in" class="button" type="submit"></li>
										</ul>
									</form>
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
			<script src="/js/jquery.min.js"></script>
			<script src="/js/jquery.poptrox.min.js"></script>
			<script src="/js/jquery.scrolly.min.js"></script>
			<script src="/js/skel.min.js"></script>
			<script src="/js/util.js"></script>
			<script src="/js/main.js"></script>

	</body>
</body>
</html>