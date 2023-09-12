package com.login.loginjava.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@AllArgsConstructor
@Getter
@Setter
public class User {
    @JsonProperty("username")
    private String username = null;
    @JsonProperty("password")
    private String password = null;
}
