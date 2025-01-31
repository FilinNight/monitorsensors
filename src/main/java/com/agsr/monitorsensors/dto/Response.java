package com.agsr.monitorsensors.dto;

import lombok.Getter;

@Getter
public class Response<T> {
    private T value = null;
    private Boolean isSuccessful = true;
    private String error = null;
    private Integer status = 200;

    public Response(T value) {
        this.value = value;
    }

    public Response(T value, Integer status) {
        this.value = value;
        this.status = status;
    }

    public Response(String error, Integer status) {
        this.isSuccessful = false;
        this.error = error;
        this.status = status;
    }
}
