//@author s192671
var survey = {};
var baseUrl;
var listView = {};
var survey_answer = {};
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
    var survey_id = $("#survey_id").val();
    baseUrl = $("#baseUrl").val();
    require.config({
        baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "./jquery.min",//jquery path
        }
    });
    require(["jquery", "kendo.pager.min", "kendo.listview.min", "kendo.data.min", "kendo.dropdownlist.min", "kendo.slider.min"],
        function ($, kendo) {

            survey_answer = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/survey/current/answer?survey_id=" + survey_id,
                        type: "get",
                        dataType: "json"
                    },
                },
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            id: { type: "number" },
                            name: { type: "string" },
                            expiration_date: {type: "date", from: "survey.expiration_date"},
                            survey_id:{type:"number",from: "survey.id"},
                            questionAnswers: {}
                        }
                    }
                }
            });
            survey_answer.read().then(function () {
                survey = survey_answer.at(0);
                loadQuestionsAndList(survey.questionAnswers);
            });
            //$("#expiration_date").kendoDateInput();
            //var questionList = [];
        });
    function loadQuestionsAndList(qlist) {
        questions = new kendo.data.DataSource({
            data: qlist,
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
              
                //attachEvents();
                var lv = $("#questionList").data("kendoListView");
                // refreshes the ListView
                lv.refresh();
                this.data().forEach(element => {
                    
                });
            }
        });
     
        listView = $("#questionList").kendoListView({
            dataSource: questions,
            editable: true,
            template: kendo.template($("#viewTemplate").html()),
            dataBound: function () {
               
                this.dataSource.data().forEach(element => {
                   
                    if (element.question_isRange) {
                        var slider = $("#slider_" + element.number).kendoSlider({
                            increaseButtonTitle: "Right",
                            decreaseButtonTitle: "Left",
                            min: element.question_start,
                            max: element.question_end,
                            smallStep: 1,
                            largeStep: 1
                        }).data("kendoSlider");
                    }
                });
            }
        }).data("kendoListView");
    };
    const handleFormSubmit = event => {
        // Stop the form from submitting since we’re handling that with AJAX.
        event.preventDefault();
        submitForm();
    };
    function submitForm() {
        baseUrl = $("#baseUrl").val();


        $("#survey_form").find("input, textarea").each(function () {
            var inputType = this.tagName.toUpperCase() === "INPUT" && this.type.toUpperCase();
            if (inputType !== "BUTTON" && inputType !== "SUBMIT") {
                if ($(this).hasClass("range_edit")) {
                    var q_number = $(this).attr("data-number");
                    var question = questions.get(q_number);
                    question.range_answer = $(this).val();
                }
                if ($(this).hasClass("text_answer")) {
                    var q_number = $(this).attr("data-number");
                    var question = questions.get(q_number);
                    question.text_answer = $(this).val();
                }
            }
        });
        $.ajax({
            contentType: 'application/json',
            data: JSON.stringify(survey),
            dataType: 'json',
            success: function (data, status) {
                $("#result").html("<p> <b>" + data.message + "</b></p>");
                $("#save_survey").prop('disabled',true);
            },
            error: function () {
                console.log("Request failed");
            },
            processData: false,
            type: 'POST',
            url: baseUrl + '/survey/answer/save'
        });
    };
    const form = document.getElementById('survey_form');
    form.addEventListener('submit', handleFormSubmit);
});
