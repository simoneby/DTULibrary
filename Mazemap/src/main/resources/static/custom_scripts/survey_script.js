//@author s192671
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
            $("#endDate").kendoDateInput({
                format: 'yyyy-MM-dd'
            });
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
                   
                    this.data().forEach(element => {
                        element.isRange = element.type == 1;
                       
                    });
                   
                }
            });
            listView = $("#questionList").kendoListView({
                dataSource: questions,
                editale: true,
                template: kendo.template($("#viewTemplate").html()),
                editTemplate: kendo.template($("#editTemplate").html()),
                edit: function (e) {
                    if (e.model.number == 0) {
                        e.model.number = this.dataSource.data().length;
                      
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
                
                survey[this.name] = $(this).val();
               
                
            }
        });
        survey.questions = questions.data(); 
       
        $.ajax({
            contentType: 'application/json',
            data: JSON.stringify(survey),
            dataType: 'json',
            success: function (data, status) {
                $("#result").html("<p>" + data.message + "</p>");
                $("#save_survey").prop('disabled',true);
            },
            error: function () {
                console.log("Request failed");
            },
            processData: false,
            type: 'POST',
            url: baseUrl + '/survey/save'
        });
        
    };
    const form = document.getElementById('survey_form');
    form.addEventListener('submit', handleFormSubmit);
});