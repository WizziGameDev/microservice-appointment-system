package com.service.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse <T>{

    private int statusCode;

    private T data;

    private Object errors;
}
