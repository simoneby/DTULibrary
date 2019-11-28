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
									<input id="dateinput" title="please enter date" style="width: 100%" />

									<ul class="actions">
										<li><input value="save survey" class="button" type="submit"></li>
									</ul>
								<div class="demo-section k-content wide">

									<div id="questionList">
										<table></table>
									</div>
									<a class="k-button k-button-icontext k-add-button" href="#"><span
											class="k-icon k-i-add"></span>Add new record</a>
								</div>
							</form>
								<script type="text/x-kendo-template" id="viewTemplate">
										<tr> <td> Question #:number# </td> 
										<ul class="question_Type">
												<li>
													<input type="radio" name="text_q" id="text_q" class="k-radio" checked="">
													<label class="k-radio-label" for="text_q">Text</label>
												</li>
												<li>
													<input type="radio" name="range_q" id="range_q" class="k-radio">
													<label class="k-radio-label" for="range_q">Range</label>
												</li>
										</ul>	</tr>
										<tr><td colspan="4">
											<h3>#:name#</h3><td> </tr>
										<tr>	
											    <td>
														<a class="k-button k-edit-button" href="\\#"><span class="k-icon k-i-edit"></span></a>
												</td>
											<td colspan="1"> 
												<button class="editBtn" data-number="#:number#" onclick="handleEdit(this)"> Edit question </button>
											</td>
										</tr>
                             </script>
								<script type="text/x-kendo-template" id="editTemplate">
								<tr> <td> Question #:number# </td> 
									<ul class="question_Type">
											<li>
												<input type="radio" name="text_q" id="text_q" class="k-radio" checked="#:!isRange#">
												<label class="k-radio-label" for="text_q">Text</label>
											</li>
											<li>
												<input type="radio" name="range_q" id="range_q" class="k-radio" checked="#:isRange#">
												<label class="k-radio-label" for="range_q">Range</label>
											</li>
									</ul>
								</tr>
                                <tr>
									<td colspan="4">
                                    <input type="text" class="k-textbox" data-bind="value:name" name="question_name" required="required" validationMessage="required" />
                                    <td> 
								</tr>
                                <tr class="edit-buttons">
                                     <td>
                                            <a class="k-button k-update-button" href="\\#"><span class="k-icon k-i-check"></span></a>
									</td>
										<td>
                                            <a class="k-button k-cancel-button" href="\\#"><span class="k-icon k-i-cancel"></span></a>
                                        </td>
                                    </tr>
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
		var listView = {};
		$(document).ready(function () {
			require.config({
				baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
				paths: {
					"jquery": "./jquery.min",//jquery path
				}
			});
			require(["jquery", "kendo.pager.min", "kendo.listview.min"],
				function ($, kendo) {
					$("#dateinput").kendoDateInput();
					console.log("sth1");
					var questionList = [{ id: 1, name: "some stuff here", number: 1 }];
					var questions = new kendo.data.DataSource({
						data: questionList,
						schema: {
							model: {
								id: "id",
								fields: {
									id: { type: "number", editable: false, nullable: true },
									name: { type: "string" },
									number: { type: "number" },
									isRange: {type: "boolean"}
								}
							}
						}
					});
					//console.log("sth1");
					listView = $("#questionList").kendoListView({
						dataSource: questions,
						template: kendo.template($("#viewTemplate").html()),
						editTemplate: kendo.template($("#editTemplate").html()),
						edit: function(e) {
						console.log("Editing of item with id " + e.model.id);
						}
					}).data("kendoListView");
				});
			$(".k-add-button").click(function (e) {
				var currentQuestionNumber = questionList.length + 1;
				questionList.add(
					{
						name: "Question...",
						number: currentQuestionNumber,
					});
				listView.add();
				e.preventDefault();
				console.log("add event activated");
			});

			$(".k-edit-button").click(function (e) {
				// get a reference to the ListView widget
				var lv = $("#questionList").data("kendoListView");
				// edit the first ListView item
				lv.edit(e.get);
			});
		});
		function handleEdit(element) {
				// get a reference to the ListView widget
				var lv = $("#questionList").data("kendoListView");
				var rowNumber = element.getAttribute("data-number");
				console.log(rowNumber);
				var currentRow = lv.element.children()[parseInt(rowNumber)-1];
				console.log(currentRow);
				// edit the first ListView item
				lv.edit(currentRow);
			};
	</script>
</body>

</html>