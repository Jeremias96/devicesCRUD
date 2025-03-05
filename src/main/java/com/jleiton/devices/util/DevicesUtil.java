package com.jleiton.devices.util;

import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.model.Device;

public class DevicesUtil {
    
    public static void updateDeviceFields(Device actualDevice, DeviceDto newDevice){
        if (newDevice.getName() != null){
            actualDevice.setName(newDevice.getName());
        }
        if (newDevice.getBrand() != null){
            actualDevice.setBrand(newDevice.getBrand());
        }
        if (newDevice.getState() != null){
            actualDevice.setState(newDevice.getState());
        }
    }
}
