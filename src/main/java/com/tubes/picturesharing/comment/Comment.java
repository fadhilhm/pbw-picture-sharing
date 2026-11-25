package com.tubes.picturesharing.comment;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int accountId;
    private int postId;
    private String text;
    private Instant createdAt;
}


