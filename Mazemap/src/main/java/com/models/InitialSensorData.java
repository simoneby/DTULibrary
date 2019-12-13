package com.models;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;

//@Author s192671
public class InitialSensorData {

    public static int sensorNum = 0;
    // static String soundUnit = "dB";
    // static String humidityUnit = "%";
    // static String temperatureUnit = "degree-celsius";
    // static String ultraSoundUnit = "cm";
    // static String lightUnit = "lux";
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
    
    // private float sound = -1;
    // private float humidity = -1;
    // private float temperature = -1;
    // private float ultraSound = -1;
    // private float light = -1;

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
        //changeData(sensor.getString("type"),Float.valueOf(sensor.getString("value")),false);
    }

    public String getName()
    {
        return name;
    }

    // public boolean changeData(String type, float value, boolean b)
    // {

    //     switch(type) {

    //         case ("Humidity"):
    //             if(humidity>=0 && b) return false;
    //             humidity = value;
    //             break;
    //         case("ultraSound"):
    //             if(ultraSound>=0 && b) return false;
    //             ultraSound = value;
    //             break;
    //         case("Sound"):
    //             if(sound>=0 && b) return false;
    //             sound = value;
    //             break;
    //         case("Light"):
    //             if(light>=0 && b) return false;
    //             sound = value;
    //             break;
    //         case("Temperature"):
    //             if(temperature>=0 && b) return false;
    //             temperature = value;
    //             break;
    //     }
    //     return true;
    // }

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
