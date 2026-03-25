package com.example.servicebased.user.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponseDto {
    Long id;
    String name;
    String email;
}
