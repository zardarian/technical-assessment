package com.example.owner.usecase;

import com.example.owner.dto.CreateOwnerRequest;
import com.example.owner.model.Owner;
import com.example.owner.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OwnerUsecase {
    @Autowired
    private OwnerRepository ownerRepository;

    public Owner create(CreateOwnerRequest req) {
        Instant now = Instant.now();
        Owner owner = new Owner();
        owner.setName(req.getName());
        owner.setCreatedAt(now);
        owner.setUpdatedAt(now);

        ownerRepository.save(owner);
        return owner;
    }

    public List<Owner> list() {
        return ownerRepository.findAll();
    }

    public Owner getById(Long id) {
        return ownerRepository.findById(id).orElse(null);
    }
}
