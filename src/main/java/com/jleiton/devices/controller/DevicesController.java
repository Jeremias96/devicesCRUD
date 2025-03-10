package com.jleiton.devices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.exception.DeviceInUseException;
import com.jleiton.devices.exception.DeviceNotFoundException;
import com.jleiton.devices.model.Device;
import com.jleiton.devices.model.StateEnum;
import com.jleiton.devices.service.DevicesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.error(e.getMessage(), e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getDeviceById(@PathVariable Integer deviceId){
        try{
            return new ResponseEntity<Device>(devicesService.getDevice(deviceId), HttpStatus.OK);
        } catch (DeviceNotFoundException e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(@RequestParam(required = false) String brand,
            @RequestParam(required = false) StateEnum state){
        if (brand == null && state == null){
            return new ResponseEntity<List<Device>>(devicesService.getAllDevices(), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<Device>>(devicesService.getDevicesByParams(brand, state), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable Integer deviceId) {
        try{
            devicesService.deleteDevice(deviceId);
            return new ResponseEntity<String>("Device ID " + deviceId + " deleted", HttpStatus.OK);
        } catch (DeviceNotFoundException | DeviceInUseException e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
}
