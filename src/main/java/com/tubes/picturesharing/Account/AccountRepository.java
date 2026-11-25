package com.tubes.picturesharing.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();
    Optional<Account> findById(int id);
    List<Account> findByName(String name);
    List<Account> findByUsername(String username);
    List<Account> findByRole(String role);
}
