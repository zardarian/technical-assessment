package com.example.owner.repository.impl;

import com.example.owner.model.Owner;
import com.example.owner.repository.OwnerRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OwnerRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Owner save(Owner owner) {
        String sql = """
            INSERT INTO owners (name, created_at, updated_at)
            VALUES (:name, :createdAt, :updatedAt)
            """;
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", owner.getName())
                .addValue("createdAt", owner.getCreatedAt())
                .addValue("updatedAt", owner.getUpdatedAt());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        owner.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return owner;
    }

    @Override
    public Optional<Owner> findById(Long id) {
        String sql = "SELECT * FROM owners WHERE id = :id";
        List<Owner> list = jdbcTemplate.query(sql, Map.of("id", id), this::mapRow);
        return list.stream().findFirst();
    }

    @Override
    public List<Owner> findAll() {
        String sql = "SELECT * FROM owners";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    private Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
        Owner o = new Owner();
        o.setId(rs.getLong("id"));
        o.setName(rs.getString("name"));
        o.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        o.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());
        return o;
    }
}
