
    	var currentLat,currentLong;
    	var curLocation = {
    		lat : 0,
    		long : 0
    	};

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
		
		var lngLat = {lng: 12.525542, lat: 55.786158}; // DTU Library
		
		window.onload = function() {

		// console.log(locationDataOfAll);
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
							friends = data2;
						},
						error: function () {
							console.log("Stuff happened");
						},
						// processData: false,
						type: 'get',
						async: false,
						url: 'http://localhost:8080/friends/all'

					
					});
				for (i=0;i<friends.length;i++){

					var matches = friends[i].name.match(/\b(\w)/g); // first letter of each word in string
					var displayName = matches.join('').toUpperCase(); 


					if (friends[i].locationOfUser!=null){
						var la = parseFloat(friends[i].locationOfUser.coordinateX);
						var lo = parseFloat(friends[i].locationOfUser.coordinateY);
						var tempMarker = new Mazemap.MazeMarker( {
		                	color: "MazeBlue",
		                	size: 36,
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
				}



				
		}
		showLocations();
		/**
 		* Kasper Jensen s183051
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
	                	
	           	document.getElementById("events").style.display = "flex";
	           	document.getElementById("events").innerHTML = output; 
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
	
	//function to get and draw markers on the map - catching right now just to test
	function redrawMarkers() {
	//purely testmarkers
		var testMarkers = [{
	    	"zLevel" :1,
	    	"coordinates" : [12.525542, 55.786158],
	    	"date": "Tomorrow",
	    	"hour": "in an hour",
	    	"eventDescription":"Bullrun like the ones in Madrid!"
	    	},
	    	{ 
	    	"zLevel" :0,
	    	"coordinates" : [12.525362, 55.786078],
	    	"date": "today",
	    	"hour": "now",
	    	"eventDescription":"Savage icetea making!"
	    	}
	    ];
	    
		var eventData;
	    var markerIterator;
	    var markerIteratorPopup;
	    fetch('!!!!!ADDRESS!!!!!').then(response => {
  			return response.json();
			}).then(data => {
  				eventData = data;
  				for(var i in eventData){
  			  		markerIterator = new Mazemap.MazeMarker( {
				zLevel : eventData[i].zLevel,
				color: 'green',
				innerCircle: true,
				innerCircleColor: '#FEFEFE',
				innerCircleScale: 0.7,
				glyphColor: '#000',
				glyphSize: 20,
				glyph: '🤷'
			} )
			.setLngLat({lng: eventData[i].coordinates[0], lat: eventData[i].coordinates[1]})
			markerIteratorPopup = new Mazemap.Popup({ closeOnClick: true, offset: [0,-40]})
			.setHTML(eventData[i].date + " at " + eventData[i].hour + " --- " + eventData[i].eventDescription);

			markerIterator.setPopup(markerIteratorPopup);
		
			markerIterator.addTo(map);
		
  		  	}                                    
		}).catch(err => {
  			eventData = testMarkers;
  			for(var i in eventData){
  				markerIterator = new Mazemap.MazeMarker( {
			zLevel : eventData[i].zLevel,
			color: 'green',
			innerCircle: true,
			innerCircleColor: '#FEFEFE',
			innerCircleScale: 0.7,
			glyphColor: '#000',
			glyphSize: 20,
			glyph: '🤷'
		} )
		.setLngLat({lng: eventData[i].coordinates[0], lat: eventData[i].coordinates[1]})
		markerIteratorPopup = new Mazemap.Popup({ closeOnClick: true, offset: [0,-40]})
			.setHTML(eventData[i].date + " at " + eventData[i].hour + " --- " + eventData[i].eventDescription);
			
		markerIterator.setPopup(markerIteratorPopup);
		
		markerIterator.addTo(map);
		
  		}         
	});
	}
	
	//@author Wendy Dec.6th
	var zonePolygons;
	function redrawPolygons() {
		var zLevel = map.getZLevel();
		if(zLevel > -1) zLevel = zLevel -1; 
		var getAddress = baseUrl+"/sensors/zonedata?level="+zLevel;
			   	
		//need to get coords from server in the future
		fetch(getAddress).then(response => {
  			return response.json();
			}).then(data => {
  				zonePolygons = data;                              
                map.getSource("custom-polygon-layer").setData({type: "FeatureCollection", features:zonePolygons });
				}).catch(err => {
  			console.log('The request failed!'); 
		});	         
	}
