package com.tubes.picturesharing.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPostRepository implements PostRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Post save(Post post) {
        try{
            String sql = "INSERT INTO posts (account_id, url_image, description, created_at) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, post.getAccountId(), post.getUrlImage(), post.getDescription(), post.getCreatedAt());
            return post;
        }
        catch (Exception e){
            throw new RuntimeException("Failed to save post", e);
        }
    }

    public Optional<Post> findById(int id) {
        String sql="SELECT * FROM posts WHERE id = ?";
        List<Post> posts= jdbcTemplate.query(sql,this::mapRowToPost,id);
        return posts.size()==0? Optional.empty():Optional.of(posts.get(0));
    }

    public List<Post> findAll() {
        String sql="SELECT * FROM posts";
        return jdbcTemplate.query(sql,this::mapRowToPost);
    }

    public void deleteById(int id) {
        String sql="DELETE FROM posts WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    public List<Post> findByDescription(String description){
        String sql="SELECT * FROM posts WHERE description ILIKE ?";
        return jdbcTemplate.query(sql,this::mapRowToPost,"%"+description+"%");
    }

    private Post mapRowToPost(ResultSet rs,int rowNum) throws SQLException {
        return new Post(
                rs.getInt("id"),
                rs.getInt("account_id"),
                rs.getString("url_image"),
                rs.getString("description"),
                rs.getTimestamp("created_at").toInstant()
        );
    }
}