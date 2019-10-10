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
				<li><a href="index"><span class="icon fa-home"></span></a></li>
				<li><a href=""><span class="icon fas fa-map"></span></a></li>
				<li><a href="users"><span class="icon fas fa-users"></span></a></li>
				<li><a href="#" class="active"><span class="icon fas fa-sign-in"></span></a></li>
			</ul>
		</nav>

		<!-- Main -->
		<section id="main">
			<!-- Banner -->
			<section id="banner" style=" min-height: 25vh; height: 40vh">
					<div class="column inner">
						<img alt="DTU logo" src="images/dtu_logo_white.png" class="logo">
					</div>
			</section>

		<div class="column">
			<h3>Sign in with your DTU Inside account</h3>
			<form action="login" method="post">
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
</html>