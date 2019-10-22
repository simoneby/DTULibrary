<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/custom.css">
	<link rel="stylesheet" href="/kendo-ui-core/styles/kendo.common.min.css">
    <link rel="stylesheet" href="/kendo-ui-core/styles/kendo.default.min.css">
		<!-- Scripts -->
	<script src="/js/jquery.min.js"></script>
	<script src="/js/jquery.poptrox.min.js"></script>
	<script src="/js/jquery.scrolly.min.js"></script>
	<script src="/js/skel.min.js"></script>
	<script src="/js/util.js"></script>
	<script src="/js/main.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js"></script>
	<script src="/kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="/kendo-ui-core/js/kendo.data.min.js"></script>
	<script src="/kendo-ui-core/js/kendo.dropdownlist.min.js"></script>
	<script src="/kendo-ui-core/js/kendo.multiselect.min.js"></script>
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
				<div class="field half">
				<h4><label for="user_role">User roles</label></h4>
				<select id="user_role">
				</select>
				<!--<input id="user_role" value="1" style="width: 100%;" />
				-->
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
    $(document).ready(function() {
	      require.config({
          baseUrl: "/kendo-ui-core/js/", // the path where the kendo scripts are present
          paths: {
            "jquery": "/js/jquery.min",//jquery path
          }
        });
		/*
		 require([ "jquery", "kendo.dropdownlist.min" ], function($, kendo) {
        // create DropDownList from input HTML element
        $("#user_role").kendoDropDownList({
			autoBind: false,
            dataTextField: "text",
            dataValueField: "value",
            dataSource: data,
            index: 0,
            change: onChange
        });
		var role = $("#user_role").data("kendoDropDownList");

        role.select(0);
		});
		*/
		require([ "jquery", "kendo.multiselect.min" ], function($, kendo) {
		var data = [
            { text: "Student", value: "1" },
            { text: "Faculty", value: "2" },
            { text: "Library Staff", value: "3" }
        ];
		/*
		var roleData1 = new kendo.data.DataSource({
			  data : data
			});
		*/
		var roleData1 = new kendo.data.DataSource({
			  transport: {
			    read: {
			      url: "http://localhost:8080/roles/all",
				  type: "get",
			      dataType: "json"
			    }
			  }
			});
		
		//roleData.read();
		//console.log());
        // create multiSelect from input HTML element
		roleData1.read().then(function() {
        $("#user_role").kendoMultiSelect({
          	placeHolder: "Select user roles...",
          	autoBind: true,
            dataSource: roleData1,
            dataTextField: "name",
            dataValueField: "id",
          	value : [],
          	onchange:onChange
        }).data("kendoMultiSelect");
		});

		});

        function onChange() {
            var value = $("#user_role").value();
			console.log(value);
        };
    });
    </script>
</body>
</html>