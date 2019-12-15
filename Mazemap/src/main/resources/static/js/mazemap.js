//@Author s191545
var baseUrl;
var curLocation = {
	lat: 0,
	long: 0
};
	document.getElementById("createButton").addEventListener("click", function() {
		document.querySelector(".bg-modal").style.display="block";
		document.querySelector(".feedback").style.display="none";
	});
	
	document.getElementById("close").addEventListener("click", function() {
		document.querySelector(".bg-modal").style.display="none";
		document.querySelector(".createButton").style.display="block";
		if(placingEvent){
			createMarker.remove();
		}
	});
	
   var currentLat,currentLong;
   var map = new Mazemap.Map({
            // container id specified in the HTML
            container: 'map',

            campuses: 89,

            // initial position in lngLat format
            
            center: {lng: 12.521545, lat: 55.785585},

            // initial zoom
            zoom: 15,

            zLevel: 1
        });

        // Add zoom and rotation controls to the map.
        map.addControl(new Mazemap.mapboxgl.NavigationControl());
        var zonePolygons;
		var lngLat = {lng: 12.525542, lat: 55.786158}; // DTU Library
		
		window.onload = function() {
		 baseUrl = $("#baseUrlInput").val();	
		// check for Geolocation support
		if (navigator.geolocation) {
			console.log('Geolocation is supported!');
		}
		else {
			console.log('Geolocation is not supported for this Browser/OS.');
		}

       var startPos;
       var geoOptions = {
         enableHighAccuracy: true
     }
     var geoSuccess = function(position) {
      startPos = position;
      document.getElementById('startLat').innerHTML = startPos.coords.latitude;
      document.getElementById('startLon').innerHTML = startPos.coords.longitude;
      currentLat = startPos.coords.latitude;
      currentLong = startPos.coords.longitude;
  };
  navigator.geolocation.getCurrentPosition(geoSuccess);
};

		//map.center = {lng: currentLong, lat: currentLat};
        map.on('load', function(){

           console.log(currentLong,currentLat);

           curLocation.lat = currentLat;
           curLocation.long = currentLong;
           localStorage.setItem("storedLocation",JSON.stringify(curLocation));


           var blueDot = new Mazemap.BlueDot( {
              zLevel: 1,
              accuracyCircle: true
          } )
           .setLngLat( {lng: currentLong, lat: currentLat} )
           .setAccuracy(10).addTo(map);


           function showLocations(){
            var friends;
            $.ajax({

                dataType: 'json',
                success: function (data2) {
                    try {
                        data2 = jQuery.parseJSON(data2);
                    }
                    catch (err) {
                        data2 = typeof data2 =='object' ? data2 : jQuery.parseJSON(data2);
                    }
                    if(data2 != null)
                    	friends = data2;
                    console.log("get happened");
                },
                error: function () {
                    console.log("Stuff happened");
                },
                        // processData: false,
                        type: 'get',
                        async: false,
                        url: baseUrl + '/friends/all'
                        // url: 'http://localhost:8080/friends/all'


                    });
            if(friends != null){
            	console.log("friends array length " + friends.length);
            	for (i=0;i<friends.length;i++){
            		if(friends[i].name != null)
            			{
                    var matches = friends[i].name.match(/\b(\w)/g); // first letter of each word in string
                    var displayName = matches.join('').toUpperCase(); 
                    console.log("balh");

                    if (friends[i].locationOfUser!=null){
                        var la = parseFloat(friends[i].locationOfUser.coordinateX);
                        var lo = parseFloat(friends[i].locationOfUser.coordinateY);
                        console.log("bluah",la,lo);

                        var tempMarker = new Mazemap.MazeMarker( {
                            color: "MazePink",
                            size: 35,
                            zLevel: 1,
                            glyph: displayName,
                            glyphColor:'#000000',
                            glyphSize:'20'
                        }).setLngLat({lng: lo, lat: la}).addTo(map);
                        var markerIteratorPopup = new Mazemap.Popup({ closeOnClick: true, offset: [0,-40]})
                        .setHTML(friends[i].name + ": " + friends[i].locationOfUser.locationMessage);
                        tempMarker.setPopup(markerIteratorPopup);
                        tempMarker.addTo(map);
                    }
                }}
                
            }}
            showLocations();


		/**
 		* @Author s183051
 		*
 		* Layers, get request for zones
 		*/
 		
      map.addLayer({
       id: 'custom-polygon-layer',
       type: "fill",
       source: {
           type: 'geojson',
           data: null,
       },
       paint: {
        "fill-color": "#fc0",
        "fill-outline-color": "red"
    }
});			 
      map.layerEventHandler.on('click', 'custom-polygon-layer', (e, features) => {    
        var output = ""
        var zonePropertiesArray = JSON.parse(features[0].properties.zoneProperties);
        for(var i in zonePropertiesArray)
           output = appendString(zonePropertiesArray[i], output);

       document.getElementById("sensorZones").style.display = "flex";
       document.getElementById("sensorZones").innerHTML = output; 
   });		

      map.on('zlevel', redrawPolygons);
      redrawPolygons();


	    //fetching events on window load
	    redrawMarkers();

    });

	//simple append function
	function appendString(item, output) {
		var res = output.concat(item.type + ": " + item.value + " " + item.unit + " ");
		return res;
	}
	
	var center = map.getBounds().getCenter();
  document.querySelector(".eventLng").value=center.lng;
  document.querySelector(".eventLat").value=center.lat;

	var placingEvent = false;
  map.on('drag', function() {
	  center = map.getBounds().getCenter();
   	document.querySelector(".eventLng").value=center.lng;
   	document.querySelector(".eventLat").value=center.lat;
   	if(placingEvent){
   		createMarker.setLngLat({lng: center.lng, lat: center.lat});
   	}
 });

  //@Author s183051, s170899
  function fire_ajax_submit() {

   var event = {}
   event["description"] = $("#description").val();
   event["name"] = $("#name").val();
   event["date"] = $("#date").val();
   event["time"] = $("#time").val();
   event["lng"] = $("#eventLng").val();
   event["lat"] = $("#eventLat").val();


   $("#submitEventForm").prop("disabled", true);

   $.ajax({
       type: "POST",
       contentType: "application/json",
       url: baseUrl+"/events/createevent",
       data: JSON.stringify(event),
       dataType: 'json',
       cache: false,
       timeout: 600000,
       success: function (data) {

           var json = "<h4>Ajax Response</h4><pre>"
           + JSON.stringify(data, null, 4) + "</pre>";
           $('#feedback').html(data.msg);
           window.location.reload(true);
           console.log("SUCCESS : ", data);
           $("#submitEventForm").prop("disabled", false);
           document.querySelector(".feedback").style.display="block";
           document.querySelector(".bg-modal").style.display="none";

       },
       error: function (e) {

           var json = "<h4>Ajax Response</h4><pre>"
           + e.responseText + "</pre>";
           $('#feedback').html(json);
           document.querySelector(".feedback").style.display="block";
           document.querySelector(".bg-modal").style.display="none";
           console.log("ERROR : ", e);
           $("#submitEventForm").prop("disabled", false);

       }
   });

}	
  var createMarker;
  document.getElementById("placeEvent").addEventListener("click", function() {
  placingEvent = true;
  center = map.getBounds().getCenter();
  
  if(typeof(createMarker) === 'undefined')
	{
	createMarker = new Mazemap.MazeMarker( {
		zLevel : 1,
		color: 'green',
		innerCircle: true,
		innerCircleColor: '#FEFEFE',
		innerCircleScale: 0.7,
		glyphColor: '#000',
		glyphSize: 20,
		glyph: '🤷'
	} ).setLngLat({lng: center.lng, lat: center.lat});
	 	createMarker.addTo(map);
			}
	document.querySelector(".bg-modal").style.display="none";
	document.querySelector(".createButton").style.display="none";
	document.querySelector(".okPlacement").style.display="block";
	
});

	
  document.getElementById("okPlacement").addEventListener("click", function() {
	document.querySelector(".bg-modal").style.display="block";
	document.querySelector(".okPlacement").style.display="none";
		
	});
	//function to get and draw markers on the map - catching right now just to test
	function redrawMarkers() {   
		var eventData;
       var markerIterator;
       var markerIteratorPopup;
       fetch(baseUrl+'/events/eventdata').then(response => {
           return response.json();
       }).then(data => {
          eventData = data;
          for(var i in eventData){
           markerIterator = new Mazemap.MazeMarker( {
            zLevel : 1,
            color: 'green',
            innerCircle: true,
            innerCircleColor: '#FEFEFE',
            innerCircleScale: 0.7,
            glyphColor: '#000',
            glyphSize: 20,
            glyph: '🤷'
        } )
           .setLngLat({lng: eventData[i].lng, lat: eventData[i].lat})
           markerIteratorPopup = new Mazemap.Popup({ closeOnClick: true, offset: [0,-40]})
           .setHTML(eventData[i].date + " at " + eventData[i].time + " --- " + eventData[i].description);

           markerIterator.setPopup(markerIteratorPopup);

           markerIterator.addTo(map);

       }                                    
   }).catch(err => {


   });
}

	//function to get and redraw polygons on start and on floor switch, Coordinates need to be server side
	function redrawPolygons() {
		var zLevel = map.getZLevel();
		if(zLevel > -1)
			zLevel = zLevel -1; 
		var getAddress = baseUrl + "/sensors/zonedata?level=".concat(zLevel);

		fetch(getAddress).then(response => {
           return response.json();
       }).then(data => {
          zonePolygons = data;
          map.getSource("custom-polygon-layer").setData({type: "FeatureCollection", features:zonePolygons });
      }).catch(err => {
       console.log('The request failed!'); 
   });	         
  }