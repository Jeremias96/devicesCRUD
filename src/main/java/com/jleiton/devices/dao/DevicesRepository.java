package com.jleiton.devices.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jleiton.devices.model.Device;

public interface DevicesRepository extends JpaRepository<Device, Integer>{
    
}
