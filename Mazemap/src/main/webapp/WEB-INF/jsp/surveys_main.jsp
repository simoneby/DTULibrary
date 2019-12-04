<!-- @author s192671 -->
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
    <script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.1/require.js"></script>
    <script src="./custom_scripts/survey_main_script.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DTU CampusNet</title>
</head>

<body>
    <input type="hidden" id="baseUrl" name="baseUrl" value='<%= ServerUrl.baseUrl %>'>
    <div class="page-wrap">

        <!-- Nav -->
        <nav id="nav">
            <ul>
                <li><a href="index" class="active"><span class="icon fa-home"></span></a></li>
                <li><a href="friendlist"><span class="icon fas fa-users"></span></a></li>
                <li><a href="survey_main"><i class="fas fa-poll-h"></i></a></li>
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
            <section>
                <div id="active_surveys" class="column">
                    <div class="demo-section k-content">
                        <div id="tabstrip">
                            <ul>
                                <li> Active survey list </li>
                                <li> Surveys you created</li>
                            </ul>
                            <div>
                                <table id="survey_list_to_complete"></table>
                            </div>
                            <div>
                                <table id="survey_list_created">
                                </table>
                                <div>
                                    <li><a href="save_survey"><i class="fas fa-external-link-square-alt"> Click here to
                                                create new survey... </i></a></li>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
            <script type="text/x-kendo-tmpl" id="template1">
									<tr class=" k-widget">
										<th> 
										 Survey name:  #:name# 
                                        </th>
                                    <td><a href="complete_survey?survey_id=#:id#"><i class="fas fa-external-link-square-alt"> Complete survey... </i></a>  
                                    </td> </tr>
                                        <tr> <td>
                                         Created on: #:startDate.toDateString()#; </td> <td> Expires on #:endDate.toDateString()#</td> </tr>

                </script>
            <script type="text/x-kendo-tmpl" id="template2">
                    <tr class=" k-widget">
                        <th> 
                         Survey name:  #:name# 
                        </th>
                    <td><a href="view_survey_results?survey_id=#:id#"><i class="fas fa-external-link-square-alt"> View survey results... </i></a>  
                    </td> </tr>
                        <tr> <td>
                         Created on: #:startDate.toDateString()#; </td> <td> Expires on #:endDate.toDateString()#</td> </tr>

</script>
            <!-- Contact -->
            <section id="contact">
                <!-- Social -->
                <div class="social">

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