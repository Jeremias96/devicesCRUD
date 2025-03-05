package com.jleiton.devices.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jleiton.devices.dao.DevicesRepository;
import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.exception.DeviceNotFoundException;
import com.jleiton.devices.mapper.DeviceMapper;
import com.jleiton.devices.model.Device;
import com.jleiton.devices.util.DevicesUtil;

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
        Optional<Device> device = getDevice(deviceDto.getId());
        if (device.isPresent()){
            Device actualDevice = device.get();
            DevicesUtil.updateDeviceFields(actualDevice, deviceDto);
            actualDevice = devicesRepository.save(actualDevice);
            return actualDevice;
        } else {
            throw new DeviceNotFoundException(deviceDto.getId());
        }
    }
    //Fetch a single device.
    public Optional<Device> getDevice(Integer deviceId){
        log.info("[DevicesService] Getting device with ID {}", deviceId);
        return devicesRepository.findById(deviceId);
    }
    //Fetch all devices.
    //Fetch devices by brand.
    //Fetch devices by state.
    //Delete a single device.
}
