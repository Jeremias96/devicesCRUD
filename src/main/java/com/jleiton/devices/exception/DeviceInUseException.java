package com.jleiton.devices.exception;

public class DeviceInUseException extends RuntimeException{

    public DeviceInUseException(){
        super("Device in use");
    }

    public DeviceInUseException(Integer id, String action){
        super("Device ID: " + id + " in use, can't " + action);
    }
    
}
