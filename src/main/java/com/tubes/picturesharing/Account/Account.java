package com.tubes.picturesharing.Account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @NotNull
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String urlPfp;
    @NotNull
    private String role;
    @NotNull
    private boolean isActive;
    @NotNull
    private Instant createdAt;
}