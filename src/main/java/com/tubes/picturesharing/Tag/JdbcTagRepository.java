
package com.tubes.picturesharing.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTagRepository implements TagRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Tag save(Tag tag) {
        String sql = """
                INSERT INTO tag (name, is_active)
                VALUES (?, ?)
                RETURNING id
                """;

        Integer generatedId = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                tag.getName(),
                tag.getActive());

        if (generatedId != null) {
            tag.setId(generatedId);
        }

        return tag;
    }

    @Override
    public Optional<Tag> findById(int id) {
        String sql = "SELECT * FROM tag WHERE id = ?";

        List<Tag> tags = jdbcTemplate.query(sql, this::mapRowToTag, id);

        return tags.isEmpty()
                ? Optional.empty()
                : Optional.of(tags.get(0));
    }

    @Override
    public List<Tag> findAll() {
        String sql = "SELECT * FROM tag";

        return jdbcTemplate.query(sql, this::mapRowToTag);
    }

    @Override
    public List<Tag> findByName(String name) {
        String sql = "SELECT * FROM tag WHERE name ILIKE ?";

        return jdbcTemplate.query(
                sql,
                this::mapRowToTag,
                "%" + name + "%");
    }

    @Override
    public void update(Tag tag) {
        String sql = """
                UPDATE tag
                SET name = ?, is_active = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                tag.getName(),
                tag.getActive(),
                tag.getId());
    }

    @Override
    public void updateActive(int id, Boolean active) {
        String sql = "UPDATE tag SET is_active = ? WHERE id = ?";

        jdbcTemplate.update(sql, active, id);
    }

    private Tag mapRowToTag(ResultSet rs, int rowNum)
            throws SQLException {

        return new Tag(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getObject("is_active", Boolean.class));
    }
}
