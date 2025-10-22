package com.example.listing.dto;

import lombok.Data;

@Data
public class CreateListingRequest {
    private String title;
    private String description;
    private Long ownerId;
    private Long price;
}
