package com.example.owner.repository;

import com.example.owner.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {
    Owner save(Owner owner);
    Optional<Owner> findById(Long id);
    List<Owner> findAll();
}
