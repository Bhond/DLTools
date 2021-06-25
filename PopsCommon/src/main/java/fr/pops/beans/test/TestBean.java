package fr.pops.beans.test;

import fr.pops.beans.bean.Bean;
import fr.pops.beans.properties.Property;

public class TestBean extends Bean {

	/*****************************************
	*
	 * Properties
	 *
	 *****************************************/
	public final static String beanTypeId = "Test";

	private Property<String> name;
	private String nameDefault = "Test";

	private Property<Double> speed;
	private Double speedDefault = 0.5d;

	private Property<Boolean> isRunning;
	private boolean isRunningDefault = true;

	/*****************************************
	*
	 * Ctor
	 *
	 *****************************************/
	 public TestBean(){
		super(beanTypeId);
		this.name = this.createProperty("name",nameDefault,false,false);
		this.speed = this.createProperty("speed",speedDefault,false,true);
		this.isRunning = this.createProperty("isRunning",isRunningDefault,true,false);
	}

	/*****************************************
	*
	 * Getters
	 *
	 *****************************************/
	public String getName(){
		return this.name.getValue();
	}

	public double getSpeed(){
		return this.speed.getValue();
	}

	public boolean getIsRunning(){
		return this.isRunning.getValue();
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

	public void setIsRunning(boolean newValue){
		this.isRunning.setValue(newValue);
	}

}