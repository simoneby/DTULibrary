//@author s192671
var survey = {};
var baseUrl;
var listView = {};
var survey_answer = {};
var questions = {};
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
                        url: baseUrl + "/survey/current?survey_id=" + survey_id,
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
                            endDate: { type: "date" },
                            startDate: { type: "date" },
                            questions: {}
                        }
                    }
                }
            });
            survey_answer.read().then(function () {
                survey = survey_answer.at(0);
                $("#survey_name").html(survey.name);
                $("#survey_info").html("Survey was created on " + survey.startDate.toDateString() + " and expires on " + survey.endDate.toDateString());
                loadQuestionsAndList(survey.questions);
            });
            //$("#expiration_date").kendoDateInput();
            //var questionList = [];
        });
    function loadQuestionsAndList(qlist) {
        questions = new kendo.data.DataSource({
            data: qlist,
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: { type: "number" },
                        number: { type: "number" },
                        isRange: { type: "boolean" },
                        type: { type: "number" },
                        text: { type: "string" },
                        start: { type: "number" },
                        start_label: { type: "string" },
                        end: { type: "number" },
                        end_label: { type: "string" },
                    }
                },
            },
            sort: { field: "number", dir: "asc" }
        });
        //console.log("sth1");
        listView = $("#questionList").kendoListView({
            dataSource: questions,
            template: kendo.template($("#viewTemplate").html()),
        }).data("kendoListView");
    };
});

function loadAnswers(question_id, question_number) {
    require.config({
        baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "./jquery.min",//jquery path
        }
    });
    require(["jquery", "kendo.pager.min", "kendo.listview.min", "kendo.data.min"],
        function ($, kendo) {
            console.log("loading answers with id " + question_id + " and number" + question_number);
            var answerDiv = $("#answers" + question_number);
            console.log(answerDiv);
            if (!isEmpty(answerDiv)) {
                answerDiv.show();
                return;
            }
            var question_answers = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/survey/question_answers?question_id=" + question_id,
                        type: "get",
                        dataType: "json"
                    },
                },
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            fields: {
                                id: { type: "number" },
                                number: { type: "number" },
                                text_answer: { type: "string" },
                                range_answer: { type: "number" },
                                user_studentnr: { type: "string" },
                                questionType: { type: "string" },
                            }
                        }
                    }
                },
                sort: { field: "id", dir: "asc" }
            });

            question_answers.read().then(function () {
                console.log("we are reading...");
                console.log(question_answers.data());
                answerDiv.kendoListView({
                    dataSource: question_answers,
                    editable: true,
                    template: kendo.template($("#answerTemplate").html()),

                }).data("kendoListView");
                answerDiv.show();
            });
        });
};
function hideAnswers(question_number) {
    $("#answers" + question_number).hide();
};
function isEmpty(el) {
    return !$.trim(el.html())
};