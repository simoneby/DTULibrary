package com.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ProcessedSensorData {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String pointId;
	private Date timestamp;
    //zone coordinates for the overlay
    private double[] coordinates = new double[5];
	private Short building;
	private Short floor;
	private Short zone;
    private ArrayList<SensorValue> zoneProperties;

	public ProcessedSensorData() {
        zoneProperties = new ArrayList<SensorValue>();
	}
	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
		this.building = Short.valueOf(pointId.substring(1,4));
        this.floor = Short.valueOf(pointId.substring(7,8));
        this.zone = Short.valueOf(pointId.substring(11,13));
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Short getBuilding() {
		return building;
	}

	public void setBuilding(Short building) {
		this.building = building;
	}

	public Short getFloor() {
		return floor;
	}

	public void setFloor(Short floor) {
		this.floor = floor;
	}

	public Short getZone() {
		return zone;
	}

	public void setZone(Short zone) {
		this.zone = zone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
    }
    public void addProperty(String type, String unit, double value)
    {
        zoneProperties.add(new SensorValue(type,unit,value));
    }
    
class SensorValue
{
    private String type;
    private String unit;
    private double value;

    
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

        public SensorValue(String type, String unit, double value) {
            this.type = type;
            this.unit = unit;
            this.value = value;
        }

        public SensorValue() {
        }
}

    public ArrayList<SensorValue> getZoneProperties() {
        return zoneProperties;
    }

    public void setZoneProperties(ArrayList<SensorValue> zoneProperties) {
        this.zoneProperties = zoneProperties;
    }
}

