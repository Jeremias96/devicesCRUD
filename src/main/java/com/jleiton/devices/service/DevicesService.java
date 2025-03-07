package com.jleiton.devices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.jleiton.devices.dao.DevicesRepository;
import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.exception.DeviceInUseException;
import com.jleiton.devices.exception.DeviceNotFoundException;
import com.jleiton.devices.mapper.DeviceMapper;
import com.jleiton.devices.model.Device;
import com.jleiton.devices.model.StateEnum;

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
    public List<Device> getAllDevices(){
        log.info("[DevicesService] Getting all devices");
        return devicesRepository.findAll();
    }

    //Fetch devices by brand.
    //Fetch devices by state.
    public List<Device> getDevicesByParams(String brand, StateEnum state){
        log.info("[DevicesService] Getting devices by brand: {} and state: {}", brand, state);
        Device device = Device.builder().brand(brand).state(state).build();
        Example<Device> deviceExample = Example.of(device);
        return devicesRepository.findAll(deviceExample);
    }

    //Delete a single device.
    public void deleteDevice(Integer deviceId){
        log.info("[DevicesService] Deleting device with ID: {}", deviceId);
        Device deviceToDelete = getDevice(deviceId);
        if (deviceToDelete.getState() != StateEnum.IN_USE){
            devicesRepository.deleteById(deviceId);
        } else {
            throw new DeviceInUseException(deviceId, "delete");
        }
    }

    private void updateDeviceFields(Device actualDevice, DeviceDto newDevice){
        if (newDevice.getState() != null){
            actualDevice.setState(newDevice.getState());
        }
        if (actualDevice.getState() != StateEnum.IN_USE) {
            if (newDevice.getName() != null){
                actualDevice.setName(newDevice.getName());
            }
            if (newDevice.getBrand() != null){
                actualDevice.setBrand(newDevice.getBrand());
            }
        }
    }
}
