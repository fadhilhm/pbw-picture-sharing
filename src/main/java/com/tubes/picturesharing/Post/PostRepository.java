package com.tubes.picturesharing.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(int id);
    List<Post> findAll();
    void deleteById(int id);
    List<Post> findByDescription(String description);
}