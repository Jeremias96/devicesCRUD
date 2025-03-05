package com.jleiton.devices.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jleiton.devices.dao.DevicesRepository;
import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.exception.DeviceNotFoundException;
import com.jleiton.devices.mapper.DeviceMapper;
import com.jleiton.devices.model.Device;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DevicesService {

    @Autowired
    private final DevicesRepository devicesRepository;
    
    //Create a new device.
    public Device createDevice(DeviceDto deviceDto){
        log.info("[DevicesService] Creating new device");
        return devicesRepository.save(DeviceMapper.INSTANCE.map(deviceDto));
    }
    
    //Fully and/or partially update an existing device.
    public Device updateDevice(DeviceDto deviceDto){
        log.info("[DevicesService] Updating device");
        Device device = getDevice(deviceDto.getId());
        updateDeviceFields(device, deviceDto);
        return devicesRepository.save(device);
    }
    //Fetch a single device.
    public Device getDevice(Integer deviceId){
        log.info("[DevicesService] Getting device with ID {}", deviceId);
        Optional<Device> device = devicesRepository.findById(deviceId);
        if (device.isPresent()){
            return device.get();
        } else {
            throw new DeviceNotFoundException(deviceId);
        }
    }
    
    //Fetch all devices.
    //Fetch devices by brand.
    //Fetch devices by state.
    //Delete a single device.

    private void updateDeviceFields(Device actualDevice, DeviceDto newDevice){
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
