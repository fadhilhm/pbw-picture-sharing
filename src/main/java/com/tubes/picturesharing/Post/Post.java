package com.tubes.picturesharing.Post;
import java.time.Instant;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor 
public class Post{
    @NotNull
    private int id;
    @NotNull
    private int accountId;
    @NotNull 
    private String urlImage;
    @NotNull 
    @Max(120)
    private String description;
    @NotNull 
    private Instant createdAt;
}