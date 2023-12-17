package com.example.bsms.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoginReq {

    @JsonProperty("username")
    private String userName;

    @JsonProperty("password")
    private String password;

}
