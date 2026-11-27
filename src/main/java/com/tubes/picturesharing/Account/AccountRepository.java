package com.tubes.picturesharing.Account;

import com.tubes.picturesharing.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AccountRepository implements GenericRepository<Account> {
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

    @Override
    public Account save(Account account) {
        String query = """
                INSERT INTO account
                    (username, name, email, url_pfp, is_active, created_at, role)
                VALUES
                    (?,?,?,?,?,?,?)
                RETURNING
                    id
                """;

        Integer id = jdbcTemplate.queryForObject(query,
                Integer.class,
                account.getUsername(), account.getName(), account.getEmail(), account.getUrlPfp(), account.isActive(), account.getCreatedAt(), account.getRole()
        );

        account.setId(id);
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = null;

        String query = """
                SELECT * 
                FROM account
                """;

        accounts = jdbcTemplate.query(query, rowMapper);

        return accounts;
    }

    @Override
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

    @Override
    public int update(Account account) {
        String query = """
                UPDATE account
                SET
                    name = ?,
                    username = ?,
                    email = ?,
                    url_pfp = ?,
                    is_active = ?,
                    created_at = ?,
                    role = ?
                WHERE
                    id = ?
                """;

        int affectedRows = jdbcTemplate.update(query,
                account.getUsername(), account.getName(), account.getEmail(),
                account.getUrlPfp(), account.isActive(), account.getCreatedAt(), account.getRole()
        );

        return affectedRows;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = """
                DELETE FROM account
                WHERE id = ?
                """;

        int affectedRows = jdbcTemplate.update(sql, id);

        return affectedRows > 0;
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
