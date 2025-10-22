package com.example.owner.delivery.controller;

import com.example.owner.dto.BaseResponse;
import com.example.owner.dto.CreateOwnerRequest;
import com.example.owner.model.Owner;
import com.example.owner.usecase.OwnerUsecase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private final OwnerUsecase ownerUsecase;

    public OwnerController(OwnerUsecase ownerUsecase) {
        this.ownerUsecase = ownerUsecase;
    }

    @PostMapping
    public BaseResponse<Owner> create(@RequestBody CreateOwnerRequest req) {
        Owner created = ownerUsecase.create(req);
        return BaseResponse.success(created);
    }

    @GetMapping
    public BaseResponse<List<Owner>> list() {
        List<Owner> owner = ownerUsecase.list().stream()
                .toList();
        return BaseResponse.success(owner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> getById(@PathVariable Long id) {
        Owner owner = ownerUsecase.getById(id);

        if (owner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.fail("Owner not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(owner));
    }
}
