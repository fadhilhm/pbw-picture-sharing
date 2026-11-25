package com.tubes.picturesharing.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository 
public class JdbcCommentRepository implements CommentRepository {
    @Autowired 
    private JdbcTemplate jdbcTemplate;

    public RowMapper<Comment> rowMapper = (rs, rowNum) -> {
        Comment comment = new Comment();

        comment.setAccountId(rs.getInt("account_id"));
        comment.setPostId(rs.getInt("post_id"));
        comment.setText(rs.getString("text"));
        comment.setCreatedAt(rs.getTimestamp("created_at").toInstant());
        
        return comment;
    };

    @Override 
    public List<Comment> showByPostId(int postId, int page, int size, boolean newestFirst){
            String direction = newestFirst ? "DESC" : "ASC";
            int offset = (page-1)*size;

            String sql = "SELECT * FROM COMMENTS" + "WHERE post_id = ?" 
            + "ORDER BY created_at" + direction + ", account_id "
            + "LIMIT ? OFFSET ?";

            return jdbcTemplate.query(sql, rowMapper, postId, size, offset);
        }

        @Override 
        public int counterByPostId(int postId){
            String sql = "SELECT COUNT(*) FROM comments WHERE post_id = ?";
            
            List<Integer> counts = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt(1), postId);
            return counts.get(0);
        }
}
