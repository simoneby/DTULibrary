//@author Wendy&Laura
package com.models;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;

public class InitialSensorData {

    public static int sensorNum = 0;
    static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
	private String type;
	private String unit;
	private double value;
    private String name = "";
    private short building = -1;
    private short floor = -1;
    private short zone = -1;
    private int id = 0;
    private Date timeStamp = null;

    public InitialSensorData()
    {
        
    }
    public InitialSensorData(JSONObject sensor) throws ParseException {
        id = sensorNum;
        sensorNum++;
        name = sensor.getString("pointId");
        building = Short.valueOf(name.substring(1,4));
        floor = Short.valueOf(name.substring(7,8));
        zone = Short.valueOf(name.substring(11,13));
        String time_s = (String) sensor.get("timestamp");
        timeStamp = f.parse(time_s.substring(0,10)+"-"+time_s.substring(11,19));
        type = (String) sensor.get("type");
        unit = (String) sensor.get("unit");
        value = Float.valueOf(sensor.getString("value"));
    }

    public String getName()
    {
        return name;
    }

    public static int getSensorNum() {
        return sensorNum;
    }

    public static void setSensorNum(int sensorNum) {
        InitialSensorData.sensorNum = sensorNum;
    }

    public static SimpleDateFormat getF() {
        return f;
    }

    public static void setF(SimpleDateFormat f) {
        InitialSensorData.f = f;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getBuilding() {
        return building;
    }

    public void setBuilding(short building) {
        this.building = building;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    public short getZone() {
        return zone;
    }

    public void setZone(short zone) {
        this.zone = zone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}