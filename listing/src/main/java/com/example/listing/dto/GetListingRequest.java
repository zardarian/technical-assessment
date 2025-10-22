package com.example.listing.dto;

import lombok.Data;

@Data
public class GetListingRequest {
    private String title;
    private String ownerId;
    private String minPrice;
    private String maxPrice;
}
