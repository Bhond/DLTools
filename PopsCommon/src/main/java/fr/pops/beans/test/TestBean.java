package fr.pops.beans.test;

import fr.pops.beans.bean.Bean;
import fr.pops.beans.bean.Property;

public class TestBean extends Bean {

	/*****************************************
	*
	 * Properties
	 *
	 *****************************************/
	private Property name;
	private String nameDefault = "Test";

	private Property speed;
	private Double speedDefault = 0.5d;

	private Property positionX;
	private Double positionXDefault = 0d;

	private Property positionY;
	private Double positionYDefault = 0d;

	private Property directionX;
	private Double directionXDefault = -0.5d;

	private Property directionY;
	private Double directionYDefault = 0.5d;

	private Property isRunning;
	private boolean isRunningDefault = true;

	/*****************************************
	*
	 * Ctor
	 *
	 *****************************************/
	 public TestBean(){
		super();
		this.name = new Property.PropertyBuilder().withName("name").withType("String").withDefaultValue("Test").build();
		this.speed = new Property.PropertyBuilder().withName("speed").withType("double").withDefaultValue(0.5d).build();
		this.positionX = new Property.PropertyBuilder().withName("positionX").withType("double").withDefaultValue(0d).build();
		this.positionY = new Property.PropertyBuilder().withName("positionY").withType("double").withDefaultValue(0d).build();
		this.directionX = new Property.PropertyBuilder().withName("directionX").withType("double").withDefaultValue(-0.5d).build();
		this.directionY = new Property.PropertyBuilder().withName("directionY").withType("double").withDefaultValue(0.5d).build();
		this.isRunning = new Property.PropertyBuilder().withName("isRunning").withType("boolean").withDefaultValue(true).build();
	}

	/*****************************************
	*
	 * Getters
	 *
	 *****************************************/
	public String getName(){
		return (String)this.name.getValue();
	}

	public double getSpeed(){
		return (double)this.speed.getValue();
	}

	public double getPositionX(){
		return (double)this.positionX.getValue();
	}

	public double getPositionY(){
		return (double)this.positionY.getValue();
	}

	public double getDirectionX(){
		return (double)this.directionX.getValue();
	}

	public double getDirectionY(){
		return (double)this.directionY.getValue();
	}

	public boolean getIsRunning(){
		return (boolean)this.isRunning.getValue();
	}

	/*****************************************
	*
	 * Setters
	 *
	 *****************************************/
	public void setName(String newValue){
		this.name.setValue(newValue);
	}

	public void setSpeed(double newValue){
		this.speed.setValue(newValue);
	}

	public void setPositionX(double newValue){
		this.positionX.setValue(newValue);
	}

	public void setPositionY(double newValue){
		this.positionY.setValue(newValue);
	}

	public void setDirectionX(double newValue){
		this.directionX.setValue(newValue);
	}

	public void setDirectionY(double newValue){
		this.directionY.setValue(newValue);
	}

	public void setIsRunning(boolean newValue){
		this.isRunning.setValue(newValue);
	}

}