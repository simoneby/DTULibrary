package com.models;

import java.util.ArrayList;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.helpers.Coordinates;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessedSensorData {
	@JsonIgnore
	private Integer id;
	@JsonIgnore
	private String pointId;
	@JsonIgnore
	private Date timestamp;
	private String type = "Feature";
	// zone coordinates for the overlay
	@JsonIgnore
	private Short building;
	@JsonIgnore
	private Short floor;
	@JsonIgnore
	private Short zone;
	PolygonGeometry geometry;
	PolygonProperties properties;

	public ProcessedSensorData() {
		properties = new PolygonProperties();
		geometry = new PolygonGeometry();
		properties.zoneProperties = new ArrayList<SensorValue>();

	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
		this.building = Short.valueOf(pointId.substring(1, 4));
		this.floor = Short.valueOf(pointId.substring(7, 8));
		this.zone = Short.valueOf(pointId.substring(11, 13));
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

	public void addProperty(String type, String unit, double value) {
		properties.addProperty(type, unit, value);
	}

	class SensorValue {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PolygonGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(short floor, short zone) {
		this.geometry.addCoordinates(floor, zone);
	}

	public PolygonProperties getProperties() {
		return properties;
	}

	public void setProperties(PolygonProperties properties) {
		this.properties = properties;
	}

	// #region inner classes
	class PolygonGeometry {
		String type = "Polygon";
		private ArrayList<double[][]> coordinates;

		public PolygonGeometry() {
			coordinates = new ArrayList<double[][]>();
		}

		public String getType() {
			return type;
		}

		public void addCoordinates(short floor, short zone) {
			coordinates.add(Coordinates.getCoordinates(floor, zone));
		}

		public void setType(String type) {
			this.type = type;
		}

		public ArrayList<double[][]> getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(ArrayList<double[][]> coordinates) {
			this.coordinates = coordinates;
		}

	}

	class PolygonProperties {
		private short zLevel;
		private ArrayList<SensorValue> zoneProperties;

		public PolygonProperties() {
			zoneProperties = new ArrayList<SensorValue>();
		}

		public short getzLevel() {
			return zLevel;
		}

		public ArrayList<SensorValue> getZoneProperties() {
			return zoneProperties;
		}

		public void setZoneProperties(ArrayList<SensorValue> zoneProperties) {
			this.zoneProperties = zoneProperties;
		}

		public void addProperty(String type, String unit, double value) {
			zoneProperties.add(new SensorValue(type, unit, value));
		}

		public void setzLevel(short zLevel) {
			this.zLevel = zLevel;
		}

	}
	// #endregion
}
