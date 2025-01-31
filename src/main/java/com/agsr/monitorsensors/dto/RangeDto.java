package com.agsr.monitorsensors.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RangeDto {
    Integer from;
    Integer to;
}
