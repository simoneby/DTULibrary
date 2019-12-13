package com.models;

import java.time.LocalDateTime;
import java.lang.Comparable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Author s191218, s192671
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class SensorData {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String pointId;
	private LocalDateTime timestamp;
	private String type;
	private String unit;
	private double value;
	private String status;
	private double[] coordinates;
	private Short building;
	private Short floor;
	private Short zone;

	public SensorData() {
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
	public String getTimestamp() {
		return timestamp.toString();
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = LocalDateTime.parse(timestamp);
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

}
