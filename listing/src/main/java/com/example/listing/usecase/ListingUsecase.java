package com.example.listing.usecase;

import com.example.listing.dto.CreateListingRequest;
import com.example.listing.dto.GetListingRequest;
import com.example.listing.dto.OwnerByIdResponse;
import com.example.listing.model.Listing;
import com.example.listing.repository.ListingRepository;
import com.example.listing.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ListingUsecase {
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private OwnerService ownerService;

    @Transactional
    @CacheEvict(value = "listing_list", allEntries = true)
    public Listing create(CreateListingRequest req) {
        OwnerByIdResponse owner = ownerService.getOwnerById(req.getOwnerId());
        if (owner == null || owner.getData() == null) {
            throw new RuntimeException("Owner not found");
        }

        Listing l = new Listing();
        l.setTitle(req.getTitle());
        l.setDescription(req.getDescription());
        l.setPrice(req.getPrice());
        l.setOwnerId(req.getOwnerId());
        Instant now = Instant.now();
        l.setCreatedAt(now);
        l.setUpdatedAt(now);
        Listing saved = listingRepository.save(l);
        return l;
    }

    @Cacheable(value = "listing_list")
    public List<Listing> list(GetListingRequest req) {
        System.out.println("Fetching listings from DB...");
        Map<String, Object> filters = new HashMap<>();
        filters.put("title", req.getTitle());
        filters.put("ownerId", req.getOwnerId());
        filters.put("minPrice", req.getMinPrice());
        filters.put("maxPrice", req.getMaxPrice());

        return listingRepository.findAll(filters);
    }

    public Listing get(Long id) {
        return listingRepository.findById(id).orElse(null);
    }
}
