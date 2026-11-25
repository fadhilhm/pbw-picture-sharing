package com.tubes.picturesharing.comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> showByPostId(int postId, int page, int size, boolean newestFirst);
    int counterByPostId(int postId);
}
