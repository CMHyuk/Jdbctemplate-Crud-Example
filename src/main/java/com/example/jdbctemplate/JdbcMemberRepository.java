package com.example.jdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(SaveRequest request) {
        String sql = "insert into member(name) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        Member member = new Member();
        member.setId(key);
        member.setName(request.getName());
        return member;
    }

    @Override
    public void update(Long memberId, UpdateRequest request) {
        String sql = "update member set name=? where id=?";
        jdbcTemplate.update(sql,
                request.getName(),
                memberId);
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from Member where id = ?";
        try {
            Member member = jdbcTemplate.queryForObject(sql, memberRowMapper(), id);
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from Member";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }
}
