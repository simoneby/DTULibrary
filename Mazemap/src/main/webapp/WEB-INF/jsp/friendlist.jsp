<!DOCTYPE html>
<html lang="en">
	<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
	<%@page import="com.models.User"%>
	<%@page import="com.helpers.ServerUrl"%>
	<!-- @author s192671 -->
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
	<script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js" crossorigin="anonymous"></script>
	<script src="./custom_scripts/friendlist_script.js"></script>
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
				<li><a href="index" ><span class="icon fa-home"></span></a></li>
				<% if(session.getAttribute("user")!=null) {%>
				<li><a href="friendlist" class="active"><span class="icon fas fa-users"></span></a></li>
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
							<ul>
								<li class="k-state-active">
									Friends
								</li>
								<li>
									Requests sent
								</li>
								<li>
									Requests received
								</li>
							</ul>
							<div id="tab1">
								<div class="demo-section k-content wide">
									<div id="listView1">
										<table></table>
									</div>
									<div id="pager1" class="k-pager-wrap"></div>
								</div>

								<script type="text/x-kendo-template" id="template1">
									<div class="k-widget">
														<tr>

															<td colspan="4">
																<h3>#:name#</h3>
															</td> 
														</tr>
														<tr>
															<td colspan="2">#:email#
																</td>
														<td colspan="1" class="edit-buttons">
															<a class="k-button k-delete-button" href="\\#"><span class="k-icon k-i-close"></span></a>
														</td>
													</tr>
													</div>
									</script>
							</div>
							<div id="tab2">
									<h2> List of people you have sent a friend request to: </h2>
									<div class="demo-section k-content wide">
										<div id="listView2">
											<table></table>
										</div>

										<div id="pager2" class="k-pager-wrap"></div>

									</div>

									<script type="text/x-kendo-template" id="template2">

																<tr>
																	<td colspan="3">
																		<h3>#:name#</h3><td> </tr>
																<tr>
																		<td colspan="2"><p>#:email#</p>
																		</td>
																	</tr>
																</script>
							<div>
								<form id="friendForm">
									<label for="friendEmail"> Add to your friend-list: </label>
									<input name="friendEmail" id="friendEmail" type="text"
										placeholder="Type friend's email here...">
									</input>
									<ul class="actions">
										<li><input value="Add friend" class="button" type="submit"></li>
									</ul>
								</form>
								<div id="addFriendResult">
								</div>
							</div>
						</div>
						<div id="tab3">
								<h2> List of people who have sent you a friend request: </h2>
								<div class="demo-section k-content wide">
									<div id="listView3">
										<table>
										</table>
									</div>
									<div id="pager3" class="k-pager-wrap"></div>
									<div id="acceptRequestResult"></div>
									<div id="rejectRequestResult"></div>
								</div>

								<script type="text/x-kendo-template" id="template3">
										<tr><td colspan="4">
											<h3>#:name#</h3><td> </tr>
										<tr>	
											<td colspan="2"><p>#:email#</p>
											</td>
											<td colspan="1"> 
												<button class="acceptReqButton" data-email="#:email#" onclick="acceptRequest(this)">Accept request </button>
											</td>
											<td colspan="1">
												<button class="rejectReqButton" data-email="#:email#" onclick="rejectRequest(this)">Reject request </button>
											</td>
										</tr>
									</script>
							</div>
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
