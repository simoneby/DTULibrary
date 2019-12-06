$(document).ready(function () {
    var events = [];
    require.config({
        baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "./jquery.min",//jquery path
        }
    });
    loadEventlist(events);
});

function loadEventlist(events) {
	 require(["jquery", "kendo.pager.min", "kendo.listview.min"],
		      function ($, kendo) {
		 			events = new kendo.data.DataSource({

	                    schema: {
	                        model: {
	                            id: "id",
	                            fields: {
	                                id: { type : "number"},
	                                name: { type : "string"},
	                                lat: { type : "number" },
	                                lng: { type : "number" },
	                                description:{ type : "string"},
	                                isPublic:{ type: "boolean"},
	                                creator:{ type:"string"},
	                                date: { type : "string" },
	                                time: { type : "string" },
	                            }
	                        }
	                },
		 				transport: {
		 					read: {
                        url: "http://localhost:8080/events/eventdata",
                        type: "get",
                        dataType: "json"
                    },
                    destroy: {
                        url: "http://localhost:8080/events/deleteevent",
                        type: "delete",
                        dataType: "json",
                    },
                    update: {
                    	url: "http://localhost:8080/events/updateevent",
                    	type: "post",
                    	dataType:"json",
                    	contentType:"application/json",
                    },
                    parameterMap: function(options, operation) {
                        if (operation == "destroy" && options) {
                            //console.log(options.models);
                            return {id : options.id};
                        }
                        if (operation == "update" && options) {
                        	return JSON.stringify(options);
                        }
                    }
                }
            });
                        
            // create multiSelect from input HTML element
            events.read().then(function () {
                $("#pagerEvents").kendoPager({
                    dataSource: events
                });

                $("#listViewEvents").kendoListView({
                    dataSource: events,
                    template: kendo.template($("#templateEvents").html()),
                    editTemplate: kendo.template($("#editTemplate").html())
                });
            });
	 });
       };

