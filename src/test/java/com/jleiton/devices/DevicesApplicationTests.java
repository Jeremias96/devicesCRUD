package com.jleiton.devices;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import com.jleiton.devices.dao.DevicesRepository;
import com.jleiton.devices.dto.DeviceDto;
import com.jleiton.devices.exception.DeviceInUseException;
import com.jleiton.devices.exception.DeviceNotFoundException;
import com.jleiton.devices.mapper.DeviceMapper;
import com.jleiton.devices.model.Device;
import com.jleiton.devices.model.StateEnum;
import com.jleiton.devices.service.DevicesService;

@SpringBootTest
class DevicesApplicationTests {

	@Mock
	private DevicesRepository devicesRepository;

	@Mock
	private DeviceMapper mapper;

	@InjectMocks
	private DevicesService devicesService;

	private Device device;

	private DeviceDto deviceDto;

	@BeforeEach
	void contextLoads() {
		device = Device.builder().id(0).name("Test Device").brand("Test Brand")
			.state(StateEnum.AVAILABLE).creationTime(Instant.now()).build();
		deviceDto = DeviceDto.builder().id(0).name("Test Device").brand("Test Brand")
			.state(StateEnum.AVAILABLE).creationTime(Instant.now()).build();
	}

	@Test
	void shouldReturnDeviceObject_WhenCreatingNewDevice(){
		Mockito.when(mapper.map(deviceDto)).thenReturn(device);
		Mockito.when(devicesRepository.save(any())).thenReturn(device);
		Device actualDevice = devicesService.createDevice(deviceDto);
		Assertions.assertThat(actualDevice).usingRecursiveComparison().isEqualTo(device);
	}

	@Test
	void shouldReturnDeviceObject_WhenUpdatingNewDevice(){
		DevicesService serviceSpy = Mockito.spy(devicesService);
		Mockito.doReturn(device).when(serviceSpy).getDevice(anyInt());
		Mockito.when(devicesRepository.save(any())).thenReturn(device);
		Device actualDevice = serviceSpy.updateDevice(deviceDto);
		Assertions.assertThat(actualDevice).usingRecursiveComparison().isEqualTo(device);
	}

	@Test
	void shouldReturnDeviceObject_WhenGettingDeviceById(){
		Mockito.when(devicesRepository.findById(anyInt())).thenReturn(Optional.of(device));
		Device actualDevice = devicesService.getDevice(1);
		Assertions.assertThat(actualDevice).usingRecursiveComparison().isEqualTo(device);
	}

	@Test
	void shouldThrowDeviceNotFoundException_WhenGettingDeviceByUnknownId(){
		Mockito.when(devicesRepository.findById(anyInt())).thenReturn(Optional.empty());
		assertThrows(DeviceNotFoundException.class, () -> devicesService.getDevice(1));
	}

	@Test
	void shouldReturnDeviceObjectList_WhenGettingAllDevices(){
		Mockito.when(devicesRepository.findAll()).thenReturn(List.of(device));
		List<Device> deviceList = devicesService.getAllDevices();
		Assertions.assertThat(deviceList.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnDeviceObjectList_WhenGettingDevicesByParams(){
		Mockito.when(devicesRepository.findAll(any(Example.class))).thenReturn(List.of(device));
		List<Device> deviceList = devicesService.getDevicesByParams("Test Brand", StateEnum.AVAILABLE);
		Assertions.assertThat(deviceList.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnDeviceObjectList_WhenGettingDevicesByBrand(){
		Mockito.when(devicesRepository.findAll(any(Example.class))).thenReturn(List.of(device));
		List<Device> deviceList = devicesService.getDevicesByParams("Test Brand", null);
		Assertions.assertThat(deviceList.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnDeviceObjectList_WhenGettingDevicesByState(){
		Mockito.when(devicesRepository.findAll(any(Example.class))).thenReturn(List.of(device));
		List<Device> deviceList = devicesService.getDevicesByParams(null, StateEnum.AVAILABLE);
		Assertions.assertThat(deviceList.size()).isEqualTo(1);
	}

	@Test
	void shouldDeleteSuccesfully_WhenDeletingDeviceNotInUse(){
		DevicesService serviceSpy = Mockito.spy(devicesService);
		Mockito.doReturn(device).when(serviceSpy).getDevice(anyInt());
		Mockito.doNothing().when(devicesRepository).deleteById(anyInt());
		serviceSpy.deleteDevice(1);
		Mockito.verify(devicesRepository, Mockito.times(1)).deleteById(anyInt());
	}

	@Test
	void shouldThrowDeviceInUseException_WhenDeletingDeviceInUse(){
		device.setState(StateEnum.IN_USE);
		DevicesService serviceSpy = Mockito.spy(devicesService);
		Mockito.doReturn(device).when(serviceSpy).getDevice(anyInt());
		Mockito.doNothing().when(devicesRepository).deleteById(anyInt());
		assertThrows(DeviceInUseException.class, () -> serviceSpy.deleteDevice(1));
	}

}
