<!DOCTYPE html>
<html lang="en">
		<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
		<%@page import="com.models.User"%>
		<%@page import="com.Helpers.ServerUrl"%>
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
	<script src="./kendo-ui-core/js/kendo.core.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.listview.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.pager.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.data.min.js"></script>
	<script src="./kendo-ui-core/js/kendo.tabstrip.min.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js"></script>
	<script src="./custom_scripts/survey_script.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>DTU CampusNet</title>
</head>

<body>
	<input type="hidden" id="baseUrl" name="baseUrl" value = '<%= ServerUrl.baseUrl %>' > 
	<div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="index" class="active"><span class="icon fa-home"></span></a></li>
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
			<section id="friendList">
				<div id="friends_tab" class="column">
					<div class="demo-section k-content">
						<div id="tabstrip">
							<div id="tab3">
								<h2> Create new survey</h2>
								<form id="survey_form">
									<label for="name"> Enter survey name: </label>
									<input name="name" id="name" type="text"
										placeholder="Enter the survey name here...">
									</input>
									<label for="expiration_date"> Enter expiration date: </label>
									<input id="expiration_date" name="expiration_date" title="please enter date" style="width: 100%" />
									<div class="demo-section k-content wide">
										<a class="k-button k-button-icontext k-add-button" href="#"><span
												class="k-icon k-i-add"></span>Add question</a>
										<div id="questionList">
										</div>
									</div>
									<ul class="actions">
										<li><input value="save survey" class="button" type="submit"></li>
									</ul>
								</form>
								<script type="text/x-kendo-tmpl" id="viewTemplate">
									<div class=" k-widget">
										<p> 
										 Question #:number# 
										
										Type: 
										<input class="view_q_type" value="#:type#">
										
									</p>
										#if(isRange)
										{#
										<p>
											 Start #:start# label: #:start_label# 
										</p>
										<p>
												 End #:end# label: #:end_label# 
												</p>
										#}#
										<p>
											<h3>#:text#</h3> </p>
										</div>
									<div class="edit-buttons">
											<a class="k-button k-edit-button" href="\\#"><span class="k-icon k-i-edit"></span></a>
											<a class="k-button k-delete-button" href="\\#"><span class="k-icon k-i-close"></span></a>
									</div>
									
                             </script>
								<script type="text/x-kendo-tmpl" id="editTemplate">
						<div class="product-view k-widget">
								<dl> <dt> Question #:number# </dt> 
									<dt><input id="ddl_#:number#" class="view_q_type type_in_edit" data-bind="value:type"> </dt>
									<dd> Text: </dd>
									<dt>
                                    <input type="text" class="k-textbox" data-bind="value:text" name="question_name" required="required" validationMessage="Please enter the text of the question..." />
                                    <dt> 
									</dl>
								<div class="range_edit">
									<p>Start<input type="text" data-bind="value:start" data-role="numerictextbox" data-type="number" name="start"  min="1" />
									
									 label: 
																	
									<input type="text" class="k-textbox" data-bind="value:start_label" name="start_label"  />
								</p>	
								<p>
										 End <input type="text" data-bind="value:end" data-role="numerictextbox" data-type="number" name="end" min="1" /> </dt>
												label: 
										
										<input type="text" class="k-textbox" data-bind="value:end_label" name="end_label"  />
								</p>
										</div>
										
                                <div class="edit-buttons">
                                    <a class="k-button k-update-button" href="\\#"><span class="k-icon k-i-check"></span></a>
                                    <a class="k-button k-cancel-button" href="\\#"><span class="k-icon k-i-cancel"></span></a>
								</div>
						</div>
                     			</script>

							</div>
						</div>
					</div>
			</section>

			<!-- Contact -->
			<section id="contact">
				<!-- Social -->
				<div class="social column">

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
		var baseUrl ;
		var listView = {};
		var questions = {};
		var q_type = [
			{ text: "Text", value: 0 },
			{ text: "Range", value: 1 },
		];
		$(document).ready(function () {
			require.config({
				baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
				paths: {
					"jquery": "./jquery.min",//jquery path
				}
			});
			require(["jquery", "kendo.pager.min", "kendo.listview.min", "kendo.data.min", "kendo.dropdownlist.min"],
				function ($, kendo) {
					$("#expiration_date").kendoDateInput();
					var questionList = [];
					 questions = new kendo.data.DataSource({
						data: questionList,
						schema: {
							model: {
								id: "number",
								fields: {
									text: { type: "string" },
									number: { type: "number" },
									type: { type: "number" },
									start: { type: "number" },
									end: { type: "number" },
									start_label: { type: "string" },
									end_label: { type: "string" },
									isRange: { type: "boolean" }

								}
							},
						},
						sort: { field: "number", dir: "asc" },
						change: function (e) {
							console.log(e.model);
							this.data().forEach(element => {
								element.isRange = element.type == 1;
								console.log("isRange" + element.number + "value" + element.isRange);
							});
							//	  listView.refresh();
						}
					});
					//console.log("sth1");
					listView = $("#questionList").kendoListView({
						dataSource: questions,
						editale: true,
						template: kendo.template($("#viewTemplate").html()),
						editTemplate: kendo.template($("#editTemplate").html()),
						edit: function (e) {
							if (e.model.number == 0) {
								e.model.number = this.dataSource.data().length;
								console.log(e.model.isRange);
							}
						},
						dataBound: function () {
							$(".view_q_type").each(function (index) {
								$(this).kendoDropDownList({
									dataTextField: "text",
									dataValueField: "value",
									dataSource: q_type,
									enable: false
								});
							});
						}
					}).data("kendoListView");
					// create DropDownList from input HTML element
					$(".view_q_type").each(function (index) {
						$(this).kendoDropDownList({
							dataTextField: "text",
							dataValueField: "value",
							dataSource: q_type,
							enable: false
						});
					});
				});
			$(".k-add-button").click(function (e) {
				var currentQuestionNumber = questionList.length + 1;
				//e.model.number = currentQuestionNumber;
				//e.model.type = 1;
				console.log("adding");
				listView.add();
				$(".range_edit").hide();
				$(".view_q_type, .type_in_Edit").each(function (index) {
					$(this).kendoDropDownList({
						dataTextField: "text",
						dataValueField: "value",
						dataSource: q_type,
						enable: true,
						value: 1,
						change: function (e) {
							var value = this.value();
							if (value == 1) {
								$(".range_edit").show();
							}
							else {
								$(".range_edit").hide();
							}
						}
					});
				});
				e.preventDefault();
				//console.log("add event activated");
			});
			const handleFormSubmit = event => {
				// Stop the form from submitting since we’re handling that with AJAX.
				event.preventDefault();
				submitForm();
			}
			function submitForm() {
				baseUrl = $("#baseUrl").val();
				var survey = {};
				$("#survey_form").find("input, textarea").each(function () {
					var inputType = this.tagName.toUpperCase() === "INPUT" && this.type.toUpperCase();
					if (inputType !== "BUTTON" && inputType !== "SUBMIT") {
						// if (this.name === "roles") {
						// 	var user_roles = [];
						// 	$("#roles option:selected").each(function () {
						// 		var optionValue = $(this).val();
						// 		var optionText = $(this).text();
						// 		user_roles.push({ id: optionValue, name: optionText });
						// 		console.log("optionText", optionText,optionValue);
						// 	});
						// 	user[this.name] = user_roles;
						// }
						// else {
						survey[this.name] = $(this).val();
						console.log(this.name);
						// }
					}
				});
				survey.questions = questions.data(); 
				console.log(survey);
				$.ajax({
					contentType: 'application/json',
					data: JSON.stringify(survey),
					dataType: 'json',
					success: function (data, status) {
						$("#result").text("<p>" + data + "</p>");
					},
					error: function () {
						console.log("Stuff happened");
					},
					processData: false,
					type: 'POST',
					url: baseUrl + '/survey/save'
				});
				// $.post("http://localhost:8080/signup",
				// 	{ user : user},
				// 	function (data, status) {
				// 		alert("Data: " + data + "\nStatus: " + status);
				// 	}
				// 	);
			};
			const form = document.getElementById('survey_form');
			form.addEventListener('submit', handleFormSubmit);
		});
	</script>
</body>

</html>