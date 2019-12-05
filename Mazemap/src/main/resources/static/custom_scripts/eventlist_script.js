$(document).ready(function () {
    var events = [];
    require.config({
        baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "./jquery.min",//jquery path
        }
    });
    loadEventlist(events);
    console.log(events);

    //var acceptRequestButtons = document.getElementsByClassName('acceptReqButton');
    //acceptRequestButtons.foreach()

    
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
                    parameterMap: function(options, operation) {
                        console.log(operation);
                        console.log(options);
                        if (operation !== "read" && options) {
                            //console.log(options.models);
                            return {id : options.id};
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
                    template: kendo.template($("#templateEvents").html())
                });
            });
	 });
       };
