<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" type="text/css" href="./css/custom.css">
	<link rel="stylesheet" href="./kendo-ui-core/styles/kendo.common.min.css">
    <link rel="stylesheet" href="./kendo-ui-core/styles/kendo.default.min.css">
		<!-- Scripts -->
	<script src="./js/jquery.min.js"></script>
	<script src="./js/jquery.poptrox.min.js"></script>
	<script src="./js/jquery.scrolly.min.js"></script>
	<script src="./js/skel.min.js"></script>
	<script src="./js/util.js"></script>
	<script src="./js/main.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js" crossorigin="anonymous"></script>
	<script src="./kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.data.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.dropdownlist.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.multiselect.min.js"></script>
	<script src="https://kit.fontawesome.com/7510661d31.js" crossorigin="anonymous"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DTU CampusNet</title>
</head>
<body>
	<!-- s192671 -->
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
			<section id="banner" style=" min-height: 25vh; height: 40vh">
					<div class="column inner">
						<img alt="DTU logo" src="images/dtu_logo_white.png" class="logo">
					</div>
			</section>

		<div class="column">
			<h3>Sign in with your DTU Inside account</h3>
			<form id="sign_in" method="POST" action="https://se2-webapp05.compute.dtu.dk:8080/signin">
				<div class="field half first">
					<label for="email">Student email </label>
					<input name="email" id="email" type="text" placeholder="Name">
				</div>
				<div class="field half">
					<label for="password">Password</label>
					<input name="password" id="password" type="password" placeholder="Password">
				</div>
				
				<ul class="actions">
					<li><input value="Log in" class="button" type="submit"></li>
				</ul>
			</form>
			<div> Don't have an account? 
				<a href="register">Click here to register for our application</a>
			 </div>
			<div id="#result" class="column inner"></div>
		</div>
			<!-- Footer -->
			<footer id="footer">
				<div class="copyright">
					&copy; Group E: <a href="https://se2-webapp05.compute.dtu.dk/">DTU Snapmap</a>. Images: <a href="http://inside.dtu.dk">DTU Inside</a>.
				</div>
			</footer>
		</section>
	</div>


	 <style>
            #cap {
                width: 242px;
                height: 225px;
                margin: 20px auto;
                background-image: url('../content/web/dropdownlist/cap.png');
                background-repeat: no-repeat;
                background-color: transparent;
            }
            .black-cap {
                background-position: 0 0;
            }
            .grey-cap {
                background-position: 0 -225px;
            }
            .orange-cap {
                background-position: 0 -450px;
            }
    </style>
	<script>
		
    </script>
</body>
</html>