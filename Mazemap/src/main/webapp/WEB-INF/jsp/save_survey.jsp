<!-- @author s192671 -->
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
	<script src="https://kit.fontawesome.com/7510661d31.js" crossorigin="anonymous"></script>
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js" crossorigin="anonymous"></script>
	<script src="./custom_scripts/survey_script.js"></script>
	
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>DTU CampusNet</title>
</head>

<body>
	<!-- @Author s192671 -->
	<input type="hidden" id="baseUrl" name="baseUrl" value = '<%= ServerUrl.baseUrl %>' > 
	<div class="page-wrap">

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a href="index"><span class="icon fa-home"></span></a></li>
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
			<section id="friendList">
				<div id="friends_tab" class="column">
					<div class="demo-section k-content">
						<div id="tabstrip">
								<h2> Create new survey</h2>
								<form id="survey_form">
									<label for="name"> Enter survey name: </label>
									<input name="name" id="name" type="text"
										placeholder="Enter the survey name here...">
									</input>
									<label for="endDate"> Enter expiration date: </label>
									<input id="endDate" name="endDate" title="please enter date" style="width: 100%" />
									<div class="demo-section k-content wide">
										<a class="k-button k-button-icontext k-add-button" href="#"><span
												class="k-icon k-i-add"></span>Add question</a>
										<div id="questionList">
										</div>
									</div>
									<input id="save_survey" value="Save survey" class="button" type="submit">
								</form>
								<div id = "result">
									</div>
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
									<p>Start<input type="text" data-bind="value:start" data-role="numerictextbox" data-type="number" format="0" decimals="0" restrictDecimals = "true" name="start"  min="1" />
									
									 label: 
																	
									<input type="text" class="k-textbox" data-bind="value:start_label" name="start_label"  />
								</p>	
								<p>
										 End <input type="text" data-bind="value:end" data-role="numerictextbox" data-type="number" format="0" decimals="0" restrictDecimals = "true" name="end" min="1" /> </dt>
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
</body>

</html>