package com.jleiton.devices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jleiton.devices.dao.DevicesRepository;
import com.jleiton.devices.dto.DeviceDto;
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
    //Fully and/or partially update an existing device.
    //Fetch a single device.
    //Fetch all devices.
    //Fetch devices by brand.
    //Fetch devices by state.
    //Delete a single device.
}
