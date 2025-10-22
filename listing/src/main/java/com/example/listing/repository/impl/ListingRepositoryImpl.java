package com.example.listing.repository.impl;

import com.example.listing.model.Listing;
import com.example.listing.repository.ListingRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

@Repository
public class ListingRepositoryImpl implements ListingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ListingRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Listing save(Listing listing) {
        String sql = """
            INSERT INTO listings (title, description, price, owner_id, created_at, updated_at)
            VALUES (:title, :description, :price, :ownerId, :createdAt, :updatedAt)
            """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", listing.getTitle())
                .addValue("description", listing.getDescription())
                .addValue("price", listing.getPrice())
                .addValue("ownerId", listing.getOwnerId())
                .addValue("createdAt", listing.getCreatedAt())
                .addValue("updatedAt", listing.getUpdatedAt());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        listing.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return listing;
    }

    @Override
    public Optional<Listing> findById(Long id) {
        String sql = "SELECT * FROM listings WHERE id = :id";
        List<Listing> list = jdbcTemplate.query(sql, Map.of("id", id), this::mapRow);
        return list.stream().findFirst();
    }

    @Override
    public List<Listing> findAll(Map<String, Object> filters) {
//        String sql = "SELECT * FROM listings";
//        return jdbcTemplate.query(sql, this::mapRow);
        StringBuilder sql = new StringBuilder("SELECT * FROM listings WHERE 1=1");
        Map<String, Object> params = new HashMap<>();

        if (filters != null) {
            if (filters.get("title") != null) {
                sql.append(" AND title ILIKE :title");
                params.put("title", "%" + filters.get("title") + "%");
            }
            if (filters.get("ownerId") != null) {
                sql.append(" AND owner_id = :ownerId");
                params.put("ownerId", filters.get("ownerId"));
            }
            if (filters.get("minPrice") != null) {
                sql.append(" AND price >= :minPrice");
                params.put("minPrice", filters.get("minPrice"));
            }
            if (filters.get("maxPrice") != null) {
                sql.append(" AND price <= :maxPrice");
                params.put("maxPrice", filters.get("maxPrice"));
            }
        }

        return jdbcTemplate.query(sql.toString(), params, this::mapRow);
    }

    private Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Listing l = new Listing();
        l.setId(rs.getLong("id"));
        l.setTitle(rs.getString("title"));
        l.setDescription(rs.getString("description"));
        l.setPrice(rs.getLong("price"));
        l.setOwnerId(rs.getLong("owner_id"));
        l.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        l.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        return l;
    }
}