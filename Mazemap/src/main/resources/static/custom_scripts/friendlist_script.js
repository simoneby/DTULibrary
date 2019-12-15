//@author s192671
var baseUrl = "";
$(document).ready(function () {
    var friends = [];
    var friendRequestsReceived = [];
    var friendRequestsSent = [];
    
    require.config({
        baseUrl: "./kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "./jquery.min",//jquery path
        }
    });
    baseUrl = $("#baseUrl").val(); //http://se2-webapp05.compute.dtu.dk:8080/mazemap
    loadFriendlist(friends,friendRequestsReceived,friendRequestsSent);

   
    const handleFormSubmit = event => {

        // Stop the form from submitting since we’re handling that with AJAX.
        event.preventDefault();
        submitForm();
    };
    function submitForm() {
        var data = {};
        data.friendEmail = $("#friendEmail").val();
        console.log(data.friendEmail);
        doPostRequest(data.friendEmail,baseUrl + '/friends/add',"#addFriendResult");
    };

    const form = document.getElementById('friendForm');
    form.addEventListener('submit', handleFormSubmit);
    $("#tabstrip").kendoTabStrip({
        animation:  {
            open: {
                effects: "fadeIn"
            }
        }
    });
});

function acceptRequest(element) {
    var friendEmail = element.getAttribute("data-email");
    console.log("accepted");
    doPostRequest(friendEmail,baseUrl + '/friends/acceptFriendRequest',"#acceptRequestResult");
};
function rejectRequest(element) {
    var friendEmail = element.getAttribute("data-email");
    console.log("rejected")
    doPostRequest(friendEmail,baseUrl + '/friends/rejectFriendRequest',"#rejectRequestResult");
};
function doPostRequest(data, url,resultId)
{
    $.ajax({
        contentType: 'application/json',
        data: data,
        dataType: 'json',
        success: function (data, status) {
            console.log("Data: " + data + "\nStatus: " + status);
            $(resultId).html("<p>"+ data.message+"</p>") 
            loadFriendlist();
        },
        error: function () {
            console.log("Something went wrong!");
        },
        processData: false,
        type: 'POST',
        url: url
    });
};
function loadFriendlist(friends,friendRequestsReceived,friendRequestsSent) {
    require(["jquery", "kendo.pager.min", "kendo.listview.min"],
        function ($, kendo) {
            friends = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/friends/all",
                        type: "get",
                        dataType: "json"
                    },
                    destroy: {
                        url: baseUrl + "/friends/deleteFriend",
                        type: "delete",
                        dataType: "json",
                    },
                    parameterMap: function(options, operation) {
                        console.log(operation);
                        console.log(options);
                        if (operation !== "read" && options) {
                            //console.log(options.models);
                            return {friendEmail : options.email};
                        }
                    }
                },
                    schema: {
                        model: {
                            id: "id",
                            fields: {
                                id: { type : "number"},
                                name: { type : "string"},
                                email:{ type : "string"},
                            }
                        }
                }
            });
            friendRequestsSent = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/friends/sentFriendRequests",
                        type: "get",
                        dataType: "json"
                    },

                },
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            id: { type : "number"},
                            name: { type : "string"},
                            email:{ type : "string"},
                        }
                    }
            }
            });
            friendRequestsReceived = new kendo.data.DataSource({
                transport: {
                    read: {
                        url: baseUrl + "/friends/receivedFriendRequests",
                        type: "get",
                        dataType: "json"
                    },
                },
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            id: { type : "number"},
                            name: { type : "string"},
                            email:{ type : "string"},
                        }
                    }
            }
            });
            // create multiSelect from input HTML element
            friends.read().then(function () {
                $("#pager1").kendoPager({
                    dataSource: friends
                });

                $("#listView1").kendoListView({
                    dataSource: friends,
                    template: kendo.template($("#template1").html())
                });
            });
            // create multiSelect from input HTML element
            friendRequestsSent.read().then(function () {
                $("#pager2").kendoPager({
                    dataSource: friendRequestsSent
                });

                $("#listView2").kendoListView({
                    dataSource: friendRequestsSent,
                    template: kendo.template($("#template2").html())
                });
            });
            friendRequestsReceived.read().then(function () {
                $("#pager3").kendoPager({
                    dataSource: friendRequestsReceived
                });

                $("#listView3").kendoListView({
                    dataSource: friendRequestsReceived,
                    template: kendo.template($("#template3").html())
                });
            });
        });
};
