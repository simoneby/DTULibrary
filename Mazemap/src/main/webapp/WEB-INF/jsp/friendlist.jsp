<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<link rel="stylesheet" type="text/css" href="css/custom.css">
	<link rel="stylesheet" href="kendo-ui-core/styles/kendo.common.min.css">
	<link rel="stylesheet" href="kendo-ui-core/styles/kendo.default.min.css">
	<!-- Scripts -->
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.poptrox.min.js"></script>
	<script src="js/jquery.scrolly.min.js"></script>
	<script src="js/skel.min.js"></script>
	<script src="js/util.js"></script>
	<script src="js/main.js"></script>
	<script src="kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="kendo-ui-core/js/kendo.listview.min.js"></script>
	<script src="kendo-ui-core/js/kendo.pager.min.js"></script>
	<script src="kendo-ui-core/js/kendo.data.min.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>DTU CampusNet</title>
</head>

<body>
	<div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="index" class="active"><span class="icon fa-home"></span></a></li>
				<li><a href=""><span class="icon fas fa-map"></span></a></li>
				<li><a href="friendlist"><span class="icon fas fa-users"></span></a></li>
				<li><a href="login"><span class="icon fas fa-sign-in"></span></a></li>
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
			<section id="friendList">
				<div class="inner column">
					<h2> Friend list: </h2>
					<div class="demo-section k-content wide">
						<div id="listView">
							<table></table>
						</div>
						<div id="pager" class="k-pager-wrap"></div>
					</div>

					<script type="text/x-kendo-template" id="template">
						<tr><td colspan="3">
								<h3>#:name#</h3><td> </tr>
							<tr>	
								<td colspan="2"><p>#:email#</p>
								</td>
								<td colspan="1">
									<button>See location </button>
								</td>
								</tr>
							</div>
						</script>
				</div>
				<div class="resp-container">
					<form id="friendForm">
						<label for="friendEmail"> Add to your friend-list: </label>
						<input name="friendEmail" id="friendEmail" type="text" placeholder="Type friend's email here...">
						</input>
						<ul class="actions">
							<li><input value="Add friend" class="button" type="submit"></li>
						</ul>
					</form>
				</div>
			</section>

			<!-- Contact -->
			<section id="contact">
				<!-- Social -->
				<div class="social column">
					<h3>About DTU Hub</h3>
					<p>Here you can look at upcoming events, follow your friends around and find classrooms and shit!
						Welcome to
						the DTU Hub, your new favourite website while on campus :).</p>
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
					&copy; Group E: <a href="https://se2-webapp05.compute.dtu.dk/">DTU Snapmap</a>. Images: <a
						href="http://inside.dtu.dk">DTU Inside</a>.
				</div>
			</footer>
		</section>
	</div>

	<script>
		$(document).ready(function () {
			require.config({
				baseUrl: "/kendo-ui-core/js/", // the path where the kendo scripts are present
				paths: {
					"jquery": "/js/jquery.min",//jquery path
				}
			});
			loadFriendlist();
			function loadFriendlist() {
				var friends = [];
				require(["jquery", "kendo.pager.min","kendo.listview.min"],
					function ($, kendo) {
						friends = new kendo.data.DataSource({
							transport: {
								read: {
									url: "http://localhost:8080/friends/all",
									type: "get",
									dataType: "json"
								}
							}
						});

						// create multiSelect from input HTML element
						friends.read().then(function () {
							$("#pager").kendoPager({
								dataSource: friends
							});

							$("#listView").kendoListView({
								dataSource: friends,
								template: kendo.template($("#template").html())
							});
						});

					});
			}
			const handleFormSubmit = event => {

				// Stop the form from submitting since we’re handling that with AJAX.
				event.preventDefault();
				submitForm();
			}
			function submitForm() {
				var data = {};
				data.friendEmail = $("#friendEmail").val();
				console.log(data.friendEmail);
				$.ajax({
					contentType: 'application/json',
					data: data.friendEmail,
					dataType: 'json',
					success: function (data, status) {
						console.log("Data: " + data + "\nStatus: " + status);
						loadFriendlist();
					},
					error: function () {
						console.log("Device control failed");
					},
					processData: false,
					type: 'POST',
					url: 'http://localhost:8080/friends/add'
				});
				// $.post("http://localhost:8080/signup",
				// 	{ user : user},
				// 	function (data, status) {
				// 		alert("Data: " + data + "\nStatus: " + status);
				// 	}
				// 	);
			};
			const form = document.getElementById('friendForm');
			form.addEventListener('submit', handleFormSubmit);
		});

	</script>


</body>

</html>