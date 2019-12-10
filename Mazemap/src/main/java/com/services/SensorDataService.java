
package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import com.models.*;
import com.helpers.*;


@Component
public class SensorDataService
{
	RestTemplate restTemplate;
    public ProcessedSensorData[] getZoneData(short level) 
	{
		HashMap<String, ProcessedSensorData> zoneData = new HashMap<String, ProcessedSensorData>();
		InitialSensorData[] sensorData = SensorDataHelper.getSensorData();
		if(sensorData.length == 0)
		{
			try {
				throw new Exception("No sensor data");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		for (int i = 0; i < sensorData.length; i++)
		{
			InitialSensorData sd = sensorData[i];
			if (level == sd.getFloor()) 
			{
				String zoneName = String.format("%d_%d_%d", sd.getBuilding(), sd.getFloor(), sd.getZone());
				if (zoneData.containsKey(zoneName))
				{
					zoneData.get(zoneName).addProperty(sd.getType(), sd.getUnit(), sd.getValue());
				}
				else 
				{
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
		if(zoneData.size() == 0)
		{
			try {
				throw new Exception("No zone data");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		if(zoneData.size()==0) return null;
		ProcessedSensorData[] a = new ProcessedSensorData[zoneData.size()];
		zoneData.values().toArray(a);
		return a;
	}

}
