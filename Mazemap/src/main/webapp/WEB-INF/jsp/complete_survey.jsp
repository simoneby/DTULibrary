<!DOCTYPE html>
<html lang="en">
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.models.Survey"%>
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
    <input type="hidden" id="baseUrl" name="baseUrl" value='<%= ServerUrl.baseUrl %>'>
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
                                        <li><input value="save survey" class="button" type="submit"></li>
                                    </ul>
                                </form>
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

    <script>

        var baseUrl;
        var listView = {};
        var questions = {};
        var questionList = [
            {
                number: 1, text_answer: "", range_answer: 0, question: {
                    number: 1,
                    type: 0,
                    text: "What do you think of this form?",
                    isRange: false
                }
            },
            {
                number: 2, text_answer: "", range_answer: 1, question: {
                    number: 2,
                    type: 1,
                    text: "Rate this form on a scale of 1 to 10.",
                    start: 1,
                    end: 10,
                    start_label: "Bad",
                    end_label: "Awesome",
                    isRange: true
                }
            }
        ];
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
            require(["jquery", "kendo.pager.min", "kendo.listview.min", "kendo.data.min", "kendo.dropdownlist.min", "kendo.slider.min"],
                function ($, kendo) {
                    //$("#expiration_date").kendoDateInput();
                    //var questionList = [];
                    questions = new kendo.data.DataSource({
                        data: questionList,
                        schema: {
                            model: {
                                id: "number",
                                fields: {
                                    number: { type: "number" },
                                    text_answer: { type: "string", editable: true },
                                    range_answer: { type: "number", editable: true },
                                    question_isRange: { type: "boolean", from: "question.isRange" },
                                    question_number: { type: "string", from: "question.number" },
                                    question_type: { type: "number", from: "question.type" },
                                    question_text: { type: "string", from: "question.text" },
                                    question_start: { type: "number", from: "question.start" },
                                    question_start_label: { type: "string", from: "question.start_label" },
                                    question_end: { type: "number", from: "question.end" },
                                    question_end_label: { type: "string", from: "question.end_label" },
                                }
                            },
                        },
                        sort: { field: "number", dir: "asc" },
                        change: function (e) {
                            console.log(e.model);
                            attachEvents();
                            var lv = $("#questionList").data("kendoListView");
                            // refreshes the ListView
                            lv.refresh();
                            this.data().forEach(element => {
                                // element.isRange = element.type == 1;
                                // console.log("isRange" + element.number + "value" + element.isRange);
                            });
                        }
                    });
                    //console.log("sth1");
                    listView = $("#questionList").kendoListView({
                        dataSource: questions,
                        editable: true,
                        //editTemplate: kendo.template($("#editTemplate").html()),
                        template: kendo.template($("#viewTemplate").html()),
                        dataBound: function () {
                            console.log("databound happening");
                            this.dataSource.data().forEach(element => {
                                console.log(element);
                                if (element.question_isRange) {
                                    var slider = $("#slider_" + element.number).kendoSlider({
                                        increaseButtonTitle: "Right",
                                        decreaseButtonTitle: "Left",
                                        min: element.start,
                                        max: element.end,
                                        smallStep: 1,
                                        largeStep: 1
                                    }).data("kendoSlider");
                                }
                            });
                            attachEvents();
                            // $(".view_q_type").each(function (index) {
                            //     $(this).kendoDropDownList({
                            //         dataTextField: "text",
                            //         dataValueField: "value",
                            //         dataSource: q_type,
                            //     });
                            // });
                        }
                    }).data("kendoListView");
                });

            const rangeChangeEvent = function (element) {
                var nr = element.getAttribute("data-number");
                var value = $(element).val();
                rangeAnswer(nr, value);
            };
            function rangeAnswer(number, value) {
                console.log("nr:" + number + " , value:" + value);
                questions.data().forEach(element => {
                    if (element.number == number) {
                        element.rangeAnswer = value;
                        console.log("yay1");
                    }
                });
                // var lv = $("#questionList").data("kendoListView");
                // // refreshes the ListView
                // lv.refresh();
            };
            const textChangeEvent = function (element) {
                console.log(element);
                console.log($(this));
                var nr = element.getAttribute("data-number");
                var value = $(element).val();
                textAnswer(nr, value);
            };
            function textAnswer(number, value) {
                console.log("nr:" + number + " , value:" + value);
                questions.data().forEach(element => {
                    if (element.number == number) {
                        element.rangeAnswer = value;
                        console.log("yay1");
                    }
                });
                // var lv = $("#questionList").data("kendoListView");
                // // refreshes the ListView
                // lv.refresh();
            };
            const handleFormSubmit = event => {
                // Stop the form from submitting since we’re handling that with AJAX.
                event.preventDefault();
                submitForm();
            };
            function submitForm() {
                baseUrl = $("#baseUrl").val();
                var survey = {};

               
                $("#survey_form").find("input, textarea").each(function () {
                    var inputType = this.tagName.toUpperCase() === "INPUT" && this.type.toUpperCase();
                    if (inputType !== "BUTTON" && inputType !== "SUBMIT") {
                        if($(this).hasClass("range_edit"))
                        {
                            var q_number = $(this).attr("data-number");
                            var question = questions.get(q_number);
                            question.range_answer = $(this).val();
                        }
                        if($(this).hasClass("text_answer"))
                        {
                            var q_number = $(this).attr("data-number");
                            var question = questions.get(q_number);
                            question.text_answer = $(this).val();
                        }
                    }
                });
                var lv = $("#questionList").data("kendoListView");
                console.log(lv);
                return;
                //survey.questions = questions.data();
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
                    url: baseUrl + '/survey/answer/save'
                });
            };
            const form = document.getElementById('survey_form');
            form.addEventListener('submit', handleFormSubmit);
            function attachEvents() {
                var ranges = document.getElementsByClassName("range_edit");
                $(".range_edit").each(function () {
                    this.addEventListener('change', rangeChangeEvent(this));
                });
                var textAnswers = document.getElementsByClassName("text_answer");
                $(".text_answer").each(function () {
                    this.addEventListener('change', textChangeEvent(this));
                });
            };
        });
    </script>
</body>

</html>