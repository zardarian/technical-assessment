package com.example.listing.dto;

import lombok.Data;

@Data
public class OwnerByIdResponse {
    private boolean result;
    private OwnerDto data;
}
