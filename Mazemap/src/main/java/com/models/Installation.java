package com.models;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Author s191218
@JsonIgnoreProperties(ignoreUnknown = true)
public class Installation {
private String id;
private String name;
private String namespace;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNamespace() {
	return namespace;
}
public void setNamespace(String namespace) {
	this.namespace = namespace;
}
public ArrayList<String> getComponents() {
	return components;
}
public void setComponents(ArrayList<String> components) {
	this.components = components;
}
private ArrayList<String> components;
}
