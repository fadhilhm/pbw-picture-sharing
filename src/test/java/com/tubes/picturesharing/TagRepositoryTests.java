
package com.tubes.picturesharing;

import com.tubes.picturesharing.Tag.Tag;
import com.tubes.picturesharing.Tag.TagRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagRepositoryTests {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @Transactional
    void testSaveAndFindById() {
        Tag tag = new Tag(0, "Nature", true);

        Tag savedTag = tagRepository.save(tag);

        assertTrue(savedTag.getId() > 0);

        var result = tagRepository.findById(savedTag.getId());

        assertTrue(result.isPresent());
        assertEquals("Nature", result.get().getName());
        assertEquals(Boolean.TRUE, result.get().getActive());
    }
}
