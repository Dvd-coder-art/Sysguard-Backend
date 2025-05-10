package com.arquivs.sysguard.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiResponse <T> {

    private String status;
    private String message;
    private T data;
    private List<String> erros;


    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.erros = new ArrayList<>();
    }

    public ApiResponse(String status, String message, List<String> erros) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.erros = erros;
    }


}
