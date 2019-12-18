var baseUrl;
var listView = {};
//var survey_answer = {};
var surveys = {};
var surveys_created = {};
$(document).ready(function () {
    var survey_id = $("#survey_id").val();
    $("#tabstrip").kendoTabStrip({
        animation:  {
            open: {
                effects: "fadeIn"
            }
        }
    });
    baseUrl = $("#baseUrl").val();
    require.config({
        baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "./jquery.min",//jquery path
        }
    });
    require(["jquery", "kendo.pager.min", "kendo.listview.min", "kendo.data.min", "kendo.dropdownlist.min", "kendo.slider.min"],
        function ($, kendo) {

            surveys = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/survey/active",
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
                            endDate: {type:"date"},
                            startDate: {type: "date"},
                        }
                    }
                },
                sort: { field: "startDate", dir: "asc" },
            });
            surveys_created = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/survey/my_surveys",
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
                            endDate: {type:"date"},
                            startDate: {type: "date"},
                        }
                    }
                },
                sort: { field: "startDate", dir: "asc" },
            });
            surveys.read().then(function () {
                loadSurveys();
            });
            surveys_created.read().then(function () {
                loadSurveysCreated();
            });
            //$("#expiration_date").kendoDateInput();
            //var questionList = [];
        });
    function loadSurveys() {
        //console.log("sth1");
        listView = $("#survey_list_to_complete").kendoListView({
            dataSource: surveys,
            template: kendo.template($("#template1").html()),
            dataBound: function () {
                this.dataSource.data().forEach(element => {
                    });
            }
        }).data("kendoListView");
    };
    function loadSurveysCreated() {
        //console.log("sth1");
        listView = $("#survey_list_created").kendoListView({
            dataSource: surveys_created,
            template: kendo.template($("#template2").html()),
            dataBound: function () {
                this.dataSource.data().forEach(element => {
                    });
            }
        }).data("kendoListView");
    };
});
