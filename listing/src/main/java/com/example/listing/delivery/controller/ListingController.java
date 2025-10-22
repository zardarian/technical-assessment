package com.example.listing.delivery.controller;

import com.example.listing.dto.*;
import com.example.listing.model.Listing;
import com.example.listing.usecase.ListingUsecase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listings")
public class ListingController {

    private final ListingUsecase listingUseCase;

    public ListingController(ListingUsecase listingUseCase) {
        this.listingUseCase = listingUseCase;
    }

    @PostMapping
    public BaseResponse<Listing> create(@RequestBody CreateListingRequest req) {
        Listing created = listingUseCase.create(req);
        return BaseResponse.success(created);
    }

    @GetMapping
    public BaseResponse<List<Listing>> list(@RequestParam(required = false) String title,
                                            @RequestParam(required = false) String ownerId,
                                            @RequestParam(required = false) String minPrice,
                                            @RequestParam(required = false) String maxPrice) {
        GetListingRequest getListingRequest = new GetListingRequest();
        getListingRequest.setTitle(title);
        getListingRequest.setOwnerId(ownerId);
        getListingRequest.setMinPrice(minPrice);
        getListingRequest.setMaxPrice(maxPrice);
        List<Listing> listings = listingUseCase.list(getListingRequest).stream()
                .toList();
        return BaseResponse.success(listings);
    }
}
