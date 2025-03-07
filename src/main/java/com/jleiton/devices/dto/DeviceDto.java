package com.jleiton.devices.dto;

import java.time.Instant;

import com.jleiton.devices.model.StateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeviceDto {
    private Integer id;

    private String name;

    private String brand;

    private StateEnum state;

    private Instant creationTime;
}
