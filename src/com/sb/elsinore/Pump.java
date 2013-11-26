package com.sb.elsinore;

import java.util.ArrayList;

import jGPIO.GPIO;
import jGPIO.InvalidGPIOException;
import jGPIO.OutPin;

public class Pump {
	
	/*
	 * Pump class is designed to control a single GPIO pin with a straight forward on/off functionality 
	 */
	
	public String name;
	public OutPin output = null;
	public String pName = null;
	public boolean virtualDevice = false;
	public ArrayList virtualDeviceAttr=null;
	
	public Pump(String name, String pinName) throws InvalidGPIOException {
		this.name = name;
		if(System.getProperty("virtualPumps") != null)
			virtualDevice=true;
		if(virtualDevice){
			virtualDeviceAttr=new ArrayList();
			virtualDeviceAttr.add(name);
			virtualDeviceAttr.add(pinName);
			virtualDeviceAttr.add("0");
		}
		else{
			try {
				output = new OutPin(pinName);
				pName=pinName;
			} catch (InvalidGPIOException e) {
				// TODO Auto-generated catch block
				throw e;
			} catch (RuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean getStatus() {
		if (virtualDevice)
			return ((String)virtualDeviceAttr.get(2)).equals("1");
		return output.getValue().equals("1");

	}
	
	public void turnOn() {
		if (virtualDevice){
			virtualDeviceAttr.set(2, "1");
			return;
		}
		output.setValue(true);
	}
	
	public void turnOff() {
		if (virtualDevice){
			virtualDeviceAttr.set(2, "0");
			return;
		}
		output.setValue(false);
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public String getGPIO() {
		// TODO Auto-generated method stub
		return output.getGPIOName();
	}
}
