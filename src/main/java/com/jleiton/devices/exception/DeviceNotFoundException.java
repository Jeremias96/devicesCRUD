package com.jleiton.devices.exception;

public class DeviceNotFoundException extends RuntimeException{

    public DeviceNotFoundException(){
        super("Device not found");
    }

    public DeviceNotFoundException(Integer id){
        super("Device ID " + id + " not found");
    }
    
}
