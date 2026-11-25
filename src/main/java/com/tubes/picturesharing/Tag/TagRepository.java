package com.tubes.picturesharing.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {

    List<Tag> findAll();
    Optional<Tag> findById(int id);
    List<Tag> findByName(String name);
    Tag save(Tag tag);
    void update(Tag tag);
    void updateActive(int id, Boolean active);
}
