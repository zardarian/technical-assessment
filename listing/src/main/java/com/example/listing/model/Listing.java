package com.example.listing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "listings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long price;
    private Long ownerId;
    private Instant createdAt;
    private Instant updatedAt;
}
