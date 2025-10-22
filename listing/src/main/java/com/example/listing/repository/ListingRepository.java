package com.example.listing.repository;

import com.example.listing.model.Listing;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ListingRepository {
    Listing save(Listing listing);
    Optional<Listing> findById(Long id);
    List<Listing> findAll(Map<String, Object> filters);

}