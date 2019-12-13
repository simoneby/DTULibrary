

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
                	
           	document.getElementById("events").style.display = "flex";
           	document.getElementById("events").innerHTML = output; 
        });		
            
		map.on('zlevel', redrawPolygons);
	    redrawPolygons();
	    
	    
	    //fetching events on window load
	    redrawMarkers();
	    	         
	
			  
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
	    fetch('https://se2-webapp05.compute.dtu.dk:8443/mazemap/events/eventdata').then(response => {
  			return response.json();
			}).then(data => {
  				eventData = data;
  				for(var i in eventData){
  			  		markerIterator = new Mazemap.MazeMarker( {
				zLevel : 0,
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
  			eventData = testMarkers;
  			for(var i in eventData){
  				markerIterator = new Mazemap.MazeMarker( {
			zLevel : 0,
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
				    
	//function to get and redraw polygons on start and on floor switch, Coordinates need to be server side
	function redrawPolygons() {
		var zLevel = map.getZLevel();
		if(zLevel > -1)
			zLevel = zLevel -1; 
		var getAddress = "https://se2-webapp05.compute.dtu.dk:8443/mazemap/sensors/zonedata?level=".concat(zLevel);
			   	
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