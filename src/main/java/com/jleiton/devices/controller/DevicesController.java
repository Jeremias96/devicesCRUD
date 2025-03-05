package com.jleiton.devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.model.Device;
import com.jleiton.devices.service.DevicesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DevicesController {

    @Autowired
    private final DevicesService devicesService;

    @PostMapping
    public ResponseEntity<Device> createNewDevice(@RequestBody DeviceDto deviceDto){
        return new ResponseEntity<Device>(devicesService.createDevice(deviceDto), HttpStatus.OK);
    }
    
}
