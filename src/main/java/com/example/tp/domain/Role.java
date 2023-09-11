package com.example.tp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    MANAGER("ROLE_MANAGER"),
    USER("ROLE_USER"),

    GUEST("ROLE_GUEST");

    private String value;
}
