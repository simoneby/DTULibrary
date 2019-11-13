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
	<script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js"></script>
	<script src="kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="kendo-ui-core/js/kendo.data.min.js"></script>
	<script src="kendo-ui-core/js/kendo.dropdownlist.min.js"></script>
	<script src="kendo-ui-core/js/kendo.multiselect.min.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>DTU CampusNet</title>
</head>

<body>
	<div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="index"><span class="icon fa-home"></span></a></li>
				<li><a href="#"><span class="icon fas fa-map"></span></a></li>
				<li><a href="#"><span class="icon fas fa-users"></span></a></li>
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
				<h3>Create an account</h3>
				<form id="signup">
					<div class="field half first">
						<label for="email">Email</label>
						<input name="email" id="email" type="text" placeholder="Email...">
					</div>
					<div class="field half ">
						<label for="name">Name</label>
						<input name="name" id="name" type="text" placeholder="Name...">
					</div>
					<div class="field half">
						<label for="password">Password</label>
						<input name="password" id="password" type="password" placeholder="Password">
					</div>
					<div class="field half">
						<label for="repassword">Repeat password</label>
						<input name="repassword" id="repassword" type="password" placeholder="Repeat Password">
					</div>
					<div class="field half">
						<h4><label for="roles">User roles</label></h4>
						<select id="roles" name="roles">
						</select>
					</div>
					<ul class="actions">
						<li><input value="Sign up" class="button" type="submit"></li>
					</ul>
				</form>
				<div id="result" class="content">
				</div>
			</div>

			<!-- Footer -->
			<footer id="footer">
				<div class="copyright">
					&copy; Group E: <a href="https://se2-webapp05.compute.dtu.dk/">DTU Snapmap</a>. Images: <a
						href="http://inside.dtu.dk">DTU Inside</a>.
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
		$(document).ready(function () {
			require.config({
				baseUrl: "/kendo-ui-core/js/", // the path where the kendo scripts are present
				paths: {
					"jquery": "/js/jquery.min",//jquery path
				}
			});

			var roleData1 = [];
			require(["jquery", "kendo.multiselect.min"],
				function ($, kendo) {
					roleData1 = new kendo.data.DataSource({
						transport: {
							read: {
								url: "https://se2-webapp05.compute.dtu.dk:8080/roles/all",
								type: "get",
								dataType: "json"
							}
						}
					});

					// create multiSelect from input HTML element
					roleData1.read().then(function () {
						$("#roles").kendoMultiSelect({
							placeHolder: "Select user roles...",
							autoBind: true,
							dataSource: roleData1,
							dataTextField: "name",
							dataValueField: "id",
							value: []
						}).data("kendoMultiSelect");
					});
				});

			const handleFormSubmit = event => {

				// Stop the form from submitting since we’re handling that with AJAX.
				event.preventDefault();
				submitForm();
			}
			function submitForm() {
				var user = {};
				$("#signup").find("input, textarea, select").each(function () {
					var inputType = this.tagName.toUpperCase() === "INPUT" && this.type.toUpperCase();
					if (inputType !== "BUTTON" && inputType !== "SUBMIT") {
						if (this.name === "roles") {
							var user_roles = [];
							$("#roles option:selected").each(function () {
								var optionValue = $(this).val();
								var optionText = $(this).text();
								user_roles.push({ id: optionValue, name: optionText });
								console.log("optionText", optionText,optionValue);
							});
							user[this.name] = user_roles;
						}
						else {
							user[this.name] = $(this).val();
							console.log(this.name);
						}
					}
				});

				console.log(user);
				$.ajax({
					contentType: 'application/json',
					data: JSON.stringify(user),
					dataType: 'json',
					success: function (data, status) {
						$("#result").text("<p>"+data+"</p>");
					},
					error: function () {
						console.log("Stuff happened");
					},
					processData: false,
					type: 'POST',
					url: 'https://se2-webapp05.compute.dtu.dk:8080/signup'
				});
				// $.post("http://localhost:8080/signup",
				// 	{ user : user},
				// 	function (data, status) {
				// 		alert("Data: " + data + "\nStatus: " + status);
				// 	}
				// 	);
			};
			const form = document.getElementById('signup');
			form.addEventListener('submit', handleFormSubmit);
		});

	</script>
</body>

</html>