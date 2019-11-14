$(document).ready(function () {
    var friends = [];
    var friendRequestsReceived = [];
    var friendRequestsSent = [];
    require.config({
        baseUrl: "/kendo-ui-core/js/", // the path where the kendo scripts are present
        paths: {
            "jquery": "/js/jquery.min",//jquery path
        }
    });
    loadFriendlist(friends,friendRequestsReceived,friendRequestsSent);

    //var acceptRequestButtons = document.getElementsByClassName('acceptReqButton');
    //acceptRequestButtons.foreach()
    //acceptRequestButtons.foreach.addEventListener('submit', handleFormSubmit);
    const handleFormSubmit = event => {

        // Stop the form from submitting since we’re handling that with AJAX.
        event.preventDefault();
        submitForm();
    };
    function submitForm() {
        var data = {};
        data.friendEmail = $("#friendEmail").val();
        console.log(data.friendEmail);
        doPostRequest(data.friendEmail,'http://localhost:8080/mazemap/friends/add',"#addFriendResult");
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
    doPostRequest(friendEmail,'http://localhost:8080/mazemap/friends/acceptFriendRequest',"#acceptRequestResult");
};
function rejectRequest(element) {
    var friendEmail = element.getAttribute("data-email");
    console.log("rejected")
    doPostRequest(friendEmail,'http://localhost:8080/mazemap/friends/rejectFriendRequest',"#rejectRequestResult");
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
                        url: "http://localhost:8080/mazemap/friends/all",
                        type: "get",
                        dataType: "json"
                    },
                    destroy: {
                        url: "http://localhost:8080/mazemap/friends/deleteFriend",
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
                        url: "http://localhost:8080/mazemap/friends/sentFriendRequests",
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
                        url: "http://localhost:8080/mazemap/friends/receivedFriendRequests",
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