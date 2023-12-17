package com.example.bsms.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasicResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("error_message")
    private String errMessage;
}
