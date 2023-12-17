package com.example.bsms.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRes {

    @JsonProperty("auth_prefix")
    private String authPrefix;

    @JsonProperty("auth_token")
    private String authToken;

    @JsonProperty("error_message")
    private String errorMessage;

}
