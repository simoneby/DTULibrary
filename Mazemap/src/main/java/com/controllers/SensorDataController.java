//@Author Laura & Wendy
package com.controllers;

import java.util.*;
import java.util.function.Function;
import java.io.File;
import java.util.Scanner;

import com.models.*;
import com.helpers.*;

import org.hibernate.internal.util.compare.ComparableComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/sensors")
public class SensorDataController 
{
	@Autowired
	RestTemplate restTemplate;
	InitialSensorData peopleCounter;
	@RequestMapping(value = "/zonedata", method = RequestMethod.GET)
	public ProcessedSensorData[] getZoneData(@RequestParam(name = "level", required = false, defaultValue = "0") short level) 
	{
    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
		HashMap<String, ProcessedSensorData> zoneData = new HashMap<String, ProcessedSensorData>();
		InitialSensorData[] sensorData = SensorDataHelper.getSensorData();
		for (int i = 0; i < sensorData.length; i++) {
		  InitialSensorData sd = sensorData[i];
		  if (level == sd.getFloor()) {
			String zoneName = String.format("%d_%d_%d", sd.getBuilding(), sd.getFloor(), sd.getZone());
			if (zoneData.containsKey(zoneName)) {
			  zoneData.get(zoneName).addProperty(sd.getType(), sd.getUnit(), sd.getValue());
			} else {
			  ProcessedSensorData newVal = new ProcessedSensorData();
			  newVal.setPointId(sd.getName());
			  newVal.setBuilding(sd.getBuilding());
			  newVal.setFloor(sd.getFloor());
			  newVal.setZone(sd.getZone());
			  newVal.addProperty(sd.getType(), sd.getUnit(), sd.getValue());
			  newVal.setTimestamp(sd.getTimeStamp());
			  newVal.setGeometry(sd.getFloor(),sd.getZone());
			  zoneData.put(zoneName, newVal);
			}
		  }
		}
		ProcessedSensorData[] a = new ProcessedSensorData[zoneData.size()];
		zoneData.values().toArray(a);
		return a;
	}
}