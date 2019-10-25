package com.controllers;

import java.util.*;
import java.util.function.Function;

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
public class SensorDataController {
  @Autowired
  RestTemplate restTemplate;

  @RequestMapping(value = "/zonedata", method = RequestMethod.GET)
  public ProcessedSensorData[] getZoneData(
      @RequestParam(name = "level", required = false, defaultValue = "0") short level) {
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

  @RequestMapping(value = "/base_wendy", method = RequestMethod.GET)
  public InitialSensorData[] getBaseData2() {
    return SensorDataHelper.getSensorData();
  }

  @RequestMapping(value = "/peopleCounter", method = RequestMethod.GET)
  public String getPeopleCounter(@RequestParam(value = "studnum", required = false, defaultValue = "") String name) {
    ArrayList<Installation> installations = new ArrayList<Installation>();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.add("x-client-id", "DTUAPI");
    headers.add("x-api-key", "3593e5b65f4ad590f859a876f976ba18");
    Object uriVariables = null;
    HttpEntity<ArrayList<Installation>> entity = new HttpEntity<ArrayList<Installation>>(installations, headers);
    ResponseEntity<? extends ArrayList> response = restTemplate.exchange(
        "https://eds.modcam.io/v1/peoplecounter/installations", HttpMethod.GET, entity, installations.getClass(),
        uriVariables);
    return response.getBody().toString();
    // installations = restTemplate.getForObject(
    // "https://eds.modcam.io/v1/heatmap/installations", installations.getClass());
    /*
     * for(Installation i : installations)
     * 
     * {
     * 
     * }
     */
  }
}
