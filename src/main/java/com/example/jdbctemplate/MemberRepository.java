package com.example.jdbctemplate;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository {

    Member save(SaveRequest request);

    void update(Long memberId, UpdateRequest request);

    Optional<Member> findById(Long memberId);

    List<Member> findAll();
}
