package com.jleiton.devices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.exception.DeviceNotFoundException;
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

    @PutMapping
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDto deviceDto){
        try{
            return new ResponseEntity<Device>(devicesService.updateDevice(deviceDto), HttpStatus.OK);
        } catch (DeviceNotFoundException e){
            return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getDeviceById(@PathVariable Integer deviceId){
        try{
            return new ResponseEntity<Device>(devicesService.getDevice(deviceId), HttpStatus.OK);
        } catch (DeviceNotFoundException e){
            return new ResponseEntity<Exception>(e, HttpStatus.BAD_REQUEST);
        }
    }
    
}
