package com.example.rdsreadonlytest.application.service;

import com.example.rdsreadonlytest.application.mapper.AccountMapper;
import com.example.rdsreadonlytest.domain.model.AccountExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class TestService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired @Qualifier("secondaryjdbc")
    JdbcTemplate secondaryJdbcTemplate;

    @Transactional(readOnly = true)
    public String readOnly() {
        return Long.valueOf(accountMapper.countByExample(new AccountExample())).toString();
    }

    public String writable() {
        return Long.valueOf(accountMapper.countByExample(new AccountExample())).toString();
    }

    public List<Map<String, Object>> getAllFromPrimary() {
        return jdbcTemplate.queryForList("SELECT count(*) FROM ACCOUNT");
    }

    public List<Map<String, Object>> getAllFromSecondary() {
        return secondaryJdbcTemplate.queryForList("SELECT count(*) FROM ACCOUNT");
    }


}
