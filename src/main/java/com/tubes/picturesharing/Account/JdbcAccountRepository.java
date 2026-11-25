package com.tubes.picturesharing.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcAccountRepository implements AccountRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RowMapper<Account> rowMapper = (rs, rowNum) -> {
        Account account = new Account();

        account.setId(rs.getInt("id"));
        account.setUsername(rs.getString("username"));
        account.setName(rs.getString("name"));
        account.setPassword(rs.getString("password"));
        account.setUrlPfp(rs.getString("url_pfp"));
        account.setActive(rs.getBoolean("is_active"));
        account.setCreatedAt(rs.getTimestamp("created_at").toInstant());

        return account;
    };

    public List<Account> findAll() {
        List<Account> accounts = null;

        String query = """
                SELECT * 
                FROM account
                """;

        accounts = jdbcTemplate.query(query, rowMapper);

        return accounts;
    }

    public Optional<Account> findById(int id) {
        List<Account> accounts = null;

        String query = """
                SELECT * 
                FROM account
                WHERE id = ?
                """;

        accounts = jdbcTemplate.query(query, rowMapper, id);

        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.getFirst());
    }

    public List<Account> findByUsername(String username) {
        List<Account> accounts = null;
        String parameter = "%" + username + "%";

        String query = """
                SELECT * 
                FROM account
                WHERE username 
                LIKE ?
                """;

        accounts = jdbcTemplate.query(query, rowMapper, parameter);

        return accounts;
    }

    public List<Account> findByName(String name) {
        List<Account> accounts = null;
        String parameter = "%" + name + "%";

        String query = """
                SELECT * 
                FROM account
                WHERE name 
                LIKE ?
                """;

        accounts = jdbcTemplate.query(query, rowMapper, parameter);

        return accounts;
    }

    public List<Account> findByRole(String role) {
        List<Account> accounts = null;

        String[] roles = {"admin", "user"};
        if (!Arrays.asList(roles).contains(role)) return new ArrayList<>();

        String query = """
                SELECT * 
                FROM account
                WHERE role = ?
                """;

        accounts = jdbcTemplate.query(query, rowMapper, role);

        return accounts;
    }
}
