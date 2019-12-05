<!DOCTYPE html>
<html lang="en">
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.models.Survey"%>
<%@page import="com.helpers.ServerUrl"%>

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
    <script src="./custom_scripts/answer_survey_script.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DTU CampusNet</title>
</head>

<body>
    <input type="hidden" id="baseUrl" name="baseUrl" value='<%= ServerUrl.baseUrl %>'>
    <input type="hidden" id="survey_id" name="survey_id" value='<%= request.getParameter("survey_id") %>'>
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
            <section id="surveyList">
                <div id="survey_tab" class="column">
                    <div class="demo-section k-content">
                        <div id="tabstrip">
                            <div id="tab3">
                                <h2> Complete survey</h2>
                                <form id="survey_form">

                                    <div class="demo-section k-content wide">
                                        <div id="questionList">
                                        </div>
                                    </div>
                                    <ul class="actions">
                                        <li><input id="save_survey" value="Submit survey answers" class="button" type="submit"></li>
                                    </ul>
                                </form>
                                <div id="result"></div>
                                <script type="text/x-kendo-tmpl" id="viewTemplate">
									<div class=" k-widget">
										<p class=""> 
										 Question #:question_number# 
                                        </p>
										<p>
                                            <h3>#:question_text#</h3> 
                                            #if(question_isRange) {#
                                        Where #:question_start# represents #:question_start_label# and #:question_end# represents #:question_end_label#
                                    #}#    
                                    </p>
                                            #if(question_isRange)
                                            {#
                                            <p>
                                                <input id="slider_#:number#" data-number="#:number#" class="range_edit" data-bind="value:range_answer" title="slider" />
                                            </p>
                                            #} else {#
                                                <textarea id="text_answer_#:number#" data-number="#:number#" rows="3" cols="150"  class="k-textbox text_answer" data-bind="value:text_answer" name="text_answer">
                                                </textarea>
                                                #}#

                             </script>
                            </div>
                        </div>
                    </div>
            </section>
            <!--                                                 <div class="edit-buttons">
                                                        <a class="k-button k-edit-button" href="\\#"><span class="k-icon k-i-edit"></span></a> </div>
                                        </div>	
                                    -->
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